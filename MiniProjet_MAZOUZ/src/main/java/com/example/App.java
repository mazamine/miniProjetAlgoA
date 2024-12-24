public class App {
    public static void main(String[] args) {
        // Load the maze from the file
        Maze maze = new Maze();
        maze.loadMaze("src/main/resources/maze.txt");

        // Initialize the A* search algorithm
        AStarSearch aStarSearch = new AStarSearch(maze);

        // Find the prisoner's starting position and the exit
        Node start = maze.getPrisonerPosition();
        Node exit = maze.getExitPosition();

        // Execute the A* search algorithm
        boolean canEscape = aStarSearch.findPath(start, exit);

        // Output the result
        if (canEscape) {
            System.out.println("The prisoner can escape!");
        } else {
            System.out.println("The prisoner cannot escape.");
        }
    }
}