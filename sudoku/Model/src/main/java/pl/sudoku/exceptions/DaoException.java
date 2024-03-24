package pl.sudoku.exceptions;

public class DaoException extends SudokuException {

    public DaoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
