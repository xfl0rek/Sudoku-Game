package pl.sudoku;

import java.util.Arrays;
import java.util.List;

public class SudokuBox extends SudokuVerify {
    SudokuBox(List<SudokuField> sudokuFields) {
        super(sudokuFields);
    }

    @Override
    public SudokuBox clone() {
            SudokuField [] tempSudokuFields = new SudokuField[9];
            for (int i = 0; i < 9; i++) {
                tempSudokuFields[i] = new SudokuField(sudokuFields.get(i).getFieldValue());
            }
            return new SudokuBox(Arrays.asList(tempSudokuFields));
    }


}
