# Currency Converter System - M320 D3 Kompetenznachweis

**Student:** Thomas & Nico
**Module:** M320 - Object-Oriented Programming
**Date:** October 2025

---

## 1. Short Description of the Application

This is a **console-based currency converter system** developed in Java that allows users to convert between different currencies, check exchange rates, and track conversion history. The application demonstrates key object-oriented programming principles including delegation, encapsulation, and clean code separation following the MVC (Model-View-Controller) architectural pattern.

### What Does It Do?

Users can interact with the application through a simple menu-driven interface to:
- **Convert currencies** with real-time exchange rates (USD, EUR, CHF, GBP, JPY, CNY, INR, AUD, CAD, BRL)
- **Check exchange rates** between any two supported currencies
- **Manage favorite currency pairs** for quick access to frequently used conversions
- **View conversion history** of the last 10 transactions with timestamps
- **Handle errors gracefully** with custom exception handling and input validation

The application uses a mock currency exchange API service that simulates realistic exchange rate data, network delays, and occasional connection failures for demonstration purposes.

---

## 2. Key Features

1. **Currency Conversion**
   - Convert any amount between supported currencies
   - Real-time exchange rate calculation
   - Support for 10 major world currencies
   - Accurate conversion with 2 decimal places

2. **Exchange Rate Lookup**
   - View current exchange rates between currency pairs
   - Display buy and sell rates (with 1.5% spread)
   - Real-time rate updates with small market variations
   - Timestamp for each rate query

3. **Favorite Currency Pairs Management**
   - Save frequently used currency pairs
   - Quick access to favorite conversions
   - Add and remove pairs easily
   - Get rates for all favorites at once

4. **Conversion History**
   - Automatically tracks last 10 conversions
   - Display historical transactions with timestamps
   - View currency pairs and rates used
   - Clear history option

5. **Robust Input Validation**
   - Amount validation (positive numbers, max 1 billion)
   - Currency code validation (must be valid 3-letter code)
   - Custom exceptions for specific error types
   - Clear error messages and graceful error recovery

---

## 3. Technical Architecture

### MVC Pattern (Model-View-Controller)

The application follows a clean **MVC architecture** with clear separation of concerns:

```
[User] ↔ [CurrencyUI (View)] ↔ [CurrencyController] ↔ [Services] ↔ [Models]
```

**View (UI Layer):**
- `CurrencyUI` - Console interface handling all user interactions
- No business logic, only input/output operations

**Controller (Business Logic):**
- `CurrencyController` - Orchestrates all operations
- Coordinates between UI and services
- Manages application state (favorites, history, preferences)

**Services (Helper Functions):**
- `CurrencyApiService` - Fetches exchange rate data (mock API with realistic simulation)
- `DataValidationService` - Validates all user inputs
- `CurrencyConversionService` - Performs currency conversions and formatting

**Models (Data Layer):**
- `Currency` - Enum representing supported currencies with symbols
- `CurrencyPair` - Represents a from/to currency combination
- `ExchangeRate` - Exchange rate between two currencies
- `ExchangeRateData` - Complete exchange rate information snapshot

**Exceptions (Error Handling):**
- `InvalidCurrencyException` - Invalid currency code errors
- `ApiConnectionException` - API connection issues
- `InvalidAmountException` - Invalid amount validation failures

### Package Structure

```
ch.tbz.m320.currency/
├── Main.java                          # Application entry point
├── controller/
│   └── CurrencyController.java        # Business logic orchestrator
├── ui/
│   └── CurrencyUI.java                # Console user interface
├── service/
│   ├── CurrencyApiService.java        # Exchange rate provider (mock API)
│   ├── DataValidationService.java     # Input validation
│   └── CurrencyConversionService.java # Currency conversions
├── model/
│   ├── Currency.java                  # Currency enum
│   ├── CurrencyPair.java              # Currency pair model
│   ├── ExchangeRate.java              # Exchange rate model
│   └── ExchangeRateData.java          # Complete exchange data
└── exception/
    ├── InvalidCurrencyException.java  # Currency validation errors
    ├── ApiConnectionException.java    # API connection errors
    └── InvalidAmountException.java    # Amount validation errors
```

---

## 4. OOP Principles Demonstrated

### Delegation
- **CurrencyUI → CurrencyController**: UI delegates all business logic to controller
- **CurrencyController → Services**: Controller delegates specific tasks to specialized services
- **Clear separation**: Each class has a single, well-defined responsibility

### Encapsulation
- Private fields with public getters
- Data hiding in model classes (immutable with `final` fields)
- Internal implementation details hidden from external classes

### Abstraction
- Service layer abstracts complex operations
- Clear interfaces between components
- Users of a class don't need to know implementation details

### Inheritance
- Custom exceptions extend Java's `Exception` class
- Proper exception chaining with cause information

### Polymorphism
- Unified exception handling across different exception types
- Enum-based strategy for currency handling

### Composition
- Controller composed of multiple services
- `ExchangeRateData` contains `CurrencyPair` and `ExchangeRate` objects
- Complex objects built from simpler ones

---

## 5. Design Patterns Used

1. **MVC Pattern** - Separation of presentation, logic, and data
2. **Service Layer Pattern** - Business logic isolated in reusable services
3. **Dependency Injection** - Services injected into controller via constructor
4. **Enum Pattern** - Type-safe currency codes with associated metadata
5. **Immutability Pattern** - Model classes use `final` fields for thread safety
6. **Value Object Pattern** - ExchangeRate and CurrencyPair are immutable value objects

---

## 6. How to Run the Application

### Using IntelliJ IDEA (Recommended):
1. Open the project in IntelliJ IDEA
2. Locate [Main.java](src/main/java/ch/tbz/m320/currency/Main.java)
3. Right-click and select "Run 'Main.main()'"

### Using Command Line:
```bash
# Compile
javac -d bin src/main/java/ch/tbz/m320/currency/**/*.java

# Run
java -cp bin ch.tbz.m320.currency.Main
```

---

## 7. User Interface Flow

```
Main Menu
├── 1. Convert currency
│   └── Enter amount and currencies → Display conversion
├── 2. Get exchange rate
│   └── Enter currency pair → Display rates
├── 3. Manage favorite currency pairs
│   ├── View favorite pairs
│   ├── Add favorite pair
│   ├── Remove favorite pair
│   └── Get rates for all favorites
├── 4. View conversion history
│   └── Display last 10 conversions
├── 5. Settings
│   ├── Change base currency
│   ├── Clear conversion history
│   └── View all available currencies
└── 6. Exit
```

---

## 8. Sample Output

```
====================================
   Welcome to Currency Converter
           System v1.0
====================================

----- Main Menu -----
1. Convert currency
2. Get exchange rate
3. Manage favorite currency pairs
4. View conversion history
5. Settings
6. Exit

Your choice: 1

----- Currency Conversion -----
Enter amount: 100
From currency (e.g., USD): USD
To currency (e.g., EUR): EUR

Fetching exchange rate...

========================================
Conversion Result:
100.00 USD = 92.15 EUR
========================================
```

---

## 9. Supported Currencies

The application supports 10 major world currencies:

| Code | Currency Name | Symbol |
|------|--------------|--------|
| USD  | US Dollar | $ |
| EUR  | Euro | € |
| CHF  | Swiss Franc | CHF |
| GBP  | British Pound | £ |
| JPY  | Japanese Yen | ¥ |
| CNY  | Chinese Yuan | ¥ |
| INR  | Indian Rupee | ₹ |
| AUD  | Australian Dollar | A$ |
| CAD  | Canadian Dollar | C$ |
| BRL  | Brazilian Real | R$ |

---

## 10. Data Structures Used

- **ArrayList<CurrencyPair>** - Stores favorite currency pairs (dynamic sizing, efficient access)
- **LinkedList<ExchangeRateData>** - Maintains conversion history with FIFO (max 10 entries, efficient insertions/deletions)
- **Enum (Currency)** - Type-safe currency representation with metadata
- **HashMap<String, Double>** - Internal storage for base exchange rates in API service

---

## 11. Technical Highlights

### Mock Currency Exchange API Service

The application uses a **mock currency exchange API** ([CurrencyApiService.java](src/main/java/ch/tbz/m320/currency/service/CurrencyApiService.java)) - a simulated exchange rate service that generates data locally instead of connecting to a real external API.

**Why use a Mock API?**
- No API key registration required
- Works without internet connection
- No cost or rate limits
- Predictable test data for development
- Focus on OOP principles rather than API integration

**How it works:**

1. **Base Rates:** Uses USD as the base currency with fixed rates to other currencies
   - 1 USD = 0.92 EUR
   - 1 USD = 0.88 CHF
   - 1 USD = 0.79 GBP
   - 1 USD = 149.50 JPY
   - And 6 more currencies

2. **Cross-Currency Conversion:** Converts any pair by going through USD
   - EUR to GBP: EUR → USD → GBP

3. **Market Variation:** Adds ±1% random variation to simulate real market fluctuations

4. **Buy/Sell Spread:** Includes 1.5% spread between buy and sell rates (realistic for currency exchange)

5. **Network Simulation:**
   - Simulated network latency: 200-500ms delay
   - 5% connection failure rate for error handling demonstration
   - Throws `ApiConnectionException` to test error handling

**Benefits:**
- ✅ Works immediately without setup
- ✅ Free and unlimited queries
- ✅ Offline capability
- ✅ Consistent test data for debugging
- ✅ Realistic API behavior simulation

In a production application, this mock service could be replaced with a real currency exchange API (ExchangeRate-API, Fixer.io) without changing other parts of the code thanks to the service layer architecture.

### Input Validation Rules
- **Amounts**: Must be positive, non-zero, less than 1 billion, and valid numbers
- **Currency codes**: Must be valid 3-letter codes (USD, EUR, CHF, etc.)
- **Menu choices**: Numeric values within valid ranges

### Exchange Rate Calculation
All exchange rates use USD as the base:
- To convert EUR to GBP: `EUR → USD → GBP`
- Formula: `(1 / EUR_to_USD) * USD_to_GBP`
- Small random variation added to simulate market changes

---

## 12. Future Enhancements

- Integration with real exchange rate API (ExchangeRate-API, Fixer.io, Open Exchange Rates)
- Persistent storage (save favorites and history to file/database)
- GUI implementation using JavaFX
- Unit tests using JUnit 5
- Historical exchange rate charts
- Currency conversion calculator widget
- Multi-language support
- Cryptocurrency support (BTC, ETH, etc.)

---

**Note:** This application uses a mock currency exchange service for educational purposes. Exchange rates are simulated and not real market rates.
