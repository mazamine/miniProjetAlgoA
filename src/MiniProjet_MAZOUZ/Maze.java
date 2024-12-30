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
     * Constructeur pour Maze.
     *
     * @param filePath le chemin du fichier pour charger le labyrinthe
     */
    public Maze(String filePath) {
        loadMaze(filePath);
    }

    /**
     * Constructeur pour Maze.
     *
     * @param mazeArray le tableau du labyrinthe
     */
    public Maze(char[][] mazeArray) {
        this.rows = mazeArray.length;
        this.cols = mazeArray[0].length;
        this.maze = mazeArray;
        this.firePositions = new ArrayList<>();

        // Initialiser le labyrinthe et trouver les positions du prisonnier, de la sortie et du feu
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 'D') {
                    prisonerX = i;
                    prisonerY = j;
                    maze[i][j] = 'P'; // Définir la position initiale à 'P'
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
     * Charge le labyrinthe à partir d'un fichier.
     *
     * @param filePath le chemin du fichier pour charger le labyrinthe
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

            // Initialiser le labyrinthe et trouver les positions du prisonnier, de la sortie et du feu
            for (int i = 0; i < rows; i++) {
                maze[i] = mazeList.get(i);
                for (int j = 0; j < cols; j++) {
                    if (maze[i][j] == 'D') {
                        prisonerX = i;
                        prisonerY = j;
                        maze[i][j] = 'P'; // Définir la position initiale à 'P'
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
     * Vérifie si un mouvement est valide.
     *
     * @param x la coordonnée x
     * @param y la coordonnée y
     * @return true si le mouvement est valide, false sinon
     */
    public boolean isValidMove(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols && (maze[x][y] == '.' || maze[x][y] == 'S');
    }

    /**
     * Déplace le prisonnier dans la direction spécifiée.
     *
     * @param direction la direction à déplacer ('T', 'B', 'L', 'R')
     * @return true si le mouvement est réussi, false sinon
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
            // Vérifier si le feu se propagera à la nouvelle position
            for (int[] firePosition : firePositions) {
                int fireX = firePosition[0];
                int fireY = firePosition[1];
                if ((fireX == newX && Math.abs(fireY - newY) == 1) || (fireY == newY && Math.abs(fireX - newX) == 1)) {
                    return false; // Le feu et le joueur se dirigent vers le même bloc
                }
            }

            if (maze[newX][newY] == 'S') {
                if (maze[exitX][exitY] == 'F') {
                    return false; // Le feu et le joueur atteignent 'S' en même temps
                }
                return true; // Le prisonnier gagne
            }
            maze[prisonerX][prisonerY] = '.'; // Effacer la position précédente
            prisonerX = newX;
            prisonerY = newY;
            maze[prisonerX][prisonerY] = 'P';
        }
        return false;
    }

    /**
     * Déplace le prisonnier aux coordonnées spécifiées.
     *
     * @param x la coordonnée x
     * @param y la coordonnée y
     */
    public void movePrisonerTo(int x, int y) {
        if (isValidMove(x, y)) {
            if (maze[prisonerX][prisonerY] == 'P' || maze[prisonerX][prisonerY] == 'D') {
                maze[prisonerX][prisonerY] = '.'; // Effacer la position précédente
            }
            prisonerX = x;
            prisonerY = y;
            maze[prisonerX][prisonerY] = 'P';
        }
    }

    /**
     * Propage le feu dans le labyrinthe.
     *
     * @return true si le feu attrape le prisonnier ou la sortie, false sinon
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
                return true; // Le feu attrape le prisonnier
            }
            if (maze[newFire[0]][newFire[1]] == 'S') {
                maze[newFire[0]][newFire[1]] = 'F';
                return true; // Le feu attrape la sortie
            }
            maze[newFire[0]][newFire[1]] = 'F';
            firePositions.add(newFire);
        }
        return false;
    }

    /**
     * Obtient le tableau du labyrinthe.
     *
     * @return le tableau du labyrinthe
     */
    public char[][] getMaze() {
        return maze;
    }

    /**
     * Obtient la coordonnée x du prisonnier.
     *
     * @return la coordonnée x du prisonnier
     */
    public int getPrisonerX() {
        return prisonerX;
    }

    /**
     * Obtient la coordonnée y du prisonnier.
     *
     * @return la coordonnée y du prisonnier
     */
    public int getPrisonerY() {
        return prisonerY;
    }

    /**
     * Obtient la coordonnée x de la sortie.
     *
     * @return la coordonnée x de la sortie
     */
    public int getExitX() {
        return exitX;
    }

    /**
     * Obtient la coordonnée y de la sortie.
     *
     * @return la coordonnée y de la sortie
     */
    public int getExitY() {
        return exitY;
    }

    /**
     * Obtient les positions du feu.
     *
     * @return les positions du feu
     */
    public List<int[]> getFirePositions() {
        return firePositions;
    }

    /**
     * Obtient la position du prisonnier en tant que Node.
     *
     * @return la position du prisonnier
     */
    public Node getPrisonerPosition() {
        return new Node(prisonerX, prisonerY, 0, 0, null);
    }

    /**
     * Obtient la position de la sortie en tant que Node.
     *
     * @return la position de la sortie
     */
    public Node getExitPosition() {
        return new Node(exitX, exitY, 0, 0, null);
    }
}
