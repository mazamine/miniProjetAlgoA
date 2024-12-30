package MiniProjet_MAZOUZ;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maze {

    private char[][] maze;
    private int rows;
    private int cols;
    private int prisonerX;
    private int prisonerY;
    private int exitX;
    private int exitY;
    private List<int[]> firePositions;

    /**
     * Constructor for Maze.
     *
     * @param filePath the file path to load the maze from
     */
    public Maze(String filePath) {
        loadMaze(filePath);
    }

    /**
     * Constructor for Maze.
     *
     * @param mazeArray the maze array
     */
    public Maze(char[][] mazeArray) {
        this.rows = mazeArray.length;
        this.cols = mazeArray[0].length;
        this.maze = mazeArray;
        this.firePositions = new ArrayList<>();

        // Initialize the maze and find the positions of the prisoner, exit, and fire
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 'D') {
                    prisonerX = i;
                    prisonerY = j;
                    maze[i][j] = 'P'; // Set initial position to 'P'
                } else if (maze[i][j] == 'S') {
                    exitX = i;
                    exitY = j;
                } else if (maze[i][j] == 'F') {
                    firePositions.add(new int[]{i, j});
                }
            }
        }
    }

    /**
     * Loads the maze from a file.
     *
     * @param filePath the file path to load the maze from
     */
    private void loadMaze(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            List<char[]> mazeList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                mazeList.add(line.toCharArray());
            }
            rows = mazeList.size();
            cols = mazeList.get(0).length;
            maze = new char[rows][cols];
            firePositions = new ArrayList<>();

            // Initialize the maze and find the positions of the prisoner, exit, and fire
            for (int i = 0; i < rows; i++) {
                maze[i] = mazeList.get(i);
                for (int j = 0; j < cols; j++) {
                    if (maze[i][j] == 'D') {
                        prisonerX = i;
                        prisonerY = j;
                        maze[i][j] = 'P'; // Set initial position to 'P'
                    } else if (maze[i][j] == 'S') {
                        exitX = i;
                        exitY = j;
                    } else if (maze[i][j] == 'F') {
                        firePositions.add(new int[]{i, j});
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a move is valid.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols && (maze[x][y] == '.' || maze[x][y] == 'S');
    }

    /**
     * Moves the prisoner in the specified direction.
     *
     * @param direction the direction to move ('T', 'B', 'L', 'R')
     * @return true if the move is successful, false otherwise
     */
    public boolean movePrisoner(char direction) {
        int newX = prisonerX;
        int newY = prisonerY;

        switch (direction) {
            case 'T':
                newX--;
                break;
            case 'B':
                newX++;
                break;
            case 'L':
                newY--;
                break;
            case 'R':
                newY++;
                break;
        }

        if (isValidMove(newX, newY)) {
            // Check if the fire will spread to the new position
            for (int[] firePosition : firePositions) {
                int fireX = firePosition[0];
                int fireY = firePosition[1];
                if ((fireX == newX && Math.abs(fireY - newY) == 1) || (fireY == newY && Math.abs(fireX - newX) == 1)) {
                    return false; // Fire and player heading to the same block
                }
            }

            if (maze[newX][newY] == 'S') {
                if (maze[exitX][exitY] == 'F') {
                    return false; // Fire and player reach 'S' at the same time
                }
                return true; // Prisoner wins
            }
            maze[prisonerX][prisonerY] = '.'; // Clear the previous position
            prisonerX = newX;
            prisonerY = newY;
            maze[prisonerX][prisonerY] = 'P';
        }
        return false;
    }

    /**
     * Moves the prisoner to the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void movePrisonerTo(int x, int y) {
        if (isValidMove(x, y)) {
            if (maze[prisonerX][prisonerY] == 'P' || maze[prisonerX][prisonerY] == 'D') {
                maze[prisonerX][prisonerY] = '.'; // Clear the previous position
            }
            prisonerX = x;
            prisonerY = y;
            maze[prisonerX][prisonerY] = 'P';
        }
    }

    /**
     * Spreads the fire in the maze.
     *
     * @return true if the fire catches the prisoner or the exit, false
     * otherwise
     */
    public boolean spreadFire() {
        List<int[]> newFires = new ArrayList<>();
        for (int[] firePosition : firePositions) {
            int x = firePosition[0];
            int y = firePosition[1];

            if (isValidMove(x - 1, y)) {
                newFires.add(new int[]{x - 1, y});
            }
            if (isValidMove(x + 1, y)) {
                newFires.add(new int[]{x + 1, y});
            }
            if (isValidMove(x, y - 1)) {
                newFires.add(new int[]{x, y - 1});
            }
            if (isValidMove(x, y + 1)) {
                newFires.add(new int[]{x, y + 1});
            }
        }

        for (int[] newFire : newFires) {
            if (maze[newFire[0]][newFire[1]] == 'P') {
                return true; // Fire catches the prisoner
            }
            if (maze[newFire[0]][newFire[1]] == 'S') {
                maze[newFire[0]][newFire[1]] = 'F';
                return true; // Fire catches the exit
            }
            maze[newFire[0]][newFire[1]] = 'F';
            firePositions.add(newFire);
        }
        return false;
    }

    /**
     * Gets the maze array.
     *
     * @return the maze array
     */
    public char[][] getMaze() {
        return maze;
    }

    /**
     * Gets the x-coordinate of the prisoner.
     *
     * @return the x-coordinate of the prisoner
     */
    public int getPrisonerX() {
        return prisonerX;
    }

    /**
     * Gets the y-coordinate of the prisoner.
     *
     * @return the y-coordinate of the prisoner
     */
    public int getPrisonerY() {
        return prisonerY;
    }

    /**
     * Gets the x-coordinate of the exit.
     *
     * @return the x-coordinate of the exit
     */
    public int getExitX() {
        return exitX;
    }

    /**
     * Gets the y-coordinate of the exit.
     *
     * @return the y-coordinate of the exit
     */
    public int getExitY() {
        return exitY;
    }

    /**
     * Gets the fire positions.
     *
     * @return the fire positions
     */
    public List<int[]> getFirePositions() {
        return firePositions;
    }

    /**
     * Gets the position of the prisoner as a Node.
     *
     * @return the position of the prisoner
     */
    public Node getPrisonerPosition() {
        return new Node(prisonerX, prisonerY, 0, 0, null);
    }

    /**
     * Gets the position of the exit as a Node.
     *
     * @return the position of the exit
     */
    public Node getExitPosition() {
        return new Node(exitX, exitY, 0, 0, null);
    }
}
