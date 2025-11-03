# Design Patterns - Stock Trading Simulator

Dieses Dokument beschreibt die verwendeten Design Patterns und begründet deren Einsatz.

## 1. Singleton Pattern

**Wo implementiert:** `StockMarket` Klasse

**Code-Location:** [StockMarket.java:26-37](src/main/java/ch/bbw/m320/stocktrading/model/StockMarket.java#L26-L37)

### Beschreibung
Das Singleton Pattern stellt sicher, dass von einer Klasse nur eine einzige Instanz existiert und bietet einen globalen Zugriffspunkt darauf.

### Implementierung
```java
public class StockMarket {
    private static StockMarket instance;

    private StockMarket() {
        // Private constructor prevents instantiation
    }

    public static synchronized StockMarket getInstance() {
        if (instance == null) {
            instance = new StockMarket();
        }
        return instance;
    }
}
```

### Begründung
**Warum macht das Singleton Pattern hier Sinn?**

1. **Konsistenz**: Es sollte nur einen Aktienmarkt in der Applikation geben. Alle Benutzer handeln auf demselben Markt mit denselben Aktienkursen.

2. **Zentrale Verwaltung**: Der Markt verwaltet alle verfügbaren Aktien und deren Preise zentral. Mehrere Instanzen würden zu inkonsistenten Daten führen.

3. **Ressourcen-Effizienz**: Die Marktdaten müssen nicht mehrfach im Speicher gehalten werden.

4. **Globaler Zugriff**: Alle Services und UI-Komponenten können einfach auf denselben Markt zugreifen.

### Vorteile in diesem Projekt
- Garantiert konsistente Preise für alle Benutzer
- Verhindert versehentliche Duplikation des Marktes
- Einfacher Zugriff von überall in der Applikation

---

## 2. Repository Pattern

**Wo implementiert:** `UserRepository` Klasse

**Code-Location:** [UserRepository.java:21-33](src/main/java/ch/bbw/m320/stocktrading/repository/UserRepository.java#L21-L33)

### Beschreibung
Das Repository Pattern trennt die Datenzugriffslogik von der Business-Logik und bietet eine einheitliche Schnittstelle für Datenoperationen.

### Implementierung
```java
public class UserRepository {
    public void save(User user) { ... }
    public Optional<User> findById(String userId) { ... }
    public Optional<User> findByUsername(String username) { ... }
    public boolean delete(String userId) { ... }
    public Map<String, User> findAll() { ... }
}
```

### Begründung
**Warum macht das Repository Pattern hier Sinn?**

1. **Separation of Concerns**: Die Business-Logik (Services) muss sich nicht darum kümmern, wie Daten gespeichert werden.

2. **Austauschbarkeit**: Die Persistenz-Technologie kann leicht geändert werden (von JSON zu Datenbank), ohne die Business-Logik anzupassen.

3. **Testbarkeit**: Die Business-Logik kann einfacher getestet werden, indem das Repository gemockt wird.

4. **Wiederverwendbarkeit**: Datenzugriffs-Code wird nicht in verschiedenen Services dupliziert.

5. **Clean Architecture**: Klare Trennung zwischen Datenschicht und Business-Schicht.

### Vorteile in diesem Projekt
- Zentrale Verwaltung aller Datenbankoperationen
- Einfache Erweiterung um weitere Repositories (z.B. für Transaktionen)
- Klare Abstraktion der Persistenz-Logik
- Einfacher Wechsel von JSON zu einer echten Datenbank möglich

---

## 3. Template Method Pattern (via Abstract Class)

**Wo implementiert:** `Transaction` (abstract) mit `BuyTransaction` und `SellTransaction`

**Code-Location:**
- [Transaction.java:14-88](src/main/java/ch/bbw/m320/stocktrading/model/Transaction.java#L14-L88)
- [BuyTransaction.java](src/main/java/ch/bbw/m320/stocktrading/model/BuyTransaction.java)
- [SellTransaction.java](src/main/java/ch/bbw/m320/stocktrading/model/SellTransaction.java)

### Beschreibung
Das Template Method Pattern definiert das Grundgerüst eines Algorithmus in einer Basisklasse, während Unterklassen bestimmte Schritte überschreiben können.

### Implementierung
```java
public abstract class Transaction {
    // Common fields and constructor
    protected Transaction(String stockSymbol, int quantity, ...) { ... }

    // Template method
    public BigDecimal getTotalValue() {
        return pricePerShare.multiply(BigDecimal.valueOf(quantity));
    }

    // Abstract methods for subclasses to implement
    public abstract BigDecimal getBalanceImpact();
    public abstract String getTransactionDetails();
}

public class BuyTransaction extends Transaction {
    @Override
    public BigDecimal getBalanceImpact() {
        return getTotalValue().negate(); // Negative for purchases
    }
}

public class SellTransaction extends Transaction {
    @Override
    public BigDecimal getBalanceImpact() {
        return getTotalValue(); // Positive for sales
    }
}
```

### Begründung
**Warum macht dieses Pattern hier Sinn?**

1. **Code-Reuse**: Gemeinsame Logik (Felder, Validierung, Gesamtwert-Berechnung) wird in der Basisklasse implementiert.

2. **Polymorphismus**: Buy- und Sell-Transaktionen können unterschiedlich behandelt werden, ohne dass der aufrufende Code die Unterschiede kennen muss.

3. **Erweiterbarkeit**: Neue Transaktionstypen (z.B. DividendTransaction) können einfach hinzugefügt werden.

4. **Type Safety**: Der Compiler stellt sicher, dass alle abstrakten Methoden implementiert werden.

### Vorteile in diesem Projekt
- Vermeidung von Code-Duplikation
- Klare Struktur für verschiedene Transaktionstypen
- Einfache Berechnung von Balance-Änderungen
- Typ-sichere Implementierung

---

## 4. Strategy Pattern (implizit durch Polymorphismus)

**Wo sichtbar:** Verwendung von `Transaction`-Subklassen in `TradingService`

**Code-Location:** [TradingService.java](src/main/java/ch/bbw/m320/stocktrading/service/TradingService.java)

### Beschreibung
Das Strategy Pattern definiert eine Familie von Algorithmen, kapselt jeden einzelnen und macht sie austauschbar.

### Implementierung
Obwohl nicht explizit als Strategy implementiert, zeigt die Verwendung von Transaction-Subklassen Strategy-Pattern-Charakteristiken:

```java
// In User class
public void addTransaction(Transaction transaction) {
    transactionHistory.add(transaction);
}

// Transaction kann BuyTransaction oder SellTransaction sein
// Beide haben unterschiedliche Implementierungen von getBalanceImpact()
```

### Begründung
**Warum ist dieser Ansatz nützlich?**

1. **Flexibilität**: Verschiedene Transaktionstypen können verschiedene Verhaltensweisen haben.

2. **Open/Closed Principle**: Neue Transaktionstypen können hinzugefügt werden, ohne bestehenden Code zu ändern.

3. **Konsistente Schnittstelle**: Alle Transaktionen implementieren dieselbe Schnittstelle.

---

## Weitere OO-Prinzipien

### Delegation

**Beispiel:** `TradingService` delegiert an `User`, `Portfolio` und `StockMarket`

```java
public BuyTransaction buyStock(User user, String stockSymbol, int quantity) {
    Stock stock = stockMarket.getStock(stockSymbol);  // Delegation to StockMarket
    user.withdraw(totalCost);                         // Delegation to User
    user.getPortfolio().addStock(stockSymbol, qty);  // Delegation to Portfolio
}
```

**Begründung:** Jede Klasse hat ihre eigene Verantwortung und delegiert Aufgaben an die zuständigen Objekte.

### Encapsulation

**Beispiele:**
- Private Felder mit public Gettern
- Defensive Kopien bei Collections (`getHoldings()` in Portfolio)
- Private Konstruktor im Singleton Pattern

### Polymorphismus

**Beispiele:**
- `Transaction`-Hierarchie mit verschiedenen Implementierungen
- Abstract Methods mit unterschiedlichen Implementierungen in Subklassen

---

## Interface-Verwendung

Obwohl in der aktuellen Version keine expliziten Interfaces implementiert sind, wäre die Architektur leicht erweiterbar:

**Mögliche Erweiterungen:**

```java
public interface IStockPriceProvider {
    BigDecimal getCurrentPrice(String symbol);
    void updatePrice(String symbol, BigDecimal price);
}

// Verschiedene Implementierungen möglich:
// - SimulatedPriceProvider (für Tests)
// - RealTimePriceProvider (API-Integration)
// - HistoricalPriceProvider (für Backtesting)
```

Dies würde das **Strategy Pattern** explizit implementieren und die Flexibilität weiter erhöhen.

---

## Zusammenfassung

Die verwendeten Design Patterns in diesem Projekt:

1. **Singleton Pattern** - Für zentrale Markt-Verwaltung
2. **Repository Pattern** - Für saubere Datenpersistenz
3. **Template Method Pattern** - Für Transaktions-Hierarchie
4. **Strategy Pattern (implizit)** - Durch Polymorphismus bei Transaktionen

Diese Patterns arbeiten zusammen, um eine wartbare, erweiterbare und testbare Applikation zu schaffen, die den Anforderungen des M320-Kompetenznachweises entspricht.
