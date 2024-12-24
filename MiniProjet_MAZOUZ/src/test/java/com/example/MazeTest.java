import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MazeTest {
    private Maze maze;

    @BeforeEach
    void setUp() {
        maze = new Maze();
    }

    @Test
    void testLoadMaze() {
        maze.loadMaze("src/main/resources/maze.txt");
        assertNotNull(maze.getMazeArray());
        assertEquals(5, maze.getMazeArray().length); // Assuming the maze has 5 rows
        assertEquals(5, maze.getMazeArray()[0].length); // Assuming the maze has 5 columns
    }

    @Test
    void testValidMove() {
        maze.loadMaze("src/main/resources/maze.txt");
        assertTrue(maze.isValidMove(1, 1)); // Assuming (1, 1) is a valid move
        assertFalse(maze.isValidMove(0, 0)); // Assuming (0, 0) is a wall
    }

    @Test
    void testGetPrisonerPosition() {
        maze.loadMaze("src/main/resources/maze.txt");
        int[] position = maze.getPrisonerPosition();
        assertNotNull(position);
        assertEquals(1, position[0]); // Assuming the prisoner starts at row 1
        assertEquals(1, position[1]); // Assuming the prisoner starts at column 1
    }

    @Test
    void testGetExitPosition() {
        maze.loadMaze("src/main/resources/maze.txt");
        int[] exitPosition = maze.getExitPosition();
        assertNotNull(exitPosition);
        assertEquals(4, exitPosition[0]); // Assuming the exit is at row 4
        assertEquals(4, exitPosition[1]); // Assuming the exit is at column 4
    }
}