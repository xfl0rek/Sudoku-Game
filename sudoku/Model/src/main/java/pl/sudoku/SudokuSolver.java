package pl.sudoku;

import java.io.Serializable;

public interface SudokuSolver extends Serializable {
    void solve(SudokuBoard sudokuBoard);
}
