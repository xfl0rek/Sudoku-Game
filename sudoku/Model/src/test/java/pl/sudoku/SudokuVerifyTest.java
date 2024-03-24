package pl.sudoku;

import org.junit.jupiter.api.Test;
import pl.sudoku.exceptions.ListInvalidException;
import pl.sudoku.exceptions.ListNullException;

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
        assertThrows(ListInvalidException.class, () -> {
            new SudokuRow(sudokuFields);
        });

        assertThrows(ListNullException.class, () -> {
            new SudokuColumn(null);
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

    @Test
    public void equalsTest() {
        SudokuField[] sudokuField = new SudokuField[9];

        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();

        for (int i = 0; i < 9; i++) {
            sudokuField[i] = new SudokuField();
            sudokuField[i].setFieldValue(i);
        }

        List<SudokuField> sudokuFields = Arrays.asList(new SudokuField[9]);

        SudokuRow sudokuRow = new SudokuRow(sudokuFields);

        assertFalse(sudokuRow.equals(null));

        assertTrue(sudokuRow.equals(sudokuRow));

        assertFalse(sudokuRow.equals(backtrackingSudokuSolver));

        SudokuField[] sudokuField2 = new SudokuField[9];

        for (int i = 0; i < 9; i++) {
            sudokuField2[i] = new SudokuField();
            sudokuField2[i].setFieldValue(i + 1);
        }

        List<SudokuField> sudokuFields2 = Arrays.asList(sudokuField2);

        SudokuRow sudokuRow2 = new SudokuRow(sudokuFields2);

        assertFalse(sudokuRow.equals(sudokuRow2));
    }
    @Test
    public void hashCodeTest() {
          SudokuRow sudokuRow1 = new SudokuRow(Arrays.asList(new SudokuField[9]));
          SudokuRow sudokuRow2 = new SudokuRow(Arrays.asList(new SudokuField[9]));

          assertEquals(sudokuRow1.hashCode(), sudokuRow2.hashCode());


    }

    @Test
    public void cloneTest() {
        SudokuField [] tab1 = new SudokuField[9];
        for(int i = 0; i < 9; i++) {
            tab1[i] = new SudokuField(i);
        }
        SudokuBox sudokuBox1 = new SudokuBox(Arrays.asList(tab1));
        SudokuBox sudokuBox2 = sudokuBox1.clone();
        assertNotNull(sudokuBox2);
        assertEquals(sudokuBox1, sudokuBox2);
        assertNotSame(sudokuBox1, sudokuBox2);

        SudokuRow sudokuRow1 = new SudokuRow(Arrays.asList(tab1));
        SudokuRow sudokuRow2 = sudokuRow1.clone();
        assertNotNull(sudokuRow2);
        assertEquals(sudokuRow1, sudokuRow2);
        assertNotSame(sudokuRow1, sudokuRow2);

        SudokuColumn sudokuColumn1 = new SudokuColumn(Arrays.asList(tab1));
        SudokuColumn sudokuColumn2 = sudokuColumn1.clone();
        assertNotNull(sudokuColumn2);
        assertEquals(sudokuColumn1, sudokuColumn2);
        assertNotSame(sudokuColumn1, sudokuColumn2);
    }
}

