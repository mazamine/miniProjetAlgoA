package MiniProjet_MAZOUZ;

import java.util.HashSet;
import java.util.LinkedList;

import MiniProjet_MAZOUZ.WeightedGraph.Graph;

public class AStarSearch {

    private final Maze maze;

    /**
     * Constructor for AStarSearch.
     *
     * @param maze the maze
     */
    public AStarSearch(Maze maze) {
        this.maze = maze;
    }

    /**
     * Finds the shortest path from start to goal using A* search.
     *
     * @param graph the graph representing the maze
     * @param start the start node
     * @param goal the goal node
     * @param ncols the number of columns in the maze
     * @param numberV the number of vertices in the maze
     * @return the list of nodes representing the shortest path
     */
    public static LinkedList<Integer> AStar(Graph graph, int start, int goal, int ncols, int numberV) {
        graph.vertexlist.get(start).timeFromSource = 0;
        int number_tries = 0;

        // Add all nodes to the set of nodes to visit
        HashSet<Integer> to_visit = new HashSet<>();
        for (int i = 0; i < numberV; i++) {
            to_visit.add(i);
        }

        // Fill the heuristic attribute for all nodes
        for (WeightedGraph.Vertex v : graph.vertexlist) {
            int vRow = v.num / ncols;
            int vCol = v.num % ncols;

            int endRow = goal / ncols;
            int endCol = goal % ncols;

            // Calculate the Manhattan distance to the goal
            v.heuristic = Math.abs(vRow - endRow) + Math.abs(vCol - endCol);
        }

        while (to_visit.contains(goal)) {
            // Find the node with the minimum temporary distance
            double minDistance = Double.POSITIVE_INFINITY;
            int min_v = -1;

            for (int v : to_visit) {
                double totalDistance = graph.vertexlist.get(v).timeFromSource + graph.vertexlist.get(v).heuristic;
                if (totalDistance < minDistance) {
                    minDistance = totalDistance;
                    min_v = v;
                }
            }

            // Remove the node from the set of nodes to visit
            to_visit.remove(min_v);
            number_tries += 1;

            // Check if we are faster by passing through this node
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

        System.out.println("Done! Using A*:");
        System.out.println("    Number of nodes explored: " + number_tries);
        System.out.println("    Total time of the path: " + graph.vertexlist.get(goal).timeFromSource);
        LinkedList<Integer> path = new LinkedList<>();
        int current = goal;
        while (current != start) {
            path.addFirst(current);
            current = graph.vertexlist.get(current).prev.num;
        }

        path.addFirst(start);
        return path;
    }
}
