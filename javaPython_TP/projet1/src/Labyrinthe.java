package src;

import java.util.ArrayList;
import java.util.HashSet;

public class Labyrinthe extends WeightedGraph {
    // attributs )
    // private int nLines; // nombre de lignes du labyrinthe (en commentaire car pas
    // possible de les utiliser dans les méthodes static)
    // private int nCols; // nombre de colonnes du labyrinthe
    // private char[][] labyrinthe; // labyrinthe sous forme de matrice
    // private Graph graph; // graphe représentant le labyrinthe


    /**
     * Méthode qui gère la propagation du feu
     * @param num_v numéro du sommet
     * @param vertexList liste des sommets
     * @param nLines nombre de lignes
     * @param nCols nombre de colonnes
     * @return true si le prisonnier est mort (pas de solution qu'il survit), false sinon
     */
    public static boolean burnAround(int num_v, ArrayList<Vertex> vertexList, int nline, int ncol) {
        // 'A' to avoid flame propagation on the same turn they are created
        // vérification des sommets voisins
        // Vérifie et mét à jour la case à gauche
        if (num_v % ncol != 0) {
            Vertex vLeft = vertexList.get(num_v - 1);
            if (vLeft.vertexName == '.') {
                vLeft.vertexName = 'A';
            } else if (vLeft.vertexName == 'D' || vLeft.vertexName == 'S') { // si le prisonnier 'D' ou la sortie 'S'
                                                                             // est à côté du feu, le jeu est terminé et
                                                                             // le prisonnier est mort
                return true;
            }
        }
        // Vérifie et mét à jour la case à droite
        if (num_v % ncol != ncol - 1) {
            Vertex vRight = vertexList.get(num_v + 1);
            if (vRight.vertexName == '.') {
                vRight.vertexName = 'A';
            } else if (vRight.vertexName == 'D' || vRight.vertexName == 'S') { // si le prisonnier 'D' ou la sortie 'S'
                                                                               // est à côté du feu, le jeu est terminé
                                                                               // et le prisonnier est mort
                return true;
            }
        }
        // Vérifie et mét à jour la case en haut
        if (num_v >= ncol) {
            Vertex vUp = vertexList.get(num_v - ncol);
            if (vUp.vertexName == '.') {
                vUp.vertexName = 'A';
            } else if (vUp.vertexName == 'D' || vUp.vertexName == 'S') { // si le prisonnier 'D' ou la sortie 'S' est à
                                                                         // côté du feu, le jeu est terminé et le
                                                                         // prisonnier est mort
                return true;
            }
        }
        // Vérifie et mét à jour la case en bas
        if (num_v < ncol * (nline - 1)) {
            Vertex vDown = vertexList.get(num_v + ncol);
            if (vDown.vertexName == '.') {
                vDown.vertexName = 'A';
            } else if (vDown.vertexName == 'D' || vDown.vertexName == 'S') { // si le prisonnier 'D' ou la sortie 'S'
                                                                             // est à côté du feu, le jeu est terminé et
                                                                             // le prisonnier est mort
                return true;
            }
        }
        return false;
    }


    /**
     * Méthode qui vérifie si le prisonnier peut se déplacer vers un sommet
     * @param num_v numéro du sommet
     * @param vertexList liste des sommets
     * @param numberV nombre de sommets
     * @return true si le prisonnier peut se déplacer, false sinon
     */
    public static boolean canMove(int num_v, ArrayList<Vertex> vertexList, int numberV) {
        if (num_v < 0 || num_v >= numberV) {
            return false; // sommet inexistant ou invalide
        }

        Vertex v = vertexList.get(num_v);
        if (v.vertexName == '.' || v.vertexName == 'S') { // si le sommet est libre('.') ou la sortie, le prisonnier 'D'
                                                          // peut se déplacer
            return true; // sommet vide ou sortie
        } else {
            return false; // sommet occupé par un mur '#' ou un feu 'F' ou 'A' probagation du feu
        }
    }
    // public static boolean canMoveDir(){}


    /**
     * Méthode qui vérifie si le prisonnier peut se déplacer vers la sortie et s'il peut gagner quand il est à côté de la sortie
     * @param startVertexNumber numéro du sommet du prisonnier
     * @param vertexList liste des sommets
     * @param nLines nombre de lignes
     * @param nCols nombre de colonnes
     * @return true si le prisonnier peut se déplacer vers la sortie, false sinon
     */
    public static boolean winMove(int startVertexNumber, ArrayList<Vertex> vertexList, int nLines, int nCols) {
        // Vérifie si le prisonnier peut se déplacer vers la case à gauche et si cette case contient la sortie
	    	boolean left = vertexList.get(startVertexNumber).y != 0 && ( vertexList.get(startVertexNumber - 1).vertexName == 'S'); 
			// Vérifie si le prisonnier peut se déplacer vers la case à droite et si cette case contient la sortie
	    	boolean right = vertexList.get(startVertexNumber).y != (nCols - 1) && ( vertexList.get(startVertexNumber + 1).vertexName == 'S'); 
			// Vérifie si le prisonnier peut se déplacer vers la case en en haut et si cette case contient la sortie
			boolean up = vertexList.get(startVertexNumber).x != 0 && vertexList.get(startVertexNumber - nCols).vertexName == 'S'; 
			// Vérifie si le prisonnier peut se déplacer vers la case en bas et si cette case contient la sortie
			boolean down = vertexList.get(startVertexNumber).x != (nLines - 1) && vertexList.get(startVertexNumber + nCols).vertexName == 'S';

        return left || right || up || down; // si le prisonnier est à côté de la sortie, il a gagné :-)
    }


    /**
     * Méthode qui déplace le prisonnier
     * @param directionMovementPossibleForThisTurn direction possible pour le prisonnier
     * @param vertexList liste des sommets
     * @param nLines nombre de lignes
     * @param nCols nombre de colonnes
     * @param endVertexNumber numéro du sommet de la sortie
     * @return true si le prisonnier a gagné ou s'il ne peut pas se déplacer, false sinon
     */
    public static boolean movePrisoner(char directionMovementPossibleForThisTurn, ArrayList<Vertex> vertexList,
            int nLines, int nCols, int endVertexNumber) {
        int startVertexNumber = 0;

        // trouve l'indice du sommet du prisonnier
        for (int i = 0; i < vertexList.size(); i++) {
            if (vertexList.get(i).vertexName == 'D') {
                startVertexNumber = i;
                break; // on s'arrête dès qu'on a trouvé le sommet du prisonnier
            }
        }

        // vérification de victoire du prisonnier
        boolean win = winMove(startVertexNumber, vertexList, nLines, nCols);
        if (win) {
            return true;
        } else {
            vertexList.get(startVertexNumber).vertexName = 'L'; // on place un 'L' pour indiquer que le prisonnier est
                                                                // déjà passé par là

            // déplacement du prisonnier en fonction de la direction
            int newStartVertexNumber = startVertexNumber;

            if (directionMovementPossibleForThisTurn == 'B' && (startVertexNumber + nCols < vertexList.size())) {
                newStartVertexNumber = startVertexNumber + nCols; // déplacement vers le bas
            } else if (directionMovementPossibleForThisTurn == 'T' && (startVertexNumber - nCols >= 0)) {
                newStartVertexNumber = startVertexNumber - nCols; // déplacement vers le haut
            } else if (directionMovementPossibleForThisTurn == 'L' && (startVertexNumber % nCols != 0)) {
                newStartVertexNumber = startVertexNumber - 1; // déplacement vers la gauche
            } else if (directionMovementPossibleForThisTurn == 'R' && (startVertexNumber % nCols != nCols - 1)) {
                newStartVertexNumber = startVertexNumber + 1; // déplacement vers la droite
            }

            // vérification si le prisonnier peut se déplacer
            if (canMove(newStartVertexNumber, vertexList, vertexList.size())) {
                vertexList.get(newStartVertexNumber).vertexName = 'D'; // on place le prisonnier sur le nouveau sommet
                return false; // le prisonnier peut se déplacer
            } else {
                return true; // le prisonnier ne peut pas se déplacer
            }
        }
    }

    // ===============================================
    // Méthode A*
    // ===============================================
    // graph: le graphe représentant la carte
    // start: un entier représentant la case de départ
    // (entier unique correspondant à la case obtenue dans le sens de la lecture)
    // end: un entier représentant la case d'arrivée
    // (entier unique correspondant à la case obtenue dans le sens de la lecture)
    // ncols: le nombre de colonnes dans la carte
    // numberV: le nombre de cases dans la carte
    // cette méthode retourne le chemin le plus court entre la case de départ et la
    // case d'arrivée sous forme d'une liste de sommets
    private static ArrayList<Vertex> AStar(Graph graphe, int startVertexNumber, int endVertexNumber, int nCols,
            int numberV) {
        graphe.vertexList.get(startVertexNumber).timeFromSource = 0;

        ArrayList<Vertex> path = new ArrayList<Vertex>(); // liste des sommets du chemin le plus court

        // on initialise la liste des sommets à visiter
        HashSet<Integer> to_visit = new HashSet<Integer>();
        for (int i = 0; i < numberV; i++) {
            to_visit.add(i);
        }

        for (WeightedGraph.Vertex v : graphe.vertexList) {
            int vRow = v.num / nCols;
            int vCol = v.num % nCols;

            int endRow = endVertexNumber / nCols;
            int endCol = endVertexNumber % nCols;

            // calcul de la distance de v à la case d'arrivée (heuristic) en utilisant
            // une distance euclidienne
            v.heuristic = Math.sqrt(Math.pow(vRow - endRow, 2) + Math.pow(vCol - endCol, 2));
        }

        // boucle de l'algorithme A*
        while (to_visit.contains(endVertexNumber)) {
            double minDistance = Double.POSITIVE_INFINITY;
            int min_v = -1;

            // On parcourt tous les noeuds à visiter pour trouver le noeud avec la distance
            // minimale
            for (int v : to_visit) {
                double totalDistance = graphe.vertexList.get(v).timeFromSource + graphe.vertexList.get(v).heuristic;
                if (totalDistance < minDistance) {
                    minDistance = totalDistance;
                    min_v = v;
                }
            }

            // On l'enlève des noeuds à visiter
            to_visit.remove(min_v);

            
    
            // On met à jour les distances des voisins du sommet
            for (int i = 0; i < graphe.vertexList.get(min_v).adjacencylist.size(); i++) {
                if (to_visit.contains(graphe.vertexList.get(min_v).adjacencylist.get(i).destination)) {
                    int to_try = graphe.vertexList.get(min_v).adjacencylist.get(i).destination;
                    boolean peutDeplacer = canMove(to_try, graphe.vertexList, numberV);

                    if (peutDeplacer) {
                        double newDistance = graphe.vertexList.get(min_v).timeFromSource
                                + graphe.vertexList.get(min_v).adjacencylist.get(i).weight;
                        if (newDistance < graphe.vertexList.get(to_try).timeFromSource) {
                            graphe.vertexList.get(to_try).timeFromSource = newDistance;
                            graphe.vertexList.get(to_try).prev = graphe.vertexList.get(min_v);
                        }
                    }

                }

            }
        }

        // ajout des sommets au chemin le plus court
        Vertex v = graphe.vertexList.get(endVertexNumber);
        while (v != null) {
            path.add(v);
            v = v.prev;
        }
        return path;

    }

    // ===============================================
    // Méthode Dijkstra
    // ===============================================
    // graph: le graphe représentant la carte
    // start: un entier représentant la case de départ
    // (entier unique correspondant à la case obtenue dans le sens de la lecture)
    // end: un entier représentant la case d'arrivée
    // (entier unique correspondant à la case obtenue dans le sens de la lecture)
    // ncols: le nombre de colonnes dans la carte
    // numberV: le nombre de cases dans la carte
    // cette méthode retourne le chemin le plus court entre la case de départ et la
    // case d'arrivée sous forme d'une liste de sommets
    private static ArrayList<Vertex> Dijkstra(Graph graphe, int startVertexNumber, int endVertexNumber, int nCols,
            int numberV) {
        graphe.vertexList.get(startVertexNumber).timeFromSource = 0;

        ArrayList<Vertex> path = new ArrayList<Vertex>(); // liste des sommets du chemin le plus court

        // on initialise la liste des sommets à visiter
        HashSet<Integer> to_visit = new HashSet<Integer>();
        for (int i = 0; i < numberV; i++) {
            to_visit.add(i);
        }

        // boucle de l'algorithme de Dijkstra
        while (to_visit.contains(endVertexNumber)) {
            double minDistance = Double.POSITIVE_INFINITY;
            int min_v = 0;

            // On parcourt tous les noeuds à visiter pour trouver le noeud avec la distance
            // minimale
            for (int v : to_visit) {
                if (graphe.vertexList.get(v).timeFromSource < minDistance) {
                    minDistance = graphe.vertexList.get(v).timeFromSource;
                    min_v = v;
                }
            }

            // On l'enlève des noeuds à visiter
            to_visit.remove(min_v);

            // On met à jour les distances des voisins du sommet
            for (Edge e : graphe.vertexList.get(min_v).adjacencylist) {
                if (to_visit.contains(e.destination)) {
                    int to_try = e.destination;
                    boolean peutDeplacer = canMove(to_try, graphe.vertexList, numberV);

                    if (peutDeplacer) {
                        double newDistance = graphe.vertexList.get(min_v).timeFromSource + e.weight;
                        if (newDistance < graphe.vertexList.get(to_try).timeFromSource) {
                            graphe.vertexList.get(to_try).timeFromSource = newDistance;
                            graphe.vertexList.get(to_try).prev = graphe.vertexList.get(min_v);
                        }
                    }

                }

            }
        }

        // ajout des sommets au chemin le plus court
        Vertex v = graphe.vertexList.get(endVertexNumber);
        while (v != null) {
            path.add(v);
            v = v.prev;
        }

        return path;

    }


    /**
     * Méthode qui retourne la liste des directions possibles pour le prisonnier
     * @param graphe graphe représentant le labyrinthe
     * @param startVertexNumber numéro du sommet du prisonnier
     * @param endVertexNumber numéro du sommet de la sortie
     * @param nLines nombre de lignes
     * @param nCols nombre de colonnes
     * @param numberV nombre de sommets
     * @param res 'A' pour A* et 'D' pour Dijkstra
     * @return liste des directions possibles pour le prisonnier
     */
    public static ArrayList<Character> directionsPossibleForThisInstance(Graph graphe, int startVertexNumber,
            int endVertexNumber, int nLines, int nCols, int numberV, char res) {
        ArrayList<Vertex> path = null;

        if (res == 'A') {
            path = AStar(graphe, startVertexNumber, endVertexNumber, nCols, numberV); // on cherche le chemin le plus
                                                                                      // court avec l'algorithme A*
        }
        if (res == 'D') {
            path = Dijkstra(graphe, startVertexNumber, endVertexNumber, nCols, numberV); // on cherche le chemin le plus
                                                                                         // court avec l'algorithme de
                                                                                         // Dijkstra
        }

        ArrayList<Character> directions = new ArrayList<Character>(); // liste des directions possibles pour le
                                                                      // prisonnier

        // on parcourt le chemin le plus court et on ajoute les directions possibles
        // pour le prisonnier
        for (int i = 0; i < path.size() - 1; i++) {
            Vertex v1 = path.get(i);
            Vertex v2 = path.get(i + 1);

            if (v1.x == v2.x && v1.y == v2.y - 1) {
                directions.add('B'); // déplacement vers le bas
            } else if (v1.x == v2.x && v1.y == v2.y + 1) {
                directions.add('T'); // déplacement vers le haut
            } else if (v1.x == v2.x - 1 && v1.y == v2.y) {
                directions.add('R'); // déplacement vers la droite
            } else if (v1.x == v2.x + 1 && v1.y == v2.y) {
                directions.add('L'); // déplacement vers la gauche
            } else {
                return directions; // si le chemin n'est pas valide, on retourne la liste des directions possibles
                                   // pour le prisonnier
            }
        }

        return directions; // on retourne la liste des directions possibles pour le prisonnier

    }

    /**
     * Méthode qui exécute une instance du jeu
     * @param graphe graphe représentant le labyrinthe
     * @param startVertexNumber numéro du sommet de départ du prisonnier
     * @param endVertexNumber numéro du sommet de la sortie
     * @param nLines nombre de lignes
     * @param nCols nombre de colonnes
     * @param res 'A' pour A* et 'D' pour Dijkstra
     * @return 'Y' si le prisonnier a gagné, 'N' sinon
     */
    public static char runInstance(Graph graphe, int startVertexNumber, int endVertexNumber, int nLines, int nCols,
            char res) {
        int turn = 0;
        ArrayList<Character> directions = directionsPossibleForThisInstance(graphe, startVertexNumber, endVertexNumber,
                nLines, nCols, graphe.vertexList.size(), res);

        while (turn < nLines * nCols) {

            // mettre à jour les case 'A' en 'F' pour la propagation du feu
            for (int i = 0; i < graphe.vertexList.size(); i++) {
                if (graphe.vertexList.get(i).vertexName == 'A') {
                    graphe.vertexList.get(i).vertexName = 'F';
                }
            }

            // propagation du feu
            for (int i = 0; i < graphe.vertexList.size(); i++) {
                if (graphe.vertexList.get(i).vertexName == 'F') {
                    boolean dead = burnAround(i, graphe.vertexList, nLines, nCols);
                    if (dead) {
                        return 'N'; // le prisonnier est mort et 'N' est affiché pour dire ('N' = No = Non) que le
                                    // prisonnier n'a pas gagné
                    }
                }
            }

            // déplacement du prisonnier en fonction de la direction
            if (movePrisoner(directions.get(turn), graphe.vertexList, nLines, nCols, endVertexNumber)) {
                return 'Y'; // le prisonnier a gagné et 'Y' est affiché pour dire ('Y' = Yes = Oui) que le
                            // prisonnier a gagné
            }

            turn++;
        }

        // si le prisonnier n'a pas gagné après nLines*nCols tours, il a perdu
        return 'N'; // le prisonnier n'a pas gagné
    }

}