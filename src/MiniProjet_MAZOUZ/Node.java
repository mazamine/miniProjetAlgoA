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
     * Constructeur pour Node.
     *
     * @param x la coordonnée x
     * @param y la coordonnée y
     * @param cost le coût pour atteindre ce nœud
     * @param heuristic la valeur heuristique pour ce nœud
     * @param parent le nœud parent
     */
    public Node(int x, int y, int cost, int heuristic, Node parent) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.heuristic = heuristic;
        this.parent = parent;
    }

    /**
     * Obtient la coordonnée x.
     *
     * @return la coordonnée x
     */
    public int getX() {
        return x;
    }

    /**
     * Obtient la coordonnée y.
     *
     * @return la coordonnée y
     */
    public int getY() {
        return y;
    }

    /**
     * Obtient le coût pour atteindre ce nœud.
     *
     * @return le coût
     */
    public int getCost() {
        return cost;
    }

    /**
     * Obtient la valeur heuristique pour ce nœud.
     *
     * @return la valeur heuristique
     */
    public int getHeuristic() {
        return heuristic;
    }

    /**
     * Obtient le nœud parent.
     *
     * @return le nœud parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Définit le nœud parent.
     *
     * @param parent le nœud parent
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * Obtient le coût total (coût + heuristique).
     *
     * @return le coût total
     */
    public int getTotalCost() {
        return cost + heuristic;
    }

    /**
     * Obtient la valeur g.
     *
     * @return la valeur g
     */
    public double getG() {
        return g;
    }

    /**
     * Définit la valeur g.
     *
     * @param g la valeur g
     */
    public void setG(double g) {
        this.g = g;
    }

    /**
     * Obtient la valeur h.
     *
     * @return la valeur h
     */
    public double getH() {
        return h;
    }

    /**
     * Définit la valeur h.
     *
     * @param h la valeur h
     */
    public void setH(double h) {
        this.h = h;
    }

    /**
     * Obtient la valeur f (g + h).
     *
     * @return la valeur f
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
