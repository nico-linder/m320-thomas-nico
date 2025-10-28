# Weather Application - Quick Summary

**Student:** Thomas & Nico
**Module:** M320 - Object-Oriented Programming
**Date:** October 2025

---

## What Does It Do?

A **console-based weather app** that lets users check weather for cities, save favorites, and view history with customizable temperature units.

---

## Main Functions

### 1. Get Weather for a City
- Enter any city name ("Zurich", "Paris", "Tokyo")
- Displays: temperature, feels-like, conditions, humidity, wind speed, pressure
- Automatically converts to your preferred unit (°C, °F, or K)
- Example: "Zurich, Switzerland - 22.5°C - Partly cloudy"

### 2. Manage Favorite Cities
- **Add favorites:** Save cities you check frequently
- **Remove favorites:** Delete cities from your list
- **View favorites:** See all saved cities
- **Get weather for all:** Check weather for all favorites at once
- Stored in memory (ArrayList)

### 3. View Weather History
- Automatically tracks your last 10 weather queries
- Shows timestamp for each query
- FIFO system: oldest queries removed when limit reached
- Can be cleared manually in settings

### 4. Settings
- **Change temperature unit:** Switch between Celsius, Fahrenheit, or Kelvin
- **Clear history:** Remove all historical queries
- Preferences apply to all future weather displays

### 5. Exit
- Cleanly exits the application
- Displays goodbye message

---

## Architecture (MVC Pattern)

```
User → WeatherUI → WeatherController → Services → Models
```

**View (WeatherUI):**
- Handles user input/output
- Displays menus and results

**Controller (WeatherController):**
- Manages business logic
- Coordinates between UI and services
- Stores favorites and history

**Services:**
- `WeatherApiService` - Fetches weather data (mock API)
- `DataValidationService` - Validates user inputs
- `TemperatureConversionService` - Converts temperature units

**Models:**
- `City`, `Temperature`, `WeatherData`, `TemperatureUnit` (enum)

**Exceptions:**
- `InvalidCityException`, `ApiConnectionException`, `InvalidTemperatureUnitException`

---

## Key OOP Concepts

- **Delegation:** UI → Controller → Services (clear task separation)
- **Encapsulation:** Private fields with public getters
- **Abstraction:** Service layer hides complex operations
- **Inheritance:** Custom exceptions extend Exception class
- **Composition:** Controller contains services and collections

---

## Technical Details

**Data Structures:**
- `ArrayList<City>` for favorites
- `LinkedList<WeatherData>` for history (max 10)
- `enum TemperatureUnit` for type safety

**Mock API:**
- 6 pre-loaded cities (Zurich, London, Paris, NYC, Tokyo, Sydney)
- Random data generation for unknown cities
- Simulated network delay (200-500ms)
- 5% connection failure rate

**Input Validation:**
- City names: 2-100 characters, letters/spaces/hyphens only
- Temperature units: CELSIUS, FAHRENHEIT, or KELVIN
- Menu choices: valid numeric ranges

---

## How to Run

**IntelliJ IDEA:**
1. Open project
2. Run `Main.java`

**Command Line:**
```bash
javac -d bin src/main/java/ch/tbz/m320/weather/**/*.java
java -cp bin ch.tbz.m320.weather.Main
```

---

## Sample Interaction

```
Main Menu:
1. Get weather for a city
2. Manage favorite cities
3. View weather history
4. Settings
5. Exit

Enter your choice: 1
Enter city name: Zurich

Weather for Zurich, Switzerland:
Temperature: 22.5°C
Feels like: 21.8°C
Conditions: Partly cloudy
Humidity: 65%
Wind Speed: 12.5 km/h
Pressure: 1013 hPa
```

---

## Learning Objectives Met

✅ MVC Architecture
✅ Delegation between layers
✅ Input validation with custom exceptions
✅ Code separation (UI / Logic / Services)
✅ Encapsulation and abstraction
✅ Data structures (ArrayList, LinkedList, Enum)

---

**Note:** Uses mock weather service for educational purposes. Weather data is simulated, not real.
