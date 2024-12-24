public class FireMovement {
    private char[][] maze;
    private int rows;
    private int cols;

    public FireMovement(char[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
    }

    public void spreadFire() {
        char[][] newMaze = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(maze[i], 0, newMaze[i], 0, cols);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 'F') {
                    spreadFireFrom(i, j, newMaze);
                }
            }
        }

        maze = newMaze;
    }

    private void spreadFireFrom(int x, int y, char[][] newMaze) {
        if (x > 0 && newMaze[x - 1][y] == ' ') {
            newMaze[x - 1][y] = 'F';
        }
        if (x < rows - 1 && newMaze[x + 1][y] == ' ') {
            newMaze[x + 1][y] = 'F';
        }
        if (y > 0 && newMaze[x][y - 1] == ' ') {
            newMaze[x][y - 1] = 'F';
        }
        if (y < cols - 1 && newMaze[x][y + 1] == ' ') {
            newMaze[x][y + 1] = 'F';
        }
    }

    public char[][] getMaze() {
        return maze;
    }
}