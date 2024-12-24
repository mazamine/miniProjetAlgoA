import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FireMovementTest {
    private FireMovement fireMovement;
    private char[][] maze;

    @BeforeEach
    void setUp() {
        maze = new char[][] {
            {'#', '#', '#', '#', '#'},
            {'#', 'P', '.', 'F', '#'},
            {'#', '.', '.', '.', '#'},
            {'#', 'F', '#', '.', '#'},
            {'#', '#', '#', '#', '#'}
        };
        fireMovement = new FireMovement(maze);
    }

    @Test
    void testFireSpread() {
        fireMovement.spreadFire();
        char[][] expectedMaze = new char[][] {
            {'#', '#', '#', '#', '#'},
            {'#', 'P', 'F', 'F', '#'},
            {'#', 'F', '.', '.', '#'},
            {'#', 'F', '#', '.', '#'},
            {'#', '#', '#', '#', '#'}
        };
        assertArrayEquals(expectedMaze, maze);
    }

    @Test
    void testFireDoesNotSpreadIntoWalls() {
        fireMovement.spreadFire();
        assertEquals('F', maze[1][2]); // Fire should spread to (1,2)
        assertEquals('F', maze[2][1]); // Fire should spread to (2,1)
        assertEquals('F', maze[3][1]); // Fire should spread to (3,1)
        assertEquals('#', maze[0][1]); // Fire should not spread into walls
    }

    @Test
    void testFireDoesNotSpreadOutsideMaze() {
        fireMovement.spreadFire();
        assertEquals('#', maze[0][0]); // Fire should not spread outside the maze
        assertEquals('#', maze[4][0]); // Fire should not spread outside the maze
    }
}