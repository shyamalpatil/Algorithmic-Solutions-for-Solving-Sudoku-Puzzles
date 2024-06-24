import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuSolver {

    private static final int N = 9;

    public static void main(String[] args) {
        int[][] puzzle = readSudokuFromFile("sudoku.txt");
        if (puzzle == null) {
            System.out.println("Failed to read the puzzle.");
            return;
        }

        if (solveSudoku(puzzle)) {
            System.out.println("Solved Sudoku:");
            printSudoku(puzzle);
        } else {
            System.out.println("No solution found.");
        }
    }

    private static int[][] readSudokuFromFile(String filename) {
        int[][] puzzle = new int[N][N];
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null && row < N) {
                String[] values = line.split(" ");
                for (int col = 0; col < N; col++) {
                    puzzle[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return puzzle;
    }

    private static void printSudoku(int[][] grid) {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
    }

    private static boolean solveSudoku(int[][] grid) {
        int[] emptyCell = findEmptyCell(grid);
        if (emptyCell == null) {
            return true; // All cells are filled
        }

        int row = emptyCell[0];
        int col = emptyCell[1];

        for (int num = 1; num <= 9; num++) {
            if (isSafe(grid, row, col, num)) {
                grid[row][col] = num;

                if (solveSudoku(grid)) {
                    return true;
                }

                grid[row][col] = 0; // Backtrack
            }
        }

        return false; // No valid number can be placed
    }

    private static int[] findEmptyCell(int[][] grid) {
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (grid[row][col] == 0) {
                    return new int[] { row, col };
                }
            }
        }
        return null;
    }

    private static boolean isSafe(int[][] grid, int row, int col, int num) {
        return !usedInRow(grid, row, num) && !usedInColumn(grid, col, num) && !usedInBox(grid, row - row % 3, col - col % 3, num);
    }

    private static boolean usedInRow(int[][] grid, int row, int num) {
        for (int col = 0; col < N; col++) {
            if (grid[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean usedInColumn(int[][] grid, int col, int num) {
        for (int row = 0; row < N; row++) {
            if (grid[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean usedInBox(int[][] grid, int boxStartRow, int boxStartCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (grid[row + boxStartRow][col + boxStartCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }
}
