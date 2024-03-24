package pl.sudoku;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import pl.sudoku.exceptions.ObjectNullException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class SudokuBoard implements Serializable, Cloneable {
    private final SudokuField[][] board = new SudokuField[9][9];

    private final SudokuSolver sudokuSolver;


    public SudokuBoard(SudokuSolver solver) {
        if (solver == null) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang");
            throw new ObjectNullException(resourceBundle.getString("solverNullException"));
        }
        sudokuSolver = solver;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
        }
    }

    public void solveGame() {
        sudokuSolver.solve(this);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j].setEditable(false);
            }
        }
    }

   public void getBoard(SudokuField[][] tab) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tab[i][j].setFieldValue(board[i][j].getFieldValue());
            }
        }
   }

   public int getValue(int x, int y) {
        return board[x][y].getFieldValue();
   }

   public void setValue(int x, int y, int value) {
        board[x][y].setFieldValue(value);
   }

   public SudokuField getField(int x, int y) {
        return board[x][y];
   }

   public SudokuRow getRow(int y) {
       //SudokuField[] sudokuFields = new SudokuField[9];
       List<SudokuField> sudokuFields = Arrays.asList(new SudokuField[9]);

        for (int i = 0; i < 9; i++) {
            sudokuFields.set(i, board[y][i]);

        }
        return new SudokuRow(sudokuFields);
   }

   public SudokuColumn getColumn(int x) {
       List<SudokuField> sudokuFields = Arrays.asList(new SudokuField[9]);

        for (int i = 0; i < 9; i++) {
            sudokuFields.set(i, board[i][x]);
        }
        return new SudokuColumn(sudokuFields);
   }

   public SudokuBox getBox(int x, int y) {
       List<SudokuField> sudokuFields = Arrays.asList(new SudokuField[9]);

        int boxStartRow = x - x % 3;
        int boxStartColumn = y - y % 3;
        int index = 0;

        for (int w = boxStartRow; w < boxStartRow + 3; w++) {
            for (int k = boxStartColumn; k < boxStartColumn + 3; k++) {
                //sudokuFields[index] = new SudokuField(board[w][k].getFieldValue());
                sudokuFields.set(index, board[w][k]);
                index++;
            }
        }
        return new SudokuBox(sudokuFields);
   }

   private boolean checkBoard() {
       for (int i = 0; i < 9; i++) {
           for (int j = 0; j < 9; j++) {
               if (!getRow(i).verify()) {
                   return false;
               }

               if (!getColumn(i).verify()) {
                   return false;
               }

               if (!getBox(i, j).verify()) {
                   return false;
               }
           }
       }

       return true;
   }

   public boolean isBoardValid() {
        return checkBoard();
   }

   @Override
   public String toString() {
       return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
               .append("board", board)
               .append("sudokuSolver", sudokuSolver)
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

        SudokuBoard rhs = (SudokuBoard) object;

        return new EqualsBuilder()
                .append(board, rhs.board)
                .isEquals();
   }

   @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 43)
                .append(board)
                .toHashCode();
   }

    @Override
    public SudokuBoard clone() {
            SudokuBoard sudokuBoard = new SudokuBoard(this.sudokuSolver);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sudokuBoard.setValue(i, j, this.getValue(i, j));
                }
            }
            return sudokuBoard;
    }

    public void removeFields(GameLevel level) {
        Random random = new Random();
        int fieldsToRemove = level.getValue();

        while (fieldsToRemove > 0) {
            int x = random.nextInt(9);
            int y = random.nextInt(9);

            if (this.getValue(x, y) != 0) {
                this.setValue(x, y, 0);
                this.getField(x, y).setEditable(true);
                fieldsToRemove--;
            }
        }
    }
}