
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

    public Maze(String filePath) {
        loadMaze(filePath);
    }

    public Maze(char[][] mazeArray) {
        this.rows = mazeArray.length;
        this.cols = mazeArray[0].length;
        this.maze = mazeArray;
        this.firePositions = new ArrayList<>();

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

    public boolean isValidMove(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols && (maze[x][y] == '.' || maze[x][y] == 'S');
    }

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

    public void movePrisonerTo(int x, int y) {
        if (isValidMove(x, y)) {
            maze[prisonerX][prisonerY] = '.'; // Clear the previous position
            prisonerX = x;
            prisonerY = y;
            maze[prisonerX][prisonerY] = 'P';
        }
    }

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

    public char[][] getMaze() {
        return maze;
    }

    public int getPrisonerX() {
        return prisonerX;
    }

    public int getPrisonerY() {
        return prisonerY;
    }

    public int getExitX() {
        return exitX;
    }

    public int getExitY() {
        return exitY;
    }

    public List<int[]> getFirePositions() {
        return firePositions;
    }

    public Node getPrisonerPosition() {
        return new Node(prisonerX, prisonerY, 0, 0, null);
    }

    public Node getExitPosition() {
        return new Node(exitX, exitY, 0, 0, null);
    }
}
