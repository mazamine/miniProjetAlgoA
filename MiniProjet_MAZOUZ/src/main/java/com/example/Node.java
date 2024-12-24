public class Node {
    private int x;
    private int y;
    private int cost;
    private int heuristic;
    private Node parent;

    public Node(int x, int y, int cost, int heuristic, Node parent) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.heuristic = heuristic;
        this.parent = parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCost() {
        return cost;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public Node getParent() {
        return parent;
    }

    public int getTotalCost() {
        return cost + heuristic;
    }
}