package pl.sudoku;

import pl.sudoku.exceptions.DaoException;

public class SudokuBoardDaoFactory {
    public Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public Dao<SudokuBoard> getJdbcDao(String boardName) throws DaoException {
        return new JdbcSudokuBoardDao(boardName);
    }
}