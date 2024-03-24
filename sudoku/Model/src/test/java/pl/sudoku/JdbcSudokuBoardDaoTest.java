package pl.sudoku;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import pl.sudoku.exceptions.JdbcReadException;
import pl.sudoku.exceptions.JdbcWriteException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSudokuBoardDaoTest {

    @Test
    void WriteReadTest() {
        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        String generatedName = RandomStringUtils.randomAlphabetic(5);

        try (Dao<SudokuBoard> file1 = sudokuBoardDaoFactory.getJdbcDao(generatedName);
             Dao<SudokuBoard> file2 = sudokuBoardDaoFactory.getJdbcDao("test")) {
            BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
            SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
            SudokuBoard sudokuBoard2;
            sudokuBoard.solveGame();

            assertDoesNotThrow(() -> {
               file1.write(sudokuBoard);
            });

            sudokuBoard2 = file1.read();

            assertEquals(sudokuBoard, sudokuBoard2);
            assertNotSame(sudokuBoard, sudokuBoard2);

            sudokuBoard2 = file2.read();

            assertEquals(sudokuBoard2, file2.read());
            assertNotSame(sudokuBoard2, file2.read());

            assertThrows(JdbcWriteException.class, () -> {
               file2.write(sudokuBoard);
            });

        } catch (Exception e) {

        }
    }

    @Test
    void JdbcReadExceptionTest() {
        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();

        try (Dao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getJdbcDao("?")) {
            assertThrows(JdbcReadException.class, () -> {
               sudokuBoardDao.read();
            });
        } catch (Exception e) {

        }
    }

    @Test
    void JdbcWriteExceptionTest() {
        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        try (Dao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getJdbcDao("test")) {
            assertThrows(JdbcWriteException.class, () -> {
               sudokuBoardDao.write(sudokuBoard);
            });
        } catch (Exception e) {

        }
    }
}