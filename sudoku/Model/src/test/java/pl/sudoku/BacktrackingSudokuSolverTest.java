package pl.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {

    @Test
    void solveTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        SudokuField[][] testBoard = new SudokuField[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testBoard[i][j] = new SudokuField();
            }
        }

        sudokuBoard.getBoard(testBoard);

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {

                for (int i = row + 1; i < 9; i++) {
                    if (testBoard[row][column] == testBoard[i][column]) {
                        fail();
                    }
                }

                for (int i = column + 1; i < 9; i++) {
                    if (testBoard[row][column] == testBoard[row][i]) {
                        fail();
                    }
                }

                int boxStartRow = row - row % 3;
                int boxStartColumn = column - column % 3;

                for (int i = boxStartRow; i < boxStartRow + 3; i++) {
                    for (int j = boxStartColumn; j < boxStartColumn + 3; j++) {
                        if (testBoard[row][column] == testBoard[i][j] && (i != row && j != column)) {
                            fail();
                        }
                    }
                }
            }
        }
    }

    @Test
    void solveComparisonTest() {
        SudokuField[][] testBoard1 = new SudokuField[9][9];
        SudokuField[][] testBoard2 = new SudokuField[9][9];

        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testBoard1[i][j] = new SudokuField();
                testBoard2[i][j] = new SudokuField();
            }
        }

        sudokuBoard.getBoard(testBoard1);

        sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        sudokuBoard.getBoard(testBoard2);

        boolean areEqual = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (testBoard1[i][j] != testBoard2[i][j]) {
                    areEqual = false;
                    break;
                }
            }
        }

        assertFalse(areEqual);
    }
}