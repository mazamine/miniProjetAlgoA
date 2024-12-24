import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AStarSearchTest {
    private AStarSearch aStarSearch;
    private Maze maze;

    @BeforeEach
    void setUp() {
        maze = new Maze();
        maze.loadMaze("src/test/resources/maze.txt"); // Adjust the path as necessary
        aStarSearch = new AStarSearch(maze);
    }

    @Test
    void testPathExists() {
        boolean result = aStarSearch.findPath(maze.getPrisonerStart(), maze.getExit());
        assertTrue(result, "A path should exist for the prisoner to escape.");
    }

    @Test
    void testPathBlockedByFire() {
        maze.simulateFireMovement(); // Simulate fire movement before checking
        boolean result = aStarSearch.findPath(maze.getPrisonerStart(), maze.getExit());
        assertFalse(result, "The path should be blocked by fire.");
    }

    @Test
    void testNoPathAvailable() {
        maze.setObstacle(2, 2); // Set an obstacle in the maze
        boolean result = aStarSearch.findPath(maze.getPrisonerStart(), maze.getExit());
        assertFalse(result, "There should be no path available due to obstacles.");
    }

    @Test
    void testOptimalPathFound() {
        boolean result = aStarSearch.findPath(maze.getPrisonerStart(), maze.getExit());
        assertTrue(result, "The optimal path should be found.");
        // Additional assertions can be added to check the path length or nodes traversed
    }
}