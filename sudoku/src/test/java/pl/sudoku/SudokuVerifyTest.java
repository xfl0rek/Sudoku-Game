package pl.sudoku;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuVerifyTest {
    @Test
    public void VerifyTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        for (int i = 0; i < 9; i++) {
            assertTrue(sudokuBoard.getRow(i).verify());
        }

        for (int i = 0; i < 9; i++) {
            assertTrue(sudokuBoard.getColumn(i).verify());
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertTrue(sudokuBoard.getBox(i, j).verify());
            }
        }

        sudokuBoard.setValue(0,0,1);
        sudokuBoard.setValue(0,1,1);

        assertFalse(sudokuBoard.getRow(0).verify());

        SudokuField [] sudokuField = new SudokuField[5];
        for(int i = 0; i < 5; i++) {
            sudokuField[i] = new SudokuField(i);
        }
        List<SudokuField> sudokuFields = Arrays.asList(new SudokuField[5]);
        for(int i = 0; i < 5; i++) {
//          sudokuFields[i] = new SudokuField(i);
            sudokuFields.set(i,sudokuField[i]);
        }
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new SudokuRow(sudokuFields);
        });
    }

    @Test
    public void toStringTest() {
        SudokuField [] sudokuField = new SudokuField[9];
        for(int i = 0; i < 9; i++) {
            sudokuField[i] = new SudokuField(i);
        }

        List<SudokuField> sudokuFields = Arrays.asList(new SudokuField[9]);
        for(int i = 0; i < 9; i++) {
            sudokuFields.set(i, sudokuField[i]);
        }
        SudokuBox sudokuBox = new SudokuBox(sudokuFields);
        assertNotNull(sudokuBox.toString());
        assertNotEquals(sudokuBox.toString().length(), 0);
    }
}