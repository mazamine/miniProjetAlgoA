
import java.util.*;

public class AStarSearch {

    private final Maze maze;
    private final PriorityQueue<Node> openSet;
    private final Set<Node> closedSet;

    public AStarSearch(Maze maze) {
        this.maze = maze;
        this.openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
        this.closedSet = new HashSet<>();
    }

    public List<Node> findPath(Node start, Node goal) {
        openSet.add(start);

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

    private boolean isFireAt(Node node) {
        for (int[] firePosition : maze.getFirePositions()) {
            if (firePosition[0] == node.getX() && firePosition[1] == node.getY()) {
                return true;
            }
        }
        return false;
    }

    private double distance(Node a, Node b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    private double heuristic(Node a, Node b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

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
