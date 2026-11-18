# Grade Calculator

A Java application for calculating a credit-weighted GPA. It includes both a JavaFX desktop interface and an interactive terminal version.

## Requirements

- JDK 25
- Apache Maven

## Run the JavaFX application

From the project root:

```bash
cd gradecalculator
mvn javafx:run
```

The JavaFX interface lets you enter a course count, then the grade and credit value for each course. Select **Calculate GPA** when all courses have been entered.

## Run the terminal application

From the project root:

```bash
cd gradecalculator
mvn compile
java -cp target/classes com.riki.TerminalMain
```

The terminal version prompts for the course count, each course grade, and its credits.

## GPA calculation

Grades use a 4.0 scale:

| Grade | Points |
| --- | ---: |
| A | 4.0 |
| B | 3.0 |
| C | 2.0 |
| D | 1.0 |
| F | 0.0 |

GPA is calculated as the sum of each course's grade points multiplied by its credits, divided by the total credits.

## Run tests

From the project root:

```bash
cd gradecalculator
mvn test
```

## Project structure

```text
gradecalculator/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/riki/
    │   │   ├── Course.java
    │   │   ├── Main.java          # JavaFX application
    │   │   └── TerminalMain.java  # Terminal application
    │   └── resources/
    │       ├── GPA_Calculator.fxml
    │       └── GPA_Calculator.css
    └── test/java/com/riki/
        └── GradeCalculatorTest.java
```
