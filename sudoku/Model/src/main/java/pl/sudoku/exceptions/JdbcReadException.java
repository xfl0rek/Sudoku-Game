package pl.sudoku.exceptions;

public class JdbcReadException extends DaoException {

    public JdbcReadException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
