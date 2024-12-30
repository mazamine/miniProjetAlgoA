package MiniProjet_MAZOUZ;

public class Node {

    private int x;
    private int y;
    private int cost;
    private int heuristic;
    private Node parent;
    private double g;
    private double h;

    /**
     * Constructor for Node.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param cost the cost to reach this node
     * @param heuristic the heuristic value for this node
     * @param parent the parent node
     */
    public Node(int x, int y, int cost, int heuristic, Node parent) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.heuristic = heuristic;
        this.parent = parent;
    }

    /**
     * Gets the x-coordinate.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the cost to reach this node.
     *
     * @return the cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets the heuristic value for this node.
     *
     * @return the heuristic value
     */
    public int getHeuristic() {
        return heuristic;
    }

    /**
     * Gets the parent node.
     *
     * @return the parent node
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Sets the parent node.
     *
     * @param parent the parent node
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Gets the total cost (cost + heuristic).
     *
     * @return the total cost
     */
    public int getTotalCost() {
        return cost + heuristic;
    }

    /**
     * Gets the g value.
     *
     * @return the g value
     */
    public double getG() {
        return g;
    }

    /**
     * Sets the g value.
     *
     * @param g the g value
     */
    public void setG(double g) {
        this.g = g;
    }

    /**
     * Gets the h value.
     *
     * @return the h value
     */
    public double getH() {
        return h;
    }

    /**
     * Sets the h value.
     *
     * @param h the h value
     */
    public void setH(double h) {
        this.h = h;
    }

    /**
     * Gets the f value (g + h).
     *
     * @return the f value
     */
    public double getF() {
        return g + h;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}
