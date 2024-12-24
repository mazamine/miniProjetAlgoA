
import java.util.List;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    private Maze maze;
    private GridPane mazeGrid;
    private String mazeText; // Store the maze text
    private List<Node> path; // Store the path found by A* search
    private int pathIndex; // Current index in the path
    private Timeline timeline; // Declare the timeline variable

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Maze Escape");

        VBox root = new VBox();
        mazeGrid = new GridPane();

        Button loadMazeButton = new Button("Load Maze");
        loadMazeButton.setOnAction(e -> showMazeInputWindow());

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> moveToNextStep());

        root.getChildren().addAll(loadMazeButton, nextButton, mazeGrid);

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    private void showMazeInputWindow() {
        Stage inputStage = new Stage();
        inputStage.setTitle("Input Maze");

        VBox inputRoot = new VBox();
        TextArea inputArea = new TextArea();
        inputArea.setPromptText("Enter your maze here, line by line.");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            mazeText = inputArea.getText(); // Store the maze text
            loadMaze(mazeText);
            inputStage.close();
        });

        inputRoot.getChildren().addAll(inputArea, submitButton);

        inputStage.setScene(new Scene(inputRoot, 300, 300));
        inputStage.show();
    }

    private void loadMaze(String mazeText) {
        String[] mazeLines = mazeText.split("\n");
        int rows = mazeLines.length;
        int cols = mazeLines[0].length();
        char[][] mazeArray = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            mazeArray[i] = mazeLines[i].toCharArray();
        }

        maze = new Maze(mazeArray);
        updateMazeDisplay();

        // Find the shortest path using A* search
        AStarSearch aStarSearch = new AStarSearch(maze);
        path = aStarSearch.findPath(maze.getPrisonerPosition(), maze.getExitPosition());
        pathIndex = 0;
    }

    private void moveToNextStep() {
        if (path == null || path.isEmpty()) {
            showGameOverAlert("Game Over", "No path found!");
            return;
        }

        FireMovement fireMovement = new FireMovement(maze.getMaze());

        if (pathIndex < path.size()) {
            Node currentNode = path.get(pathIndex);
            maze.movePrisonerTo(currentNode.getX(), currentNode.getY());
            updateMazeDisplay();

            if (maze.getMaze()[maze.getExitX()][maze.getExitY()] == 'F') {
                showGameOverAlert("Game Over", "The exit has been burned!");
            } else if (maze.getMaze()[currentNode.getX()][currentNode.getY()] == 'F') {
                showGameOverAlert("Game Over", "The prisoner has been caught by the fire!");
            } else if (currentNode.equals(maze.getExitPosition())) {
                showCongratsAlert("Congratulations!", "The prisoner has escaped!");
            }

            maze.spreadFire();
            updateMazeDisplay();
            pathIndex++;
        }
    }

    private void updateMazeDisplay() {
        if (maze != null) {
            mazeGrid.getChildren().clear();
            char[][] mazeArray = maze.getMaze();
            for (int i = 0; i < mazeArray.length; i++) {
                for (int j = 0; j < mazeArray[i].length; j++) {
                    Label cell = new Label(String.valueOf(mazeArray[i][j]));
                    cell.setMinSize(20, 20);
                    cell.setStyle("-fx-border-color: black; -fx-alignment: center;");
                    mazeGrid.add(cell, j, i);
                }
            }
        }
    }

    private void showCongratsAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Button replayButton = new Button("Replay");
        replayButton.setOnAction(e -> {
            alert.close();
            loadMaze(mazeText); // Reload the same maze
        });

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> Platform.exit());

        VBox alertContent = new VBox();
        alertContent.getChildren().addAll(replayButton, closeButton);
        alert.getDialogPane().setContent(alertContent);

        alert.showAndWait();
    }

    private void showGameOverAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Button replayButton = new Button("Replay");
        replayButton.setOnAction(e -> {
            alert.close();
            loadMaze(mazeText); // Reload the same maze
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> Platform.exit());

        VBox alertContent = new VBox();
        alertContent.getChildren().addAll(replayButton, exitButton);
        alert.getDialogPane().setContent(alertContent);

        alert.showAndWait();
    }
}
