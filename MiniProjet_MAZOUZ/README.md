# Prisoner Pardon Maze

This project implements an A* search algorithm to determine if a prisoner can escape from a maze while avoiding dynamic obstacles represented by fire movement.

## Project Structure

```
prisoner-pardon-maze
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com
│   │   │   │   └── example
│   │   │   │       ├── App.java
│   │   │   │       ├── AStarSearch.java
│   │   │   │       ├── Maze.java
│   │   │   │       ├── Node.java
│   │   │   │       └── FireMovement.java
│   │   └── resources
│   │       └── maze.txt
│   └── test
│       ├── java
│       │   └── com
│       │       └── example
│       │           ├── AStarSearchTest.java
│       │           ├── MazeTest.java
│       │           └── FireMovementTest.java
│       └── resources
├── .gitignore
├── build.gradle
└── README.md
```

## Overview

The application reads a maze configuration from a file and uses the A* search algorithm to find a path for the prisoner to escape while avoiding fire that spreads dynamically throughout the maze.

## Setup Instructions

1. Clone the repository:
   ```
   git clone <repository-url>
   ```

2. Navigate to the project directory:
   ```
   cd prisoner-pardon-maze
   ```

3. Build the project using Gradle:
   ```
   ./gradlew build
   ```

4. Run the application:
   ```
   ./gradlew run
   ```

## Usage

- The maze configuration should be placed in `src/main/resources/maze.txt`.
- The application will output whether the prisoner can escape the maze or not.

## Testing

Unit tests are provided for the A* search algorithm, maze loading, and fire movement logic. To run the tests, use:
```
./gradlew test
```

## License

This project is licensed under the MIT License. See the LICENSE file for more details.