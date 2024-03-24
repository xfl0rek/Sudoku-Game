package pl.sudoku;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import pl.sudoku.exceptions.ListInvalidException;
import pl.sudoku.exceptions.ListNullException;

import java.util.List;
import java.util.ResourceBundle;

public abstract class SudokuVerify implements Cloneable {

    protected final List<SudokuField> sudokuFields;

    public SudokuVerify(List<SudokuField> sudokuFields) {
        if (sudokuFields == null) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang");
            throw new ListNullException(resourceBundle.getString("listNullException"));
        }

        if (sudokuFields.size() != 9) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang");
            throw new ListInvalidException(resourceBundle.getString("listInvalidException"));
        }

        this.sudokuFields = sudokuFields;
    }

    public boolean verify() {
        for (int w = 0; w < sudokuFields.size(); w++) {
            for (int k = w + 1; k < sudokuFields.size(); k++) {
                if (sudokuFields.get(w).getFieldValue() == sudokuFields.get(k).getFieldValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("sudokuFields", sudokuFields)
                .toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (object == this) {
            return true;
        }

        if (object.getClass() != getClass()) {
            return false;
        }

        SudokuVerify rhs = (SudokuVerify) object;

        return new EqualsBuilder()
                .append(sudokuFields, rhs.sudokuFields)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13,43).append(sudokuFields).toHashCode();
    }
}
