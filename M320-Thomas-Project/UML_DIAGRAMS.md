# UML Diagramme - Stock Trading Simulator

> **NEW:** PlantUML-Diagramme sind jetzt verfügbar im [uml/](uml/) Ordner!
> - [Class Diagram (PlantUML)](uml/class-diagram.puml)
> - [Sequence Diagram - Buy Stock (PlantUML)](uml/sequence-buy-stock.puml)
> - [Sequence Diagram - Sell Stock (PlantUML)](uml/sequence-sell-stock.puml)
> - [Anleitung zur Nutzung](uml/README.md)

## 1. Klassendiagramm (Grobe Skizze für Planung)

```
┌─────────────────────────┐
│     StockMarket         │
│    <<Singleton>>        │
├─────────────────────────┤
│ - instance: StockMarket │
│ - availableStocks: Map  │
├─────────────────────────┤
│ + getInstance()         │
│ + getStock(symbol)      │
│ + addStock(stock)       │
│ + simulatePriceChanges()│
└─────────────────────────┘

┌─────────────────────────┐
│        Stock            │
├─────────────────────────┤
│ - symbol: String        │
│ - name: String          │
│ - currentPrice: BigDec  │
│ - priceHistory: List    │
├─────────────────────────┤
│ + updatePrice(price)    │
│ + getCurrentPrice()     │
│ + getPriceHistory()     │
└─────────────────────────┘

┌─────────────────────────┐
│         User            │
├─────────────────────────┤
│ - userId: String        │
│ - username: String      │
│ - balance: BigDecimal   │
│ - portfolio: Portfolio  │
│ - transactionHistory    │
├─────────────────────────┤
│ + deposit(amount)       │
│ + withdraw(amount)      │
│ + addTransaction()      │
│ + getTotalAccountValue()│
└─────────────────────────┘
           │
           │ has-a
           ▼
┌─────────────────────────┐
│      Portfolio          │
├─────────────────────────┤
│ - holdings: Map         │
├─────────────────────────┤
│ + addStock(symbol, qty) │
│ + removeStock()         │
│ + getQuantity()         │
│ + calculateTotalValue() │
└─────────────────────────┘

┌─────────────────────────┐
│    <<abstract>>         │
│     Transaction         │
├─────────────────────────┤
│ - transactionId: String │
│ - stockSymbol: String   │
│ - quantity: int         │
│ - pricePerShare: BigDec │
│ - timestamp: DateTime   │
│ - type: TransactionType │
├─────────────────────────┤
│ + getTotalValue()       │
│ + getBalanceImpact()    │◄──────┐
│ + getTransactionDetails()│      │
└─────────────────────────┘      │
           △                      │ inheritance
           │                      │
    ┌──────┴──────┐              │
    │             │              │
┌───────────┐ ┌───────────┐     │
│    Buy    │ │   Sell    │     │
│Transaction│ │Transaction│─────┘
└───────────┘ └───────────┘

┌─────────────────────────┐
│   TradingService        │
├─────────────────────────┤
│ - stockMarket           │
├─────────────────────────┤
│ + buyStock()            │
│ + sellStock()           │
│ + getStockPrice()       │
│ + calculateCost()       │
└─────────────────────────┘
           │
           │ uses
           ▼
┌─────────────────────────┐
│   UserRepository        │
│   <<Repository>>        │
├─────────────────────────┤
│ - users: Map            │
│ - gson: Gson            │
├─────────────────────────┤
│ + save(user)            │
│ + findById(id)          │
│ + findByUsername(name)  │
│ + delete(id)            │
└─────────────────────────┘

┌─────────────────────────┐
│      ConsoleUI          │
├─────────────────────────┤
│ - scanner: Scanner      │
│ - tradingService        │
│ - userRepository        │
│ - currentUser: User     │
├─────────────────────────┤
│ + start()               │
│ + handleBuyStock()      │
│ + handleSellStock()     │
│ + handleViewPortfolio() │
└─────────────────────────┘
```

## 2. Detailliertes Klassendiagramm

### PlantUML Implementation ✅
Ein vollständiges, detailliertes Klassendiagramm ist verfügbar als PlantUML-Datei:
- **Datei:** [uml/class-diagram.puml](uml/class-diagram.puml)
- **Features:**
  - Alle 14 Klassen mit Attributen und Methoden
  - Sichtbarkeiten (+, -, #)
  - Alle Beziehungen (Vererbung, Komposition, Aggregation, Assoziation)
  - Design Pattern Hervorhebungen (Farb-codiert)
  - Notizen zu Design Patterns

### Hinweis für finale Abgabe
Das PlantUML-Diagramm kann exportiert werden als PNG/SVG für die Abgabe.

**Tools zum Anzeigen/Exportieren:**
- PlantUML IntelliJ Plugin (empfohlen)
- PlantUML VS Code Extension
- Online Editor: [plantuml.com](http://www.plantuml.com/plantuml/uml/)
- Lokale JAR-Datei mit Java

**Zu inkludieren:**
- Alle Klassen mit vollständigen Attributen und Methoden
- Alle Beziehungen (Assoziation, Komposition, Aggregation, Vererbung)
- Multiplizitäten
- Sichtbarkeiten (public +, private -, protected #)
- Interfaces (falls vorhanden)
- Design Pattern Notationen

---

## 3. Sequenzdiagramm: Use Case "Aktie kaufen"

### PlantUML Implementation ✅
Vollständige Sequenzdiagramme sind verfügbar:
- **Buy Stock:** [uml/sequence-buy-stock.puml](uml/sequence-buy-stock.puml)
- **Sell Stock:** [uml/sequence-sell-stock.puml](uml/sequence-sell-stock.puml)

Beide Diagramme enthalten:
- Hauptfluss (Happy Path)
- Alternative Flows (Fehlerszenarien)
- Detaillierte Methodenaufrufe
- Aktivierungsbalken
- Notizen zu wichtigen Schritten

### Ablauf (Text-Beschreibung):

```
Benutzer         ConsoleUI       TradingService    StockMarket     User      Portfolio    UserRepository
   │                 │                 │               │            │            │              │
   │ Buy Stock       │                 │               │            │            │              │
   ├────────────────>│                 │               │            │            │              │
   │                 │                 │               │            │            │              │
   │                 │ Enter symbol    │               │            │            │              │
   │<────────────────┤                 │               │            │            │              │
   │ "AAPL"          │                 │               │            │            │              │
   ├────────────────>│                 │               │            │            │              │
   │                 │                 │               │            │            │              │
   │                 │ getStockPrice("AAPL")          │            │            │              │
   │                 ├────────────────>│               │            │            │              │
   │                 │                 │               │            │            │              │
   │                 │                 │ getStock("AAPL")          │            │              │
   │                 │                 ├──────────────>│            │            │              │
   │                 │                 │               │            │            │              │
   │                 │                 │ return Stock  │            │            │              │
   │                 │                 │<──────────────┤            │            │              │
   │                 │                 │               │            │            │              │
   │                 │ price: $175.50  │               │            │            │              │
   │                 │<────────────────┤               │            │            │              │
   │                 │                 │               │            │            │              │
   │ Display price   │                 │               │            │            │              │
   │<────────────────┤                 │               │            │            │              │
   │                 │                 │               │            │            │              │
   │ Enter quantity  │                 │               │            │            │              │
   │<────────────────┤                 │               │            │            │              │
   │ "10"            │                 │               │            │            │              │
   ├────────────────>│                 │               │            │            │              │
   │                 │                 │               │            │            │              │
   │                 │ calculatePurchaseCost("AAPL", 10)           │            │              │
   │                 ├────────────────>│               │            │            │              │
   │                 │                 │               │            │            │              │
   │                 │ $1755.00        │               │            │            │              │
   │                 │<────────────────┤               │            │            │              │
   │                 │                 │               │            │            │              │
   │ Confirm? (yes)  │                 │               │            │            │              │
   │<────────────────┤                 │               │            │            │              │
   │ "yes"           │                 │               │            │            │              │
   ├────────────────>│                 │               │            │            │              │
   │                 │                 │               │            │            │              │
   │                 │ buyStock(user, "AAPL", 10)     │            │            │              │
   │                 ├────────────────>│               │            │            │              │
   │                 │                 │               │            │            │              │
   │                 │                 │ getStock("AAPL")          │            │              │
   │                 │                 ├──────────────>│            │            │              │
   │                 │                 │ return Stock  │            │            │              │
   │                 │                 │<──────────────┤            │            │              │
   │                 │                 │               │            │            │              │
   │                 │                 │ hasSufficientBalance($1755)│            │              │
   │                 │                 ├───────────────────────────>│            │              │
   │                 │                 │ return true    │            │            │              │
   │                 │                 │<───────────────────────────┤            │              │
   │                 │                 │               │            │            │              │
   │                 │                 │ withdraw($1755)            │            │              │
   │                 │                 ├───────────────────────────>│            │              │
   │                 │                 │               │            │            │              │
   │                 │                 │ getPortfolio() │            │            │              │
   │                 │                 ├───────────────────────────>│            │              │
   │                 │                 │ return Portfolio           │            │              │
   │                 │                 │<───────────────────────────┤            │              │
   │                 │                 │               │            │            │              │
   │                 │                 │ addStock("AAPL", 10)       │            │              │
   │                 │                 ├───────────────────────────────────────>│              │
   │                 │                 │               │            │            │              │
   │                 │                 │ addTransaction(BuyTransaction)          │              │
   │                 │                 ├───────────────────────────>│            │              │
   │                 │                 │               │            │            │              │
   │                 │                 │ return BuyTransaction      │            │              │
   │                 │                 │<───────────────────────────┤            │              │
   │                 │                 │               │            │            │              │
   │                 │ BuyTransaction  │               │            │            │              │
   │                 │<────────────────┤               │            │            │              │
   │                 │                 │               │            │            │              │
   │                 │ save(user)      │               │            │            │              │
   │                 ├────────────────────────────────────────────────────────────────────────>│
   │                 │                 │               │            │            │              │
   │ Success message │                 │               │            │            │              │
   │<────────────────┤                 │               │            │            │              │
   │                 │                 │               │            │            │              │
```

### Beschreibung der Schritte:

1. **Benutzer initiiert Kauf**: Wählt Option "Buy Stock" im Menu
2. **Eingabe Symbol**: ConsoleUI fordert Aktiensymbol an
3. **Preis-Abfrage**: TradingService fragt aktuellen Preis vom StockMarket ab
4. **Preis-Anzeige**: Aktueller Preis wird dem Benutzer angezeigt
5. **Eingabe Menge**: Benutzer gibt gewünschte Anzahl Aktien ein
6. **Kosten-Berechnung**: TradingService berechnet Gesamtkosten
7. **Bestätigung**: Benutzer bestätigt den Kauf
8. **Kauf-Transaktion**:
   - Prüfung des Guthabens
   - Abzug vom Kontostand
   - Hinzufügen der Aktien zum Portfolio
   - Erstellen einer BuyTransaction
   - Hinzufügen zur Transaktionshistorie
9. **Persistierung**: User-Daten werden gespeichert
10. **Erfolgs-Meldung**: Benutzer erhält Bestätigung

### Alternative Flüsse:

**Alt 1: Aktie nicht gefunden**
- Nach Schritt 3: StockMarket findet Aktie nicht
- TradingService wirft StockNotFoundException
- ConsoleUI zeigt Fehlermeldung
- Zurück zum Hauptmenü

**Alt 2: Ungenügendes Guthaben**
- Nach Schritt 8 (Balance-Check): User hat nicht genug Geld
- TradingService wirft InsufficientBalanceException
- ConsoleUI zeigt Fehlermeldung
- Zurück zum Hauptmenü

**Alt 3: Benutzer bricht ab**
- Nach Schritt 7: Benutzer antwortet mit "no"
- Keine Transaktion wird durchgeführt
- Zurück zum Hauptmenü

---

## 4. Hinweise für manuelle Erstellung

### Vollständige PlantUML Diagramme

Die vollständigen, detaillierten PlantUML-Diagramme mit allen Features befinden sich im [uml/](uml/) Ordner:

1. **[class-diagram.puml](uml/class-diagram.puml)**
   - Komplettes Klassendiagramm mit allen 14 Klassen
   - Alle Beziehungen und Multiplizitäten
   - Design Pattern Annotationen

2. **[sequence-buy-stock.puml](uml/sequence-buy-stock.puml)**
   - Hauptfluss: Erfolgreicher Aktienkauf
   - Alt Flow 1: Stock nicht gefunden
   - Alt Flow 2: Ungenügendes Guthaben
   - Alt Flow 3: Benutzer bricht ab

3. **[sequence-sell-stock.puml](uml/sequence-sell-stock.puml)**
   - Hauptfluss: Erfolgreicher Aktienverkauf
   - Alt Flow 1: Portfolio leer
   - Alt Flow 2: Aktie nicht im Besitz
   - Alt Flow 3: Ungenügende Aktienanzahl
   - Alt Flow 4: Benutzer bricht ab

### Für die Abgabe

**Empfehlung:**
1. Öffne die `.puml` Dateien in IntelliJ mit dem PlantUML Plugin
2. Exportiere als PNG oder SVG (Rechtsklick → "Export to PNG")
3. Füge die Bilder in deine Dokumentation ein

Die PlantUML-Diagramme sind produktionsreif und können direkt für die Abgabe verwendet werden.

---

## 5. Beziehungen zwischen Klassen

### Vererbung (Inheritance)
- `BuyTransaction` extends `Transaction`
- `SellTransaction` extends `Transaction`

### Komposition (Strong Ownership)
- `User` has `Portfolio` (Portfolio gehört zu User und existiert nicht ohne User)
- `User` has `List<Transaction>` (Transaktionen gehören zu User)

### Aggregation (Weak Ownership)
- `StockMarket` has many `Stock` objects
- `Portfolio` referenziert `Stock` durch Symbol

### Assoziation (Usage)
- `TradingService` uses `StockMarket`
- `TradingService` uses `User`
- `ConsoleUI` uses `TradingService`
- `ConsoleUI` uses `UserRepository`

### Abhängigkeit (Dependency)
- `Portfolio` depends on `StockMarket` (für calculateTotalValue)
- `User` depends on `StockMarket` (für getTotalAccountValue)
