package MiniProjet_MAZOUZ;

public class FireMovement {

    private char[][] maze;
    private int rows;
    private int cols;

    /**
     * Constructeur pour FireMovement.
     *
     * @param maze le tableau du labyrinthe
     */
    public FireMovement(char[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
    }

    /**
     * Propage le feu dans le labyrinthe.
     */
    public void spreadFire() {
        char[][] newMaze = new char[rows][cols];

        // Copier le labyrinthe actuel dans le nouveau labyrinthe
        for (int i = 0; i < rows; i++) {
            System.arraycopy(maze[i], 0, newMaze[i], 0, cols);
        }

        // Propage le feu à partir de chaque position de feu
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
     * Propage le feu à partir des coordonnées données.
     *
     * @param x la coordonnée x
     * @param y la coordonnée y
     * @param newMaze le nouveau tableau du labyrinthe
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
     * Obtient le tableau du labyrinthe.
     *
     * @return le tableau du labyrinthe
     */
    public char[][] getMaze() {
        return maze;
    }
}
