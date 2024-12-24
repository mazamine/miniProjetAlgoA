
import java.util.LinkedList;
import java.util.ArrayList;

// Classe définissant un graphe pondéré.
public class WeightedGraph {

    // Sous-classe pour une arrête.
    static class Edge {

        int source;
        int destination;
        double weight;

        // Constructeur.
        public Edge(int source, int destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    // Sous-classe pour un sommet.
    static class Vertex {

        double indivTime; // Temps individuel pour traverser le sommet
        double timeFromSource; // Temps pour traverser le sommet depuis la source.
        double heuristic; // Temps estimé pour atteindre la destination depuis le sommet.
        Vertex prev; // Sommet précédent dans le chemin le plus court.
        LinkedList<Edge> adjacencylist; // Liste des arrêtes adjacentes.
        int num; // Numéro du sommet.

        int x; // Coordonnée x du sommet.
        int y; // Coordonnée y du sommet.
        char vertexName; // Nom du sommet(une lettre de l'alphabet pour la représentation du labyrinthe sous forme de graphe)

        // Constructeur.
        public Vertex(int num, char vertexName, int x, int y) {
            this.indivTime = Double.POSITIVE_INFINITY;
            this.timeFromSource = Double.POSITIVE_INFINITY;
            this.heuristic = -1;
            this.prev = null;
            this.adjacencylist = new LinkedList<Edge>();
            this.num = num;

            this.x = x;
            this.y = y;
            this.vertexName = vertexName;
        }

        // Ajoute une arrête à la liste des arrêtes adjacentes.
        public void addNeighbor(Edge e) {
            this.adjacencylist.addFirst(e);
        }

        // Cherche un sommet dans un vertexList à partir de ses coordonnées.
        public static int findVertexByXY(int x, int y, ArrayList<Vertex> vertexList) {
            for (int i = 0; i < vertexList.size(); i++) {
                Vertex v = vertexList.get(i);
                if (v.x == x && v.y == y) {
                    return i;
                }
            }
            return -1;
        }
    }

    // Sous-classe pour le graphe.
    static class Graph {

        ArrayList<Vertex> vertexList; // Liste des sommets.
        int num_v = 0; // Nombre de sommets.

        // Constructeur.
        Graph() {
            vertexList = new ArrayList<Vertex>();
        }

        // Ajoute un sommet au graphe.
        public void addVertex(double indivTime, char vertexName, int x, int y) {
            Vertex v = new Vertex(num_v, vertexName, x, y);
            v.indivTime = indivTime;
            vertexList.add(v);
            num_v = num_v + 1;
        }

        // Ajoute une arrête
        public void addEdge(int source, int destination, double weight) {
            Edge edge = new Edge(source, destination, weight);
            vertexList.get(source).addNeighbor(edge);
        }

    }

}
