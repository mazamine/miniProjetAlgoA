
import java.util.List;
import java.util.Scanner;
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

public class App extends Application {

    private Maze maze;
    private GridPane mazeGrid;
    private String mazeText; // Store the maze text

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

        Button moveUpButton = new Button("Move Up");
        moveUpButton.setOnAction(e -> movePrisoner('T'));

        Button moveDownButton = new Button("Move Down");
        moveDownButton.setOnAction(e -> movePrisoner('B'));

        Button moveLeftButton = new Button("Move Left");
        moveLeftButton.setOnAction(e -> movePrisoner('L'));

        Button moveRightButton = new Button("Move Right");
        moveRightButton.setOnAction(e -> movePrisoner('R'));

        GridPane buttonGrid = new GridPane();
        buttonGrid.add(moveUpButton, 1, 0);
        buttonGrid.add(moveLeftButton, 0, 1);
        buttonGrid.add(moveDownButton, 1, 1);
        buttonGrid.add(moveRightButton, 2, 1);

        root.getChildren().addAll(loadMazeButton, mazeGrid, buttonGrid);

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
    }

    private void movePrisoner(char direction) {
        if (maze != null) {
            boolean win = maze.movePrisoner(direction);
            boolean fireCaught = maze.spreadFire();
            updateMazeDisplay();

            if (win && maze.getMaze()[maze.getExitX()][maze.getExitY()] != 'F') {
                showAlert("Congratulations!", "The prisoner has escaped!");
            } else if (fireCaught || (win && maze.getMaze()[maze.getExitX()][maze.getExitY()] == 'F')) {
                if (maze.getMaze()[maze.getExitX()][maze.getExitY()] == 'F') {
                    showGameOverAlert("Game Over", "The exit has been burned!");
                } else {
                    showGameOverAlert("Game Over", "The prisoner has been caught by the fire!");
                }
            }
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
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
