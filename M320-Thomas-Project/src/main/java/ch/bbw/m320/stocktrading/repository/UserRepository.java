package ch.bbw.m320.stocktrading.repository;

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
     * Finds a user by their ID.
     *
     * @param userId The user ID to search for
     * @return Optional containing the user if found
     */
    public Optional<User> findById(String userId) {
        if (userId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.get(userId));
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
     * Deletes a user from the repository.
     *
     * @param userId The ID of the user to delete
     * @return true if user was deleted
     */
    public boolean delete(String userId) {
        if (userId == null) {
            return false;
        }

        boolean removed = users.remove(userId) != null;
        if (removed) {
            persistUsers();
        }
        return removed;
    }

    /**
     * Gets all users.
     *
     * @return Map of all users
     */
    public Map<String, User> findAll() {
        return new HashMap<>(users); // Defensive copy
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
     * Clears all users from the repository.
     * Useful for testing.
     */
    public void clear() {
        users.clear();
        persistUsers();
    }

    /**
     * Gets the number of users in the repository.
     *
     * @return The count of users
     */
    public int count() {
        return users.size();
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
}
