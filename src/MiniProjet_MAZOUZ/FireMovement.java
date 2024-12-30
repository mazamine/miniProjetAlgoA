package MiniProjet_MAZOUZ;

public class FireMovement {

    private char[][] maze;
    private int rows;
    private int cols;

    /**
     * Constructor for FireMovement.
     *
     * @param maze the maze array
     */
    public FireMovement(char[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
    }

    /**
     * Spreads the fire in the maze.
     */
    public void spreadFire() {
        char[][] newMaze = new char[rows][cols];

        // Copy the current maze to the new maze
        for (int i = 0; i < rows; i++) {
            System.arraycopy(maze[i], 0, newMaze[i], 0, cols);
        }

        // Spread the fire from each fire position
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 'F') {
                    spreadFireFrom(i, j, newMaze);
                }
            }
        }

        maze = newMaze;
    }

    /**
     * Spreads the fire from the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param newMaze the new maze array
     */
    private void spreadFireFrom(int x, int y, char[][] newMaze) {
        if (x > 0 && newMaze[x - 1][y] == '.') {
            newMaze[x - 1][y] = 'F';
        }
        if (x < rows - 1 && newMaze[x + 1][y] == '.') {
            newMaze[x + 1][y] = 'F';
        }
        if (y > 0 && newMaze[x][y - 1] == '.') {
            newMaze[x][y - 1] = 'F';
        }
        if (y < cols - 1 && newMaze[x][y + 1] == '.') {
            newMaze[x][y + 1] = 'F';
        }
    }

    /**
     * Gets the maze array.
     *
     * @return the maze array
     */
    public char[][] getMaze() {
        return maze;
    }
}
