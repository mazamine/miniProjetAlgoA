package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private char[][] maze;
    private int rows;
    private int cols;
    private int prisonerX;
    private int prisonerY;
    private int exitX;
    private int exitY;
    private List<int[]> firePositions;

    public Maze(String filePath) {
        loadMaze(filePath);
    }

    private void loadMaze(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            List<char[]> mazeList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                mazeList.add(line.toCharArray());
            }
            rows = mazeList.size();
            cols = mazeList.get(0).length;
            maze = new char[rows][cols];
            firePositions = new ArrayList<>();

            for (int i = 0; i < rows; i++) {
                maze[i] = mazeList.get(i);
                for (int j = 0; j < cols; j++) {
                    if (maze[i][j] == 'P') {
                        prisonerX = i;
                        prisonerY = j;
                    } else if (maze[i][j] == 'E') {
                        exitX = i;
                        exitY = j;
                    } else if (maze[i][j] == 'F') {
                        firePositions.add(new int[]{i, j});
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidMove(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] != '#';
    }

    public char[][] getMaze() {
        return maze;
    }

    public int getPrisonerX() {
        return prisonerX;
    }

    public int getPrisonerY() {
        return prisonerY;
    }

    public int getExitX() {
        return exitX;
    }

    public int getExitY() {
        return exitY;
    }

    public List<int[]> getFirePositions() {
        return firePositions;
    }
}