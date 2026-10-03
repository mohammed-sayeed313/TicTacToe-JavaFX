# Tic-Tac-Toe — JavaFX

A modern two-player Tic-Tac-Toe desktop application built with Core Java, JavaFX and Maven.

## Features

- 3 × 3 interactive board
- Two-player X/O gameplay
- Turn-based event handling
- Row, column and diagonal win detection
- Draw detection
- Scoreboard
- Restart round
- Reset scores
- Winning-cell animation
- Hover effects
- Smooth UI animations
- Separate game logic and UI controller
- External CSS styling

## Technology

- Java 21
- JavaFX 21
- Maven
- Core Java
- 2D Arrays
- JavaFX Event Handling
- CSS

## Project Structure

```text
TicTacToe/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── sayeed/
        │           └── tictactoe/
        │               ├── Main.java
        │               ├── GameController.java
        │               └── GameLogic.java
        └── resources/
            └── style.css
```

## Run in VS Code

Open the `TicTacToe` folder in VS Code.

Make sure Java 21 and Maven are installed.

Open the terminal and run:

```bash
mvn clean javafx:run
```

Maven automatically downloads the JavaFX dependencies.

## Main Java Concepts

### 2D Array

```java
private final String[][] board;
```

### Event Handling

```java
cell.setOnAction(event -> playMove(row, col));
```

### Win Detection

The game checks:

- Rows
- Columns
- Main diagonal
- Anti-diagonal

## Author

Mohammed Sayeed
