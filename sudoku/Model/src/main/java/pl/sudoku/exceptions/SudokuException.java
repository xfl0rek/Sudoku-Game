package pl.sudoku.exceptions;

public class SudokuException extends IllegalArgumentException {
    public SudokuException(String message) {
        super(message);
    }

    public SudokuException(String message, Throwable throwable) {
        super(message, throwable);
   }
}
