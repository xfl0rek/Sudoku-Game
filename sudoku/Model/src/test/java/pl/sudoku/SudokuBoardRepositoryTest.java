package pl.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardRepositoryTest {
    @Test
    public void createInstanceTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        SudokuBoardRepository sudokuBoardRepository = new SudokuBoardRepository(sudokuBoard);

        SudokuBoard sudokuBoard1 = sudokuBoardRepository.createInstance();

        assertNotNull(sudokuBoard1);
        assertEquals(sudokuBoard, sudokuBoard1);
        assertNotSame(sudokuBoard, sudokuBoard1);
    }
}