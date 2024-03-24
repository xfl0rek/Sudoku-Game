package pl.sudoku;

public class SudokuBoardRepository {
    private final SudokuBoard prototype;

    SudokuBoardRepository(SudokuBoard sudokuBoard) {
        this.prototype = sudokuBoard;
    }

    public SudokuBoard createInstance() {
        return prototype.clone();
    }
}
