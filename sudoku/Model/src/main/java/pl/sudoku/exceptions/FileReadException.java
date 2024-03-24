package pl.sudoku.exceptions;

public class FileReadException extends DaoException {

    public FileReadException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
