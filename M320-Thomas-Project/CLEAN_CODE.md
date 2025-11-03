# Clean Code Prinzipien - Stock Trading Simulator

Dieses Dokument zeigt auf, wo und wie Clean Code Prinzipien in diesem Projekt angewendet wurden.

## 1. Meaningful Names (Aussagekräftige Namen)

### Beispiele aus dem Code

**Klassen:**
- `StockMarket` statt `Market` - klar definiert, was für einen Markt
- `TradingService` statt `Service` - zeigt genau die Verantwortung
- `InsufficientBalanceException` statt `BalanceException` - präzise Fehlerbeschreibung

**Methoden:**
```java
// GOOD - Namen beschreiben exakt, was die Methode tut
public BigDecimal calculatePurchaseCost(String stockSymbol, int quantity)
public boolean hasSufficientBalance(BigDecimal amount)
public void addTransaction(Transaction transaction)

// AVOID (Beispiele für schlechte Namen, die wir NICHT verwendet haben)
// public BigDecimal calc(String s, int q)
// public boolean check(BigDecimal a)
// public void add(Transaction t)
```

**Variablen:**
```java
// GOOD - selbsterklärende Variablennamen
BigDecimal totalCost = stock.getCurrentPrice().multiply(BigDecimal.valueOf(quantity));
int ownedQuantity = portfolio.getQuantity(stockSymbol);

// AVOID
// BigDecimal tc = s.getPrice().multiply(BigDecimal.valueOf(q));
// int x = p.getQ(sym);
```

**Location:** Durchgehend in allen Klassen, besonders sichtbar in:
- [TradingService.java](src/main/java/ch/bbw/m320/stocktrading/service/TradingService.java)
- [User.java](src/main/java/ch/bbw/m320/stocktrading/model/User.java)

---

## 2. Single Responsibility Principle (SRP)

### Prinzip
Jede Klasse sollte nur eine einzige Verantwortung haben und nur einen Grund zur Änderung.

### Implementierung

**Beispiel 1: `Stock` Klasse**
```java
public class Stock {
    // Verantwortung: Verwaltet Aktiendaten und Preisverlauf
    // Tut NICHT: Markt-Management, Trading-Logik, UI-Darstellung
}
```
**Location:** [Stock.java:15](src/main/java/ch/bbw/m320/stocktrading/model/Stock.java#L15)

**Beispiel 2: `TradingService` Klasse**
```java
public class TradingService {
    // Verantwortung: Handelt Trading-Business-Logik
    // Tut NICHT: Datenpersistenz, UI-Darstellung, Markt-Verwaltung
}
```
**Location:** [TradingService.java:17](src/main/java/ch/bbw/m320/stocktrading/service/TradingService.java#L17)

**Beispiel 3: `UserRepository` Klasse**
```java
public class UserRepository {
    // Verantwortung: Datenpersistenz für User
    // Tut NICHT: Business-Logik, Trading-Operationen, UI
}
```
**Location:** [UserRepository.java:24](src/main/java/ch/bbw/m320/stocktrading/repository/UserRepository.java#L24)

### Vorteile
- Einfache Wartbarkeit
- Klare Struktur
- Leichte Testbarkeit
- Änderungen betreffen nur relevante Klassen

---

## 3. DRY (Don't Repeat Yourself)

### Prinzip
Code-Duplikation vermeiden durch Extraktion gemeinsamer Logik.

### Implementierung

**Beispiel 1: Validation Helper Methods**
```java
// In TradingService
private void validateUser(User user) {
    if (user == null) {
        throw new IllegalArgumentException("User cannot be null");
    }
}

private void validateQuantity(int quantity) {
    if (quantity <= 0) {
        throw new IllegalArgumentException("Quantity must be positive");
    }
}
```
**Location:** [TradingService.java:169-178](src/main/java/ch/bbw/m320/stocktrading/service/TradingService.java#L169-L178)

**Warum:** Diese Validierungen werden in mehreren Methoden benötigt. Statt den Code zu wiederholen, haben wir private Hilfsmethoden erstellt.

**Beispiel 2: Input Reading in ConsoleUI**
```java
private int readInt() {
    int value = scanner.nextInt();
    scanner.nextLine(); // Clear buffer
    return value;
}
```
**Location:** [ConsoleUI.java:361](src/main/java/ch/bbw/m320/stocktrading/ui/ConsoleUI.java#L361)

**Warum:** Input-Reading mit Buffer-Clearing wird mehrfach benötigt. Eine zentrale Methode verhindert Duplikation.

---

## 4. Small Methods (Kleine Methoden)

### Prinzip
Methoden sollten kurz sein und nur eine Sache tun.

### Implementierung

**Beispiel 1: Private Helper Methods**
```java
// In StockMarket
private void initializeDefaultStocks() {
    addStock(new Stock("AAPL", "Apple Inc.", new BigDecimal("175.50")));
    addStock(new Stock("GOOGL", "Alphabet Inc.", new BigDecimal("140.25")));
    // ...
}
```
**Location:** [StockMarket.java:50](src/main/java/ch/bbw/m320/stocktrading/model/StockMarket.java#L50)

**Warum:** Statt alles im Konstruktor zu machen, haben wir eine separate Methode für die Initialisierung.

**Beispiel 2: Focused Business Logic**
```java
// In User
public void deposit(BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Deposit amount must be positive");
    }
    this.balance = this.balance.add(amount);
}
```
**Location:** [User.java:51](src/main/java/ch/bbw/m320/stocktrading/model/User.java#L51)

**Warum:** Eine Methode = eine Aufgabe (Geld einzahlen). Kurz und verständlich.

---

## 5. Exception Handling (Fehlerbehandlung)

### Prinzip
Verwende Exceptions statt Error-Codes. Validiere Inputs früh und klar.

### Implementierung

**Beispiel 1: Custom Exceptions**
```java
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
```
**Location:** [InsufficientBalanceException.java](src/main/java/ch/bbw/m320/stocktrading/exception/InsufficientBalanceException.java)

**Beispiel 2: Input Validation**
```java
public Stock(String symbol, String name, BigDecimal initialPrice) {
    if (symbol == null || symbol.trim().isEmpty()) {
        throw new IllegalArgumentException("Stock symbol cannot be null or empty");
    }
    if (initialPrice == null || initialPrice.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Initial price must be positive");
    }
    // ...
}
```
**Location:** [Stock.java:29-37](src/main/java/ch/bbw/m320/stocktrading/model/Stock.java#L29-L37)

**Beispiel 3: Try-Catch in UI Layer**
```java
try {
    BuyTransaction transaction = tradingService.buyStock(currentUser, symbol, quantity);
    userRepository.save(currentUser);
    System.out.println("Purchase successful!");
} catch (StockNotFoundException e) {
    System.out.println("Error: " + e.getMessage());
} catch (InsufficientBalanceException e) {
    System.out.println("Error: " + e.getMessage());
}
```
**Location:** [ConsoleUI.java:180-195](src/main/java/ch/bbw/m320/stocktrading/ui/ConsoleUI.java#L180-L195)

### Vorteile
- Klare Fehler-Kommunikation
- Frühe Erkennung von ungültigen Inputs
- Benutzerfreundliche Fehlermeldungen
- Typ-sichere Fehlerbehandlung

---

## 6. Comments (Kommentare)

### Prinzip
Code sollte selbsterklärend sein. Kommentare nur wo nötig für Kontext oder komplexe Logik.

### Implementierung

**GOOD - Kommentare für Design Patterns und Begründungen:**
```java
/**
 * DESIGN PATTERN: Singleton Pattern
 * Reason: There should only be one instance of the stock market in the application.
 * This ensures all users trade on the same market with consistent prices.
 */
public class StockMarket {
    // ...
}
```

**GOOD - JavaDoc für public APIs:**
```java
/**
 * Executes a buy order for a user.
 * Clean Code: Method name clearly describes what it does
 *
 * @param user The user making the purchase
 * @param stockSymbol The stock symbol to buy
 * @param quantity The number of shares to buy
 * @return The created transaction
 * @throws StockNotFoundException if the stock doesn't exist
 */
public BuyTransaction buyStock(User user, String stockSymbol, int quantity) {
    // ...
}
```

**AVOID - Überflüssige Kommentare:**
```java
// AVOID (Beispiele, die wir NICHT verwendet haben)
// i++; // increment i
// return balance; // return the balance
```

---

## 7. Encapsulation (Kapselung)

### Prinzip
Verstecke interne Details, expose nur notwendige Schnittstellen.

### Implementierung

**Beispiel 1: Private Fields mit Public Getters**
```java
public class User {
    private final String userId;        // Private - kann nicht direkt geändert werden
    private final String username;      // Private und final
    private BigDecimal balance;         // Private

    public String getUserId() {         // Public getter
        return userId;
    }
    // ...
}
```
**Location:** [User.java:18-21](src/main/java/ch/bbw/m320/stocktrading/model/User.java#L18-L21)

**Beispiel 2: Defensive Copies**
```java
public List<Transaction> getTransactionHistory() {
    return Collections.unmodifiableList(transactionHistory);
}
```
**Location:** [User.java:97](src/main/java/ch/bbw/m320/stocktrading/model/User.java#L97)

**Warum:** Externe Änderungen an der internen Liste werden verhindert.

**Beispiel 3: Private Constructor (Singleton)**
```java
private StockMarket() {
    // Private constructor prevents direct instantiation
}
```
**Location:** [StockMarket.java:30](src/main/java/ch/bbw/m320/stocktrading/model/StockMarket.java#L30)

---

## 8. Separation of Concerns (Trennung der Verantwortlichkeiten)

### Implementierung

**Layer-Architektur:**
```
┌─────────────────────────────────┐
│ UI Layer (ConsoleUI)            │ - Benutzerinteraktion
├─────────────────────────────────┤
│ Service Layer (TradingService)  │ - Business-Logik
├─────────────────────────────────┤
│ Model Layer (User, Stock, etc.) │ - Domain-Objekte
├─────────────────────────────────┤
│ Repository Layer (UserRepo)     │ - Datenpersistenz
└─────────────────────────────────┘
```

**Beispiel:**
- `ConsoleUI` kennt `TradingService`, aber nicht die Details der Datenpersistenz
- `TradingService` kennt Models, aber nicht UI-Details
- `UserRepository` kennt Persistenz-Details, aber nicht Business-Logik

---

## 9. Consistent Formatting

### Implementierung

- **Einrückung:** 4 Spaces
- **Naming Convention:**
  - Classes: PascalCase (`TradingService`)
  - Methods: camelCase (`buyStock()`)
  - Constants: UPPER_SNAKE_CASE (`DATA_DIR`)
- **Package Structure:** Logische Gruppierung
  ```
  ch.bbw.m320.stocktrading
  ├── model
  ├── service
  ├── repository
  ├── ui
  └── exception
  ```

---

## 10. Verwendung von modernen Java-Features

### BigDecimal für Geldbeträge
```java
private BigDecimal balance;  // Statt double - präzise Berechnung
```

### Optional für Nullable Werte
```java
public Optional<User> findByUsername(String username) {
    return users.values().stream()
        .filter(user -> user.getUsername().equalsIgnoreCase(username))
        .findFirst();
}
```
**Location:** [UserRepository.java:73](src/main/java/ch/bbw/m320/stocktrading/repository/UserRepository.java#L73)

### Enums statt Magic Numbers
```java
public enum TransactionType {
    BUY,
    SELL
}
```
**Location:** [Transaction.java:81](src/main/java/ch/bbw/m320/stocktrading/model/Transaction.java#L81)

---

## Zusammenfassung

Die wichtigsten Clean Code Prinzipien, die in diesem Projekt angewendet wurden:

1. ✅ **Meaningful Names** - Selbsterklärende Namen überall
2. ✅ **Single Responsibility** - Jede Klasse hat eine klare Aufgabe
3. ✅ **DRY** - Keine Code-Duplikation
4. ✅ **Small Methods** - Fokussierte, kurze Methoden
5. ✅ **Exception Handling** - Proper Fehlerbehandlung
6. ✅ **Comments** - Nur wo sinnvoll, Code ist selbsterklärend
7. ✅ **Encapsulation** - Private fields, public interfaces
8. ✅ **Separation of Concerns** - Klare Layer-Architektur
9. ✅ **Consistent Formatting** - Einheitlicher Code-Stil
10. ✅ **Modern Java Features** - BigDecimal, Optional, Enums

Diese Prinzipien machen den Code wartbar, testbar und erweiterbar.
