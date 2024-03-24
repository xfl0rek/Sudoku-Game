package pl.sudoku;

import java.util.Arrays;
import java.util.List;

public class SudokuColumn extends SudokuVerify {
    SudokuColumn(List<SudokuField> sudokuFields) {
        super(sudokuFields);
    }

    @Override
    public SudokuColumn clone() {
        SudokuField [] tempSudokuFields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            tempSudokuFields[i] = new SudokuField(sudokuFields.get(i).getFieldValue());
        }
        return new SudokuColumn(Arrays.asList(tempSudokuFields));
    }
}
