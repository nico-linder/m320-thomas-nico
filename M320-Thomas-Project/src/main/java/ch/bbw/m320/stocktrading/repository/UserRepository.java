package ch.bbw.m320.stocktrading.repository;

import ch.bbw.m320.stocktrading.model.BuyTransaction;
import ch.bbw.m320.stocktrading.model.SellTransaction;
import ch.bbw.m320.stocktrading.model.Transaction;
import ch.bbw.m320.stocktrading.model.User;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repository for persisting user data to JSON files.
 * DESIGN PATTERN: Repository Pattern
 * Reason: Separates data access logic from business logic.
 * Provides a clean interface for data operations and makes it easy to change storage mechanism.
 *
 * Clean Code: Single Responsibility - handles only data persistence for users
 *
 * @author Thomas
 * @version 1.0
 */
public class UserRepository {
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = "users.json";

    private final Gson gson;
    private final Path dataFilePath;
    private Map<String, User> users;

    /**
     * Creates a new UserRepository.
     * Initializes the data directory and loads existing users.
     */
    public UserRepository() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Transaction.class, new TransactionAdapter())
                .create();
        this.dataFilePath = Paths.get(DATA_DIR, USERS_FILE);
        this.users = new HashMap<>();

        initializeDataDirectory();
        loadUsers();
    }

    /**
     * Initializes the data directory if it doesn't exist.
     * Clean Code: Private helper method for initialization
     */
    private void initializeDataDirectory() {
        try {
            Path dataDir = Paths.get(DATA_DIR);
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
        } catch (IOException e) {
            System.err.println("Error creating data directory: " + e.getMessage());
        }
    }

    /**
     * Saves a user to the repository.
     * Clean Code: Method does one thing - saves user data
     *
     * @param user The user to save
     */
    public void save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        users.put(user.getUserId(), user);
        persistUsers();
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for
     * @return Optional containing the user if found
     */
    public Optional<User> findByUsername(String username) {
        if (username == null) {
            return Optional.empty();
        }

        return users.values().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    /**
     * Checks if a user with the given username exists.
     *
     * @param username The username to check
     * @return true if user exists
     */
    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    /**
     * Persists all users to disk.
     * AI-Generated: JSON serialization logic
     * Private method following Clean Code principles.
     */
    private void persistUsers() {
        try (Writer writer = new FileWriter(dataFilePath.toFile())) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads users from disk.
     * AI-Generated: JSON deserialization logic
     */
    private void loadUsers() {
        if (!Files.exists(dataFilePath)) {
            return; // No file yet, start with empty map
        }

        try {
            // Check if file is empty
            if (Files.size(dataFilePath) == 0) {
                System.out.println("User data file is empty. Starting with fresh user repository.");
                return;
            }

            try (Reader reader = new FileReader(dataFilePath.toFile())) {
                // Use TypeToken to tell Gson the exact generic type we need
                Type userMapType = new TypeToken<HashMap<String, User>>(){}.getType();
                Map<String, User> loadedUsers = gson.fromJson(reader, userMapType);
                if (loadedUsers != null) {
                    this.users = loadedUsers;
                }
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Corrupted user data file detected. Starting with fresh user repository.");
            System.err.println("Error details: " + e.getMessage());
            // Start with empty map - corrupted file will be overwritten on first save
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Custom Gson TypeAdapter for LocalDateTime serialization/deserialization.
     * Fixes Java 21+ module system restrictions with Gson.
     */
    private static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public JsonElement serialize(LocalDateTime dateTime, java.lang.reflect.Type type, JsonSerializationContext context) {
            return new JsonPrimitive(dateTime.format(FORMATTER));
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, java.lang.reflect.Type type, JsonDeserializationContext context) throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(), FORMATTER);
        }
    }

    /**
     * Custom Gson TypeAdapter for Transaction serialization/deserialization.
     * Handles polymorphism by storing the concrete type and delegating to appropriate subclass.
     * Supports both old format (with "type" field) and new format (with "transactionClass" wrapper).
     */
    private static class TransactionAdapter implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {
        private static final String CLASS_TYPE = "transactionClass";
        private static final String TYPE_FIELD = "type";

        @Override
        public JsonElement serialize(Transaction transaction, java.lang.reflect.Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            // Store the actual class name
            jsonObject.addProperty(CLASS_TYPE, transaction.getClass().getName());
            // Serialize the transaction data
            JsonElement transactionData = context.serialize(transaction, transaction.getClass());
            jsonObject.add("data", transactionData);
            return jsonObject;
        }

        @Override
        public Transaction deserialize(JsonElement json, java.lang.reflect.Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            // Check if this is the new format (with transactionClass wrapper)
            if (jsonObject.has(CLASS_TYPE)) {
                String className = jsonObject.get(CLASS_TYPE).getAsString();
                JsonElement transactionData = jsonObject.get("data");

                try {
                    Class<?> clazz = Class.forName(className);
                    return context.deserialize(transactionData, clazz);
                } catch (ClassNotFoundException e) {
                    throw new JsonParseException("Unknown transaction class: " + className, e);
                }
            }

            // Handle old format (with "type" field directly in object)
            if (jsonObject.has(TYPE_FIELD)) {
                String transactionType = jsonObject.get(TYPE_FIELD).getAsString();

                if ("BUY".equalsIgnoreCase(transactionType)) {
                    return context.deserialize(json, BuyTransaction.class);
                } else if ("SELL".equalsIgnoreCase(transactionType)) {
                    return context.deserialize(json, SellTransaction.class);
                } else {
                    throw new JsonParseException("Unknown transaction type: " + transactionType);
                }
            }

            throw new JsonParseException("Transaction JSON missing both 'transactionClass' and 'type' fields");
        }
    }
}
