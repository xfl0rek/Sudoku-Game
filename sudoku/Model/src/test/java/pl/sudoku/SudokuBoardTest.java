package pl.sudoku;

import org.junit.jupiter.api.Test;
import pl.sudoku.exceptions.ObjectNullException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {
    @Test
    void Getter_Setter_Test() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();

        SudokuBoard sudokuBoard1 = new SudokuBoard(backtrackingSudokuSolver);

        sudokuBoard1.setValue(0,0, 1);
        assertEquals(sudokuBoard1.getValue(0,0), 1);

        sudokuBoard1.setValue(0,1, 2);

        int suma = sudokuBoard1.getValue(0,0) + sudokuBoard1.getValue(0,1);
        assertEquals(suma,3);

    }

    @Test
    void Get_Board_Test() {
        SudokuField[][] tab = new SudokuField[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tab[i][j] = new SudokuField();
            }
        }

        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();

        SudokuBoard sudokuBoard1 = new SudokuBoard(backtrackingSudokuSolver);

        sudokuBoard1.solveGame();

        sudokuBoard1.getBoard(tab);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(sudokuBoard1.getValue(i, j), tab[i][j].getFieldValue());
            }
        }
    }

    @Test
    public void solverNullExceptionTest() {
        assertThrows(ObjectNullException.class, () -> {
            new SudokuBoard(null);
        });
    }

    @Test
    public void checkBoardTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        assertTrue(sudokuBoard.isBoardValid());
    }

    @Test
    public void checkBoardInvalidRowTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        sudokuBoard.setValue(0, 0, sudokuBoard.getValue(0, 1));

        assertFalse(sudokuBoard.isBoardValid());
    }

    @Test
    public void checkBoardInvalidColumnTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        sudokuBoard.setValue(1, 0, sudokuBoard.getValue(0, 0));

        assertFalse(sudokuBoard.isBoardValid());
    }

    @Test
    public void checkBoardInvalidBoxTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        sudokuBoard.setValue(1, 1, sudokuBoard.getValue(0, 0));

        assertFalse(sudokuBoard.isBoardValid());
    }

    @Test
    public void toStringTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);

        sudokuBoard.setValue(0,0,1);
        sudokuBoard.setValue(0,1,2);

        assertTrue(sudokuBoard
                .toString()
                .contains("{{1,2,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}," +
                        "{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}," +
                        "{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}}"));
        assertTrue(sudokuBoard
                .toString()
                .contains("pl.sudoku.BacktrackingSudokuSolver@"));

        assertNotNull(sudokuBoard.toString());
        assertNotEquals(sudokuBoard.toString().length(), 0);
    }

    @Test
    public void equalsTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard1.solveGame();

        assertTrue(sudokuBoard1.equals(sudokuBoard1));

        assertFalse(sudokuBoard1.equals(null));

        SudokuBoard sudokuBoard2 = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard2.solveGame();

        assertFalse(sudokuBoard1.equals(sudokuBoard2));

        assertFalse(sudokuBoard1.equals(backtrackingSudokuSolver));
    }

    @Test
    public void hashCodeTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard1.solveGame();

        SudokuBoard sudokuBoard2 = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard2.solveGame();

        assertEquals(sudokuBoard1.hashCode(), sudokuBoard1.hashCode());

        assertNotEquals(sudokuBoard1.hashCode(), sudokuBoard2.hashCode());
    }

    @Test
    public void SudokuBoardCloneTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 =  new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard1.solveGame();
        SudokuBoard sudokuBoard2 = sudokuBoard1.clone();
        assertNotNull(sudokuBoard2);
        assertEquals(sudokuBoard1, sudokuBoard2);
        assertNotSame(sudokuBoard1, sudokuBoard2);
    }

    @Test
    public void removeFieldsTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();
        GameLevel easyLevel = GameLevel.Easy;

        sudokuBoard.removeFields(easyLevel);

        int count = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard.getValue(i, j) == 0) {
                    count++;
                }
            }
        }

        assertEquals(easyLevel.getValue(), count);

        SudokuBoard sudokuBoard2 = new SudokuBoard(backtrackingSudokuSolver);

        sudokuBoard2.solveGame();

        sudokuBoard2.setValue(0, 0, 0);

        sudokuBoard2.removeFields(easyLevel);

        int count2 = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard2.getValue(i, j) == 0) {
                    count2++;
                }
            }
        }

        assertEquals(count2, easyLevel.getValue() + 1);
    }
}