package MiniProjet_MAZOUZ;

import java.util.HashSet;
import java.util.LinkedList;

import MiniProjet_MAZOUZ.WeightedGraph.Graph;

public class AStarSearch {

    private final Maze maze;

    /**
     * Constructeur pour AStarSearch.
     *
     * @param maze le labyrinthe
     */
    public AStarSearch(Maze maze) {
        this.maze = maze;
    }

    /**
     * Trouve le chemin le plus court du départ à l'objectif en utilisant la
     * recherche A*.
     *
     * @param graph le graphe représentant le labyrinthe
     * @param start le nœud de départ
     * @param goal le nœud objectif
     * @param ncols le nombre de colonnes dans le labyrinthe
     * @param numberV le nombre de sommets dans le labyrinthe
     * @return la liste des nœuds représentant le chemin le plus court
     */
    public static LinkedList<Integer> AStar(Graph graph, int start, int goal, int ncols, int numberV) {
        graph.vertexlist.get(start).timeFromSource = 0;
        int number_tries = 0;

        // Ajouter tous les nœuds à l'ensemble des nœuds à visiter
        HashSet<Integer> to_visit = new HashSet<>();
        for (int i = 0; i < numberV; i++) {
            to_visit.add(i);
        }

        // Remplir l'attribut heuristique pour tous les nœuds
        for (WeightedGraph.Vertex v : graph.vertexlist) {
            int vRow = v.num / ncols;
            int vCol = v.num % ncols;

            int endRow = goal / ncols;
            int endCol = goal % ncols;

            // Calculer la distance de Manhattan jusqu'à l'objectif
            v.heuristic = Math.abs(vRow - endRow) + Math.abs(vCol - endCol);
        }

        while (to_visit.contains(goal)) {
            // Trouver le nœud avec la distance temporaire minimale
            double minDistance = Double.POSITIVE_INFINITY;
            int min_v = -1;

            for (int v : to_visit) {
                double totalDistance = graph.vertexlist.get(v).timeFromSource + graph.vertexlist.get(v).heuristic;
                if (totalDistance < minDistance) {
                    minDistance = totalDistance;
                    min_v = v;
                }
            }

            // Retirer le nœud de l'ensemble des nœuds à visiter
            to_visit.remove(min_v);
            number_tries += 1;

            // Vérifier si nous sommes plus rapides en passant par ce nœud
            for (int i = 0; i < graph.vertexlist.get(min_v).adjacencylist.size(); i++) {
                int to_try = graph.vertexlist.get(min_v).adjacencylist.get(i).destination;
                double newTimeFromSource = graph.vertexlist.get(min_v).timeFromSource
                        + graph.vertexlist.get(min_v).adjacencylist.get(i).weight;

                if (newTimeFromSource < graph.vertexlist.get(to_try).timeFromSource) {
                    graph.vertexlist.get(to_try).timeFromSource = newTimeFromSource;
                    graph.vertexlist.get(to_try).prev = graph.vertexlist.get(min_v);
                }
            }
        }

        System.out.println("Terminé ! Utilisation de A* :");
        System.out.println("    Nombre de nœuds explorés : " + number_tries);
        System.out.println("    Temps total du chemin : " + graph.vertexlist.get(goal).timeFromSource);
        LinkedList<Integer> path = new LinkedList<>();
        int current = goal;
        while (current != start) {
            path.addFirst(current);
            if (graph.vertexlist.get(current).prev == null) {
                break;
            }
            current = graph.vertexlist.get(current).prev.num;
        }

        path.addFirst(start);
        return path;
    }
}
