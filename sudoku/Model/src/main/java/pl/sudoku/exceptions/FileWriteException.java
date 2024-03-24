package pl.sudoku.exceptions;

public class FileWriteException extends DaoException {
    public FileWriteException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
