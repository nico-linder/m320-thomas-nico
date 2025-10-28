# Weather Information System - M320 D3 Kompetenznachweis

**Student:** Thomas
**Module:** M320 - Object-Oriented Programming
**Date:** October 2025

---

## 1. Short Description of the Application

This is a **console-based weather information system** developed in Java that allows users to query current weather data for cities worldwide. The application demonstrates key object-oriented programming principles including delegation, encapsulation, and clean code separation following the MVC (Model-View-Controller) architectural pattern.

### What Does It Do?

Users can interact with the application through a simple menu-driven interface to:
- **Get weather information** for any city (temperature, humidity, wind speed, pressure, conditions)
- **Manage favorite cities** for quick access to frequently checked locations
- **View weather history** of the last 10 queries with timestamps
- **Change temperature units** between Celsius, Fahrenheit, and Kelvin
- **Handle errors gracefully** with custom exception handling and input validation

The application uses a mock weather API service that simulates realistic weather data, network delays, and occasional connection failures for demonstration purposes.

---

## 2. Key Features

1. **Weather Queries**
   - Look up current weather for any city worldwide
   - Display comprehensive weather information (temperature, feels-like, conditions, humidity, wind, pressure)
   - Automatic conversion to user's preferred temperature unit

2. **Favorite Cities Management**
   - Add cities to favorites list
   - Remove cities from favorites
   - View all favorite cities
   - Get weather for all favorites at once

3. **Weather History**
   - Automatically tracks last 10 weather queries
   - Display historical searches with timestamps
   - Clear history option

4. **Temperature Unit Preferences**
   - Support for Celsius (°C), Fahrenheit (°F), and Kelvin (K)
   - User-configurable preferred unit
   - Automatic conversion between all units

5. **Robust Input Validation**
   - Comprehensive validation for city names (2-100 characters, letters/spaces/hyphens only)
   - Custom exceptions for specific error types
   - Clear error messages and graceful error recovery

---

## 3. Technical Architecture

### MVC Pattern (Model-View-Controller)

The application follows a clean **MVC architecture** with clear separation of concerns:

```
[User] ↔ [WeatherUI (View)] ↔ [WeatherController] ↔ [Services] ↔ [Models]
```

**View (UI Layer):**
- `WeatherUI` - Console interface handling all user interactions
- No business logic, only input/output operations

**Controller (Business Logic):**
- `WeatherController` - Orchestrates all operations
- Coordinates between UI and services
- Manages application state (favorites, history, preferences)

**Services (Helper Functions):**
- `WeatherApiService` - Fetches weather data (mock API with realistic simulation)
- `DataValidationService` - Validates all user inputs
- `TemperatureConversionService` - Converts between temperature units

**Models (Data Layer):**
- `City` - Represents a location (name, country, coordinates)
- `Temperature` - Temperature value with unit
- `WeatherData` - Complete weather information snapshot
- `TemperatureUnit` - Enum for type-safe unit representation

**Exceptions (Error Handling):**
- `InvalidCityException` - Invalid city name validation failures
- `ApiConnectionException` - API connection issues
- `InvalidTemperatureUnitException` - Unsupported temperature units

### Package Structure

```
ch.tbz.m320.weather/
├── Main.java                          # Application entry point
├── controller/
│   └── WeatherController.java         # Business logic orchestrator
├── ui/
│   └── WeatherUI.java                 # Console user interface
├── service/
│   ├── WeatherApiService.java         # Weather data provider (mock API)
│   ├── DataValidationService.java     # Input validation
│   └── TemperatureConversionService.java # Temperature conversions
├── model/
│   ├── City.java                      # City data model
│   ├── Temperature.java               # Temperature with unit
│   ├── WeatherData.java               # Complete weather information
│   └── TemperatureUnit.java           # Enum for temperature units
└── exception/
    ├── InvalidCityException.java      # City validation errors
    ├── ApiConnectionException.java    # API connection errors
    └── InvalidTemperatureUnitException.java # Temperature unit errors
```

---

## 4. OOP Principles Demonstrated

### Delegation
- **WeatherUI → WeatherController**: UI delegates all business logic to controller
- **WeatherController → Services**: Controller delegates specific tasks to specialized services
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
- Strategy pattern for temperature conversions

### Composition
- Controller composed of multiple services
- `WeatherData` contains `City` and `Temperature` objects
- Complex objects built from simpler ones

---

## 5. Design Patterns Used

1. **MVC Pattern** - Separation of presentation, logic, and data
2. **Service Layer Pattern** - Business logic isolated in reusable services
3. **Dependency Injection** - Services injected into controller via constructor
4. **Strategy Pattern** - Different conversion strategies per temperature unit
5. **Immutability Pattern** - Model classes use `final` fields for thread safety
6. **Enum Pattern** - Type-safe temperature units with associated symbols

---

## 6. How to Run the Application

### Using IntelliJ IDEA (Recommended):
1. Open the project in IntelliJ IDEA
2. Locate [Main.java](src/main/java/ch/tbz/m320/weather/Main.java)
3. Right-click and select "Run 'Main.main()'"

### Using Command Line:
```bash
# Compile
javac -d bin src/main/java/ch/tbz/m320/weather/**/*.java

# Run
java -cp bin ch.tbz.m320.weather.Main
```

---

## 7. User Interface Flow

```
Main Menu
├── 1. Get weather for a city
│   └── Enter city name → Display weather
├── 2. Manage favorite cities
│   ├── Add favorite city
│   ├── Remove favorite city
│   ├── View favorite cities
│   └── Get weather for all favorites
├── 3. View weather history
│   └── Display last 10 queries
├── 4. Settings
│   ├── Change temperature unit (Celsius/Fahrenheit/Kelvin)
│   └── Clear weather history
└── 5. Exit
```

---

## 8. Sample Output

```
========================================
       Welcome to Weather App!
========================================

Main Menu:
1. Get weather for a city
2. Manage favorite cities
3. View weather history
4. Settings
5. Exit

Enter your choice: 1
Enter city name: Zurich

========================================
Weather for Zurich, Switzerland:
Temperature: 22.5°C
Feels like: 21.8°C
Conditions: Partly cloudy
Humidity: 65%
Wind Speed: 12.5 km/h
Pressure: 1013 hPa
Last updated: 2025-10-28 14:30:00
========================================
```

---

## 9. Data Structures Used

- **ArrayList<City>** - Stores favorite cities (dynamic sizing, efficient access)
- **LinkedList<WeatherData>** - Maintains weather history with FIFO (max 10 entries, efficient insertions/deletions)
- **Enum (TemperatureUnit)** - Type-safe temperature unit representation

---

## 10. Learning Objectives Achieved

✅ **Own Description**: This comprehensive README documentation
✅ **Delegation**: Multi-layered architecture with clear task delegation
✅ **Input Validation**: `DataValidationService` with custom exceptions
✅ **Code Separation**: Strict separation of UI, logic, and service calls
✅ **MVC Pattern**: Clean Model-View-Controller implementation
✅ **Exception Handling**: Custom exceptions for domain-specific errors
✅ **Encapsulation**: Private fields, public interfaces
✅ **Immutability**: Thread-safe models with `final` fields

---

## 11. Technical Highlights

### Mock Weather API Service

The application uses a **mock weather API** ([WeatherApiService.java](src/main/java/ch/tbz/m320/weather/service/WeatherApiService.java)) - a simulated weather service that generates data locally instead of connecting to a real external API.

**Why use a Mock API?**
- No API key registration required
- Works without internet connection
- No cost or rate limits
- Predictable test data for development
- Focus on OOP principles rather than API integration

**How it works:**

1. **Pre-loaded Cities:** Contains hardcoded data for 6 major cities:
   - Zurich, Switzerland (15.5°C, Partly cloudy)
   - London, UK (12.3°C, Rainy)
   - Paris, France (18.7°C, Sunny)
   - New York, USA (22.1°C, Clear sky)
   - Tokyo, Japan (19.3°C, Overcast)
   - Sydney, Australia (25.5°C, Sunny)

2. **Random Generation:** For unknown cities, generates random realistic data:
   - Temperature: 15-35°C
   - Conditions: Sunny, Partly cloudy, Cloudy, Rainy, or Clear sky
   - Humidity: 40-90%
   - Wind speed: 0-20 km/h
   - Pressure: 980-1040 hPa

3. **Realistic Variation:** Adds small random changes to data each query to simulate changing weather conditions

4. **Network Simulation:**
   - Simulated network latency: 200-500ms delay
   - 5% connection failure rate for error handling demonstration
   - Throws `ApiConnectionException` to test error handling

**Benefits:**
- ✅ Works immediately without setup
- ✅ Free and unlimited queries
- ✅ Offline capability
- ✅ Consistent test data for debugging
- ✅ Realistic API behavior simulation

In a production application, this mock service could be replaced with a real weather API (OpenWeatherMap, WeatherAPI) without changing other parts of the code thanks to the service layer architecture.

### Input Validation Rules
- **City names**: 2-100 characters, only letters, spaces, hyphens, and dots
- **Temperature units**: Must be CELSIUS, FAHRENHEIT, or KELVIN
- **Menu choices**: Numeric values within valid ranges

### Temperature Conversion Formulas
- Celsius to Fahrenheit: `(C × 9/5) + 32`
- Celsius to Kelvin: `C + 273.15`
- All conversions use Celsius as internal standard

---

## 12. Future Enhancements

- Integration with real weather API (OpenWeatherMap, WeatherAPI)
- Persistent storage (save favorites and history to file/database)
- GUI implementation using JavaFX
- Unit tests using JUnit 5
- 5-day weather forecasts
- Weather alerts and notifications
- Multi-language support

---

**Note:** This application uses a mock weather service for educational purposes. Weather data is simulated and not from a real API.