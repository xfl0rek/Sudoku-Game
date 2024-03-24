package pl.sudoku.exceptions;

public class JdbcWriteException extends DaoException {

    public JdbcWriteException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
