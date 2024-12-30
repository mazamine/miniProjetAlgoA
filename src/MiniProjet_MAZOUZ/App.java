package MiniProjet_MAZOUZ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class App extends JFrame {

    private Maze maze;
    private JPanel mazePanel;
    private String mazeText; // Store the maze text
    private List<Node> path; // Store the path found by A* search
    private int pathIndex; // Current index in the path
    private PriorityQueue<Node> openSet;
    private Set<Node> closedSet;
    private Node goal;
    private boolean pathFound;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }

    public App() {
        setTitle("Évasion du Labyrinthe");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mazePanel = new JPanel(new GridLayout(0, 1));
        JButton loadMazeButton = new JButton("Charger le Labyrinthe");
        loadMazeButton.addActionListener(e -> showMazeInputWindow());

        JButton nextButton = new JButton("Suivant");
        nextButton.addActionListener(e -> moveToNextStep());

        JPanel controlPanel = new JPanel();
        controlPanel.add(loadMazeButton);
        controlPanel.add(nextButton);

        add(controlPanel, BorderLayout.NORTH);
        add(mazePanel, BorderLayout.CENTER);
    }

    private void showMazeInputWindow() {
        JFrame inputFrame = new JFrame("Entrer le Labyrinthe");
        inputFrame.setSize(300, 300);
        inputFrame.setLocationRelativeTo(this);

        JTextArea inputArea = new JTextArea();
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton submitButton = new JButton("Soumettre");
        submitButton.addActionListener(e -> {
            mazeText = inputArea.getText(); // Store the maze text
            loadMaze(mazeText);
            inputFrame.dispose();
        });

        inputFrame.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        inputFrame.add(submitButton, BorderLayout.SOUTH);
        inputFrame.setVisible(true);
    }

    private void loadMaze(String mazeText) {
        String[] mazeLines = mazeText.split("\n");
        int rows = mazeLines.length;
        int cols = mazeLines[0].length();
        char[][] mazeArray = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            mazeArray[i] = mazeLines[i].toCharArray();
        }

        maze = new Maze(mazeArray);
        updateMazeDisplay();

        // Initialize A* search
        openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
        closedSet = new HashSet<>();
        Node start = maze.getPrisonerPosition();
        goal = maze.getExitPosition();
        start.setG(0);
        openSet.add(start);
        pathFound = false;

        showLoadingWindow();
    }

    private void moveToNextStep() {
        if (pathFound) {
            if (pathIndex < path.size()) {
                Node currentNode = path.get(pathIndex);
                maze.movePrisonerTo(currentNode.getX(), currentNode.getY());
                maze.spreadFire();
                updateMazeDisplay();

                if (maze.getMaze()[maze.getExitX()][maze.getExitY()] == 'F') {
                    showGameOverDialog("Fin de Partie", "La sortie a été brûlée !");
                } else if (maze.getMaze()[currentNode.getX()][currentNode.getY()] == 'F') {
                    showGameOverDialog("Fin de Partie", "Le prisonnier a été attrapé par le feu !");
                } else if (currentNode.equals(maze.getExitPosition())) {
                    showCongratsDialog("Félicitations !", "Le prisonnier s'est échappé !");
                }

                pathIndex++;
            }
        } else {
            if (!openSet.isEmpty()) {
                Node current = openSet.poll();

                if (current.equals(goal)) {
                    path = reconstructPath(current);
                    pathIndex = 0;
                    pathFound = true;
                    return;
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

                updateMazeDisplay();
                try {
                    Thread.sleep(100); // Pause to visualize the search process
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                showGameOverDialog("Fin de Partie", "Aucun chemin trouvé !");
            }
        }
    }

    private void updateMazeDisplay() {
        if (maze != null) {
            mazePanel.removeAll();
            char[][] mazeArray = maze.getMaze();
            mazePanel.setLayout(new GridLayout(mazeArray.length, mazeArray[0].length));
            for (int i = 0; i < mazeArray.length; i++) {
                for (int j = 0; j < mazeArray[i].length; j++) {
                    JLabel cell = new JLabel(String.valueOf(mazeArray[i][j]), SwingConstants.CENTER);
                    cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    mazePanel.add(cell);
                }
            }
            mazePanel.revalidate();
            mazePanel.repaint();
        }
    }

    private void showCongratsDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showGameOverDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
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
        return 1; // Since we are moving in a grid, the distance between adjacent nodes is always 1
    }

    private double heuristic(Node a, Node b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()); // Manhattan distance
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

    private void showLoadingWindow() {
        JFrame loadingFrame = new JFrame("Recherche du chemin le plus court");
        loadingFrame.setSize(300, 100);
        loadingFrame.setLocationRelativeTo(this);

        JLabel loadingLabel = new JLabel("Recherche en cours...", SwingConstants.CENTER);
        loadingFrame.add(loadingLabel);

        loadingFrame.setVisible(true);

        new Thread(() -> {
            findShortestPath();
            loadingFrame.dispose();
            SwingUtilities.invokeLater(() -> {
                showPathWindow();
            });
        }).start();
    }

    private void findShortestPath() {
        Node start = maze.getPrisonerPosition();
        Node goal = maze.getExitPosition();
        start.setG(0);
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(goal)) {
                path = reconstructPath(current);
                pathIndex = 0;
                pathFound = true;
                return;
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

            try {
                Thread.sleep(100); // Pause to visualize the search process
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showPathWindow() {
        JFrame pathFrame = new JFrame("Chemin trouvé");
        pathFrame.setSize(300, 300);
        pathFrame.setLocationRelativeTo(this);

        JTextArea pathArea = new JTextArea();
        pathArea.setLineWrap(true);
        pathArea.setWrapStyleWord(true);
        pathArea.setEditable(false);
        pathArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        StringBuilder pathText = new StringBuilder();
        for (Node node : path) {
            pathText.append("(").append(node.getX()).append(", ").append(node.getY()).append(")\n");
        }
        pathArea.setText(pathText.toString());

        pathFrame.add(new JScrollPane(pathArea), BorderLayout.CENTER);

        pathFrame.setVisible(true);

        Timer timer = new Timer(3000, e -> {
            pathFrame.dispose();
            setVisible(true);
        });
        timer.setRepeats(false);
        timer.start();
    }
}
