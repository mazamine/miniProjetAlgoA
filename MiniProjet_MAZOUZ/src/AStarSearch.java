
import java.util.*;

public class AStarSearch {

    private final Maze maze;
    private final PriorityQueue<Node> openSet;
    private final Set<Node> closedSet;

    /**
     * Constructor for AStarSearch.
     *
     * @param maze the maze
     */
    public AStarSearch(Maze maze) {
        this.maze = maze;
        this.openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
        this.closedSet = new HashSet<>();
    }

    /**
     * Finds the shortest path from start to goal using A* search.
     *
     * @param start the start node
     * @param goal the goal node
     * @return the list of nodes representing the shortest path
     */
    public List<Node> findPath(Node start, Node goal) {
        openSet.add(start);
        start.setG(0);
        start.setH(heuristic(start, goal));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(goal)) {
                return reconstructPath(current);
            }

            closedSet.add(current);

            for (Node neighbor : getNeighbors(current)) {
                if (closedSet.contains(neighbor) || isFireAt(neighbor)) {
                    continue;
                }

                double tentativeGScore = current.getG() + distance(current, neighbor);

                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                } else if (tentativeGScore >= neighbor.getG()) {
                    continue;
                }

                neighbor.setG(tentativeGScore);
                neighbor.setH(heuristic(neighbor, goal));
                neighbor.setParent(current);
            }
        }

        return Collections.emptyList(); // No path found
    }

    /**
     * Gets the neighbors of the given node.
     *
     * @param node the node
     * @return the list of neighbor nodes
     */
    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int x = node.getX();
        int y = node.getY();

        if (maze.isValidMove(x - 1, y)) {
            neighbors.add(new Node(x - 1, y, node.getCost() + 1, 0, node));
        }
        if (maze.isValidMove(x + 1, y)) {
            neighbors.add(new Node(x + 1, y, node.getCost() + 1, 0, node));
        }
        if (maze.isValidMove(x, y - 1)) {
            neighbors.add(new Node(x, y - 1, node.getCost() + 1, 0, node));
        }
        if (maze.isValidMove(x, y + 1)) {
            neighbors.add(new Node(x, y + 1, node.getCost() + 1, 0, node));
        }

        return neighbors;
    }

    /**
     * Checks if there is fire at the given node.
     *
     * @param node the node
     * @return true if there is fire at the node, false otherwise
     */
    private boolean isFireAt(Node node) {
        for (int[] firePosition : maze.getFirePositions()) {
            if (firePosition[0] == node.getX() && firePosition[1] == node.getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the distance between two nodes.
     *
     * @param a the first node
     * @param b the second node
     * @return the distance between the nodes
     */
    private double distance(Node a, Node b) {
        return 1; // Since we are moving in a grid, the distance between adjacent nodes is always 1
    }

    /**
     * Calculates the heuristic value between two nodes.
     *
     * @param a the first node
     * @param b the second node
     * @return the heuristic value
     */
    private double heuristic(Node a, Node b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()); // Manhattan distance
    }

    /**
     * Reconstructs the path from the goal node to the start node.
     *
     * @param current the goal node
     * @return the list of nodes representing the path
     */
    private List<Node> reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.getParent();
        }
        Collections.reverse(path);
        return path;
    }
}
