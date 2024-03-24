package pl.sudoku.view;

import javafx.util.converter.IntegerStringConverter;

public class SudokuConverter extends IntegerStringConverter {

    @Override
    public String toString(Integer value) {
        if (value != null) {
            if (value == 0) {
                return "";
            }
        }
        return super.toString(value);
    }

    @Override
    public Integer fromString(String value) {
        if (value.isEmpty()) {
            return 0;
        }
        return super.fromString(value);
    }
}
