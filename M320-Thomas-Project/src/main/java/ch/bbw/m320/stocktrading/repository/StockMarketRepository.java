package ch.bbw.m320.stocktrading.repository;

import ch.bbw.m320.stocktrading.model.Stock;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository for persisting stock market data to JSON files.
 * DESIGN PATTERN: Repository Pattern
 * Reason: Separates data access logic from business logic.
 * Provides a clean interface for data operations and makes it easy to change storage mechanism.
 *
 * Clean Code: Single Responsibility - handles only data persistence for stock prices
 *
 * @author Thomas
 * @version 1.0
 */
public class StockMarketRepository {
    private static final String DATA_DIR = "data";
    private static final String STOCKS_FILE = "stocks.json";

    private final Gson gson;
    private final Path dataFilePath;

    /**
     * Creates a new StockMarketRepository.
     * Initializes the data directory.
     */
    public StockMarketRepository() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(BigDecimal.class, new BigDecimalAdapter())
                .create();
        this.dataFilePath = Paths.get(DATA_DIR, STOCKS_FILE);

        initializeDataDirectory();
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
     * Saves stock market data to disk.
     *
     * @param stocks Map of stock symbols to Stock objects
     */
    public void save(Map<String, Stock> stocks) {
        if (stocks == null) {
            throw new IllegalArgumentException("Stocks map cannot be null");
        }

        try (Writer writer = new FileWriter(dataFilePath.toFile())) {
            gson.toJson(stocks, writer);
        } catch (IOException e) {
            System.err.println("Error saving stock market data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads stock market data from disk.
     *
     * @return Map of stock symbols to Stock objects, or null if no data exists
     */
    public Map<String, Stock> load() {
        if (!Files.exists(dataFilePath)) {
            return null; // No file yet, return null to indicate no saved data
        }

        try {
            // Check if file is empty
            if (Files.size(dataFilePath) == 0) {
                System.out.println("Stock market data file is empty. Using default prices.");
                return null;
            }

            try (Reader reader = new FileReader(dataFilePath.toFile())) {
                Type stockMapType = new TypeToken<HashMap<String, Stock>>(){}.getType();
                Map<String, Stock> loadedStocks = gson.fromJson(reader, stockMapType);
                if (loadedStocks != null && !loadedStocks.isEmpty()) {
                    System.out.println("Loaded " + loadedStocks.size() + " stocks from saved data.");
                    return loadedStocks;
                }
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Corrupted stock market data file detected. Using default prices.");
            System.err.println("Error details: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error loading stock market data: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Custom Gson TypeAdapter for LocalDateTime serialization/deserialization.
     * Fixes Java 21+ module system restrictions with Gson.
     */
    private static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public JsonElement serialize(LocalDateTime dateTime, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(dateTime.format(FORMATTER));
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(), FORMATTER);
        }
    }

    /**
     * Custom Gson TypeAdapter for BigDecimal serialization/deserialization.
     * Ensures proper handling of decimal numbers.
     */
    private static class BigDecimalAdapter implements JsonSerializer<BigDecimal>, JsonDeserializer<BigDecimal> {
        @Override
        public JsonElement serialize(BigDecimal decimal, Type type, JsonSerializationContext context) {
            return new JsonPrimitive(decimal);
        }

        @Override
        public BigDecimal deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            return json.getAsBigDecimal();
        }
    }
}
