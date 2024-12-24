
import java.util.Scanner;

// Classe principale. Contient la méthode main qui permet de générer les instances de labyrinthe et d'exécuter les algorithmes de recherche de chemin les plus courts.
public class Main extends WeightedGraph {

    /**
     * Méthode main.
     *
     * @param args Arguments de la ligne de commande.
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int numberOfInstances;

        do {
            System.out.print("Entrez le nombre d'instances à générer ");
            numberOfInstances = sc.nextInt();
            if (numberOfInstances <= 0) {
                System.out.println("Le nombre d'instances doit être supérieur à 0.");
            }
        } while (numberOfInstances <= 0);

        // Génération des instances.
        // Pour chaque instance, on demande les dimensions du labyrinthe. dimensions compris entre 1 et 1000.
        for (int i = 0; i < numberOfInstances; i++) {
            System.out.println("Génération de l'instance " + (i + 1) + "...");
            System.out.println("Entrez les dimensions du labyrinthe (x y) (Attention : x et y doivent être insérés un par un) :");
            System.out.print("Attention : chque dimension doit être comprise entre 1 et 1000, c'est à vous :");
            int x;
            int y;
            do {
                x = sc.nextInt();
                y = sc.nextInt();
                if (x <= 0 || y <= 0 || x > 1000 || y > 1000) {
                    System.out.println("Les dimensions doivent être comprises entre 1 et 1000. c'est à vous: ");
                }
            } while (x <= 0 || y <= 0 || x > 1000 || y > 1000);

            char[][] maze = new char[x][y];

            // Remplissage du labyrinthe.
            for (int j = 0; j < x; j++) {
                System.out.println("Entrez la ligne " + (j + 1) + " du labyrinthe :");
                String line = sc.next();
                for (int k = 0; k < y; k++) {
                    maze[j][k] = line.charAt(k);
                }
            }

            // Création des graphes à partir des labyrinthes.
            Graph gAStar = new Graph();
            Graph gDijkstra = new Graph();
            int startVertex = 0;
            int endVertex = 0;

            // Création des sommets.
            for (int j = 0; j < x; j++) {
                for (int k = 0; k < y; k++) {
                    gAStar.addVertex(1, maze[j][k], j, k);
                    gDijkstra.addVertex(1, maze[j][k], j, k);
                    if (maze[j][k] == 'S') {
                        endVertex = j * y + k;
                    }
                    if (maze[j][k] == 'D') {
                        startVertex = j * y + k;
                    }
                }
            }

            // Création des arrêtes.
            createEdges(maze, gAStar, gDijkstra);

            // Affichage du labyrinthe.
            System.out.println("Labyrinthe :");
            printMaze(maze);

            // Exécution des algorithmes de recherche de chemin sur les instances de labyrinthe.
            System.out.println("Algorithme Dijkstra :");
            long startTime = System.currentTimeMillis();
            char Dijkstra = Labyrinthe.runInstance(gDijkstra, startVertex, endVertex, x, y, 'D');
            long endTime = System.currentTimeMillis();

            System.out.println("Avec l'algorithme Dijkstra, le résultat est : " + Dijkstra + ", et le temps d'exécution est : " + (endTime - startTime) + " ms.");

            System.out.println("Recherche du chemin le plus court...");
            System.out.println("Algorithme A* :");
            startTime = System.currentTimeMillis();
            char AStar = Labyrinthe.runInstance(gDijkstra, startVertex, endVertex, x, y, 'A');
            endTime = System.currentTimeMillis();

            System.out.println("Avec l'algorithme A*, le résultat est : " + AStar + ", et le temps d'exécution est : " + (endTime - startTime) + " ms.");

            // Affichage des chemins.
            System.out.println("#####################################:");
            System.out.println("#####################################:");
            System.out.println("Affichage des chemins :");
            System.out.println("Algorithme A* :");
            for (int j = 0; j < gAStar.vertexList.size(); j++) {
                // Si le sommet a un sommet précédent, alors il fait partie du chemin. on affiche donc son nom puis on passe au sommet précédent.
                Vertex currentV = gAStar.vertexList.get(j);
                if (currentV.prev != null) {
                    System.out.print(currentV.vertexName);
                    currentV = currentV.prev;
                }
            }

            System.out.println("");
            System.out.println("Algorithme Dijkstra :");
            for (int j = 0; j < gDijkstra.vertexList.size(); j++) {
                // Si le sommet a un sommet précédent, alors il fait partie du chemin. on affiche donc son nom puis on passe au sommet précédent.
                Vertex currentV = gDijkstra.vertexList.get(j);
                if (currentV.prev != null) {
                    System.out.print(currentV.vertexName);
                    currentV = currentV.prev;
                }
            }

            System.out.println("");
            System.out.println("#####################################:");
            System.out.println("#####################################:");
            // affichage si le prisonnier est sauvé ou non en fonction de 'Y' ou 'N'
            if (AStar == 'Y' && Dijkstra == 'Y') {
                System.out.println("Le prisonnier est sauvé !");
            } else {
                System.out.println("Le prisonnier n'est pas sauvé !");
            }

        }

        sc.close();
    }

    /**
     * Crée les arrêtes du graphe à partir du labyrinthe et en fonction des
     * règles du jeu.
     *
     * @param maze Labyrinthe.
     * @param gAStar Graphe A*.
     * @param gDijkstra Graphe Dijkstra.
     */
    public static void createEdges(char[][] maze, Graph gAStar, Graph gDijkstra) {
        int x = maze.length;
        int y = maze[0].length;

        for (int ligne = 0; ligne < x; ligne++) {
            for (int col = 0; col < y; col++) {
                int debut = ligne * y + col;
                int destination;
                double weight;

                // On ne crée pas d'arrête pour les murs, les feux
                if (maze[ligne][col] == '#' || maze[ligne][col] == 'F' || maze[ligne][col] == 'A' || maze[ligne][col] == 'L') {
                    continue;
                }

                // Vérifie et ajoute les arrêtes pour les sommets adjacents.
                // Vérifie la cellule au-dessus.
                if (ligne != 0 && maze[ligne - 1][col] != '#' && maze[ligne - 1][col] != 'F' && maze[ligne - 1][col] != 'A' && maze[ligne - 1][col] != 'L') {
                    destination = (ligne - 1) * y + col;
                    weight = 1;
                    gAStar.addEdge(debut, destination, weight);
                    gDijkstra.addEdge(debut, destination, weight);
                }

                // Vérifie la cellule en-dessous.
                if (ligne != x - 1 && (maze[ligne + 1][col] != '#' || maze[ligne + 1][col] == 'F' || maze[ligne + 1][col] == 'A') && maze[ligne + 1][col] != 'L') {
                    destination = (ligne + 1) * y + col;
                    weight = 1;
                    gAStar.addEdge(debut, destination, weight);
                    gDijkstra.addEdge(debut, destination, weight);
                }

                // Vérifie la cellule à gauche.
                if (col != 0 && maze[ligne][col - 1] != '#' && maze[ligne][col - 1] != 'F' && maze[ligne][col - 1] != 'A' && maze[ligne][col - 1] != 'L') {
                    destination = ligne * y + col - 1;
                    weight = 1;
                    gAStar.addEdge(debut, destination, weight);
                    gDijkstra.addEdge(debut, destination, weight);
                }

                // Vérifie la cellule à droite.
                if (col != y - 1 && maze[ligne][col + 1] != '#' && maze[ligne][col + 1] != 'F' && maze[ligne][col + 1] != 'A' && maze[ligne][col + 1] != 'L') {
                    destination = ligne * y + col + 1;
                    weight = 1;
                    gAStar.addEdge(debut, destination, weight);
                    gDijkstra.addEdge(debut, destination, weight);
                }
            }
        }
    }

    /**
     * Affiche le labyrinthe.
     *
     * @param maze Labyrinthe.
     */
    public static void printMaze(char[][] maze) {
        int x = maze.length;
        int y = maze[0].length;
        for (int i = 0; i < x; i++) {
            String line = "";
            for (int j = 0; j < y; j++) {
                line = line + maze[i][j];
            }
            System.out.println(line);
        }
    }

}
