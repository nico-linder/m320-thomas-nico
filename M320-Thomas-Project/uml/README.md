# UML Diagrams - PlantUML Format

This folder contains PlantUML diagram definitions for the Stock Trading Simulator project.

## Files

### Class Diagrams
- **[class-diagram.puml](class-diagram.puml)** - Complete class diagram showing all classes, relationships, and design patterns

### Sequence Diagrams
- **[sequence-buy-stock.puml](sequence-buy-stock.puml)** - Sequence diagram for "Buy Stock" use case with alternative flows
- **[sequence-sell-stock.puml](sequence-sell-stock.puml)** - Sequence diagram for "Sell Stock" use case with alternative flows

## How to View/Edit PlantUML Diagrams

### Option 1: IntelliJ IDEA (Recommended for this project)

1. **Install PlantUML Integration Plugin:**
   - Go to `File → Settings → Plugins`
   - Search for "PlantUML Integration"
   - Install and restart IntelliJ

2. **View Diagrams:**
   - Open any `.puml` file
   - The diagram will render automatically in the preview pane
   - Right-click → "Copy PlantUML Image" to save as PNG

3. **Export Diagrams:**
   - Right-click on `.puml` file
   - Select "Export to PNG" or "Export to SVG"

### Option 2: VS Code

1. **Install PlantUML Extension:**
   - Open Extensions (Ctrl+Shift+X)
   - Search for "PlantUML" by jebbs
   - Install the extension

2. **View Diagrams:**
   - Open any `.puml` file
   - Press `Alt+D` to preview
   - Or right-click → "Preview Current Diagram"

3. **Export:**
   - Press `Ctrl+Shift+P`
   - Type "PlantUML: Export Current Diagram"
   - Choose format (PNG, SVG, PDF)

### Option 3: Online PlantUML Editor

1. Go to [http://www.plantuml.com/plantuml/uml/](http://www.plantuml.com/plantuml/uml/)
2. Copy the content of any `.puml` file
3. Paste into the editor
4. The diagram renders automatically
5. Download as PNG/SVG using the download button

### Option 4: Local PlantUML JAR (Advanced)

1. **Prerequisites:**
   - Java installed
   - Download `plantuml.jar` from [https://plantuml.com/download](https://plantuml.com/download)
   - Install Graphviz: [https://graphviz.org/download/](https://graphviz.org/download/)

2. **Generate Diagrams:**
   ```bash
   java -jar plantuml.jar class-diagram.puml
   java -jar plantuml.jar sequence-buy-stock.puml
   java -jar plantuml.jar sequence-sell-stock.puml
   ```

3. **Output:**
   - PNG files will be generated in the same directory

## PlantUML Syntax Quick Reference

### Class Diagrams
```plantuml
class ClassName {
    - privateField: Type
    + publicMethod(): ReturnType
}

' Relationships
ClassA <|-- ClassB     ' Inheritance
ClassA *-- ClassB      ' Composition
ClassA o-- ClassB      ' Aggregation
ClassA --> ClassB      ' Association
ClassA ..> ClassB      ' Dependency
```

### Sequence Diagrams
```plantuml
actor User
participant "ClassName" as alias

User -> alias: methodCall()
activate alias
alias --> User: return value
deactivate alias
```

## Design Patterns Highlighted

The class diagram uses color coding to highlight design patterns:

- **Yellow (Singleton):** StockMarket
- **Blue (Repository):** UserRepository
- **Red (Exception):** Custom exception classes

## Customization

You can customize the appearance by modifying the `skinparam` settings at the top of each `.puml` file:

```plantuml
skinparam class {
    BackgroundColor LightBlue
    BorderColor Black
    ArrowColor DarkBlue
}
```

## Tips for Manual Creation

While these PlantUML files serve as a great reference, for your **M320 final submission**, you may want to:

1. **Export to PNG/SVG** and include in your documentation
2. **Hand-draw** for a more personal touch (acceptable for school projects)
3. **Use draw.io** if you prefer a GUI-based tool (can import PlantUML)

## Resources

- [PlantUML Official Documentation](https://plantuml.com/)
- [PlantUML Class Diagram Guide](https://plantuml.com/class-diagram)
- [PlantUML Sequence Diagram Guide](https://plantuml.com/sequence-diagram)
- [Real World PlantUML Examples](https://real-world-plantuml.com/)

## Integration with Maven (Optional)

If you want to automatically generate diagrams during build:

```xml
<plugin>
    <groupId>com.github.plantuml-stdlib</groupId>
    <artifactId>plantuml-maven-plugin</artifactId>
    <version>1.2</version>
    <configuration>
        <sourceFiles>
            <directory>${basedir}/uml</directory>
            <includes>
                <include>**/*.puml</include>
            </includes>
        </sourceFiles>
        <outputDirectory>${basedir}/docs/images</outputDirectory>
    </configuration>
</plugin>
```

---

**Created:** 2025-11-03
**Author:** Thomas
**Project:** M320 Stock Trading Simulator
