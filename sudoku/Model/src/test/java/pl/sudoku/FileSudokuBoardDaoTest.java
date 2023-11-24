package pl.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FileSudokuBoardDaoTest {

    boolean readAndWriteTestHelper(SudokuBoard sudokuBoard1, SudokuBoard sudokuBoard2) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard1.getValue(i, j) != sudokuBoard2.getValue(i, j)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Test
    public void readAndWriteTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard1.solveGame();
        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        SudokuBoard sudokuBoard2;

        Dao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getFileDao("testFile1");
        sudokuBoardDao.write(sudokuBoard1);

        sudokuBoard2 = sudokuBoardDao.read();

        assertNotNull(sudokuBoard2);

        assertTrue(readAndWriteTestHelper(sudokuBoard1, sudokuBoard2));
    }

    @Test
    public void readExceptionTest() {
        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getFileDao("?");

        assertThrows(RuntimeException.class, () -> {
            sudokuBoardDao.read();
        });
    }

    @Test
    public void writeExceptionTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getFileDao("?");

        assertThrows(RuntimeException.class, () -> {
            sudokuBoardDao.write(sudokuBoard);
        });
    }

    @Test
    public void closeTest() throws Exception {
        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getFileDao("testFile2");
        sudokuBoardDao.close();
    }
}