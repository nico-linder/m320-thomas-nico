# Currency Converter - Quick Summary

**Student:** Thomas & Nico
**Module:** M320 - Object-Oriented Programming
**Date:** October 2025

---

## What Does It Do?

A **console-based currency converter** that lets users convert between currencies, check exchange rates, save favorite pairs, and view conversion history.

---

## Main Functions

### 1. Convert Currency
- Enter amount and select from/to currencies
- Supports 10 major currencies (USD, EUR, CHF, GBP, JPY, CNY, INR, AUD, CAD, BRL)
- Displays accurate conversion with 2 decimal places
- Example: "100.00 USD = 92.15 EUR"

### 2. Get Exchange Rate
- Look up current exchange rate between any two currencies
- Shows buy rate and sell rate (1.5% spread)
- Displays timestamp for rate query
- Example: "1 USD = 0.9215 EUR"

### 3. Manage Favorite Currency Pairs
- **Add favorites:** Save frequently used currency pairs (e.g., USD/EUR)
- **Remove favorites:** Delete pairs from your list
- **View favorites:** See all saved pairs
- **Get rates for all:** Check rates for all favorites at once
- Stored in memory (ArrayList)

### 4. View Conversion History
- Automatically tracks your last 10 conversions
- Shows currency pair, rate, and timestamp for each
- FIFO system: oldest conversions removed when limit reached
- Can be cleared manually in settings

### 5. Settings
- **Change base currency:** Set preferred base currency (default: USD)
- **Clear history:** Remove all historical conversions
- **View all currencies:** See complete list of supported currencies

### 6. Exit
- Cleanly exits the application
- Displays goodbye message

---

## Architecture (MVC Pattern)

```
User → CurrencyUI → CurrencyController → Services → Models
```

**View (CurrencyUI):**
- Handles user input/output
- Displays menus and results

**Controller (CurrencyController):**
- Manages business logic
- Coordinates between UI and services
- Stores favorites and history

**Services:**
- `CurrencyApiService` - Fetches exchange rates (mock API)
- `DataValidationService` - Validates user inputs
- `CurrencyConversionService` - Converts and formats amounts

**Models:**
- `Currency` (enum), `CurrencyPair`, `ExchangeRate`, `ExchangeRateData`

**Exceptions:**
- `InvalidCurrencyException`, `ApiConnectionException`, `InvalidAmountException`

---

## Supported Currencies

**10 Major Currencies:**
- USD (US Dollar) - $
- EUR (Euro) - €
- CHF (Swiss Franc) - CHF
- GBP (British Pound) - £
- JPY (Japanese Yen) - ¥
- CNY (Chinese Yuan) - ¥
- INR (Indian Rupee) - ₹
- AUD (Australian Dollar) - A$
- CAD (Canadian Dollar) - C$
- BRL (Brazilian Real) - R$

---

## Key OOP Concepts

- **Delegation:** UI → Controller → Services (clear task separation)
- **Encapsulation:** Private fields with public getters
- **Abstraction:** Service layer hides complex operations
- **Inheritance:** Custom exceptions extend Exception class
- **Composition:** Controller contains services and collections
- **Enum Pattern:** Type-safe currency codes

---

## Technical Details

**Data Structures:**
- `ArrayList<CurrencyPair>` for favorites
- `LinkedList<ExchangeRateData>` for history (max 10)
- `enum Currency` for type safety
- `HashMap<String, Double>` for base rates

**Mock API:**
- Base rates using USD as standard
- Cross-currency conversion through USD
- ±1% random market variation
- 1.5% buy/sell spread
- Simulated network delay (200-500ms)
- 5% connection failure rate

**Input Validation:**
- Amounts: positive, non-zero, max 1 billion
- Currency codes: valid 3-letter codes only
- Menu choices: valid numeric ranges

---

## How to Run

**IntelliJ IDEA:**
1. Open project
2. Run `Main.java` in `ch.tbz.m320.currency` package

**Command Line:**
```bash
javac -d bin src/main/java/ch/tbz/m320/currency/**/*.java
java -cp bin ch.tbz.m320.currency.Main
```

---

## Sample Interaction

```
Main Menu:
1. Convert currency
2. Get exchange rate
3. Manage favorite currency pairs
4. View conversion history
5. Settings
6. Exit

Your choice: 1

Enter amount: 100
From currency: USD
To currency: EUR

Conversion Result:
100.00 USD = 92.15 EUR
```

---

## Learning Objectives Met

✅ MVC Architecture
✅ Delegation between layers
✅ Input validation with custom exceptions
✅ Code separation (UI / Logic / Services)
✅ Encapsulation and abstraction
✅ Data structures (ArrayList, LinkedList, Enum, HashMap)
✅ Enum pattern for type safety

---

**Note:** Uses mock exchange rate service for educational purposes. Rates are simulated, not real market data.
