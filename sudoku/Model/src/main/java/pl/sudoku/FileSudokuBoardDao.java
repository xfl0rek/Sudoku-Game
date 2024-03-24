package pl.sudoku;

import pl.sudoku.exceptions.FileReadException;
import pl.sudoku.exceptions.FileWriteException;

import java.io.*;
import java.util.ResourceBundle;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    private final String fileName;


    FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws FileReadException {
        SudokuBoard sudokuBoard;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            sudokuBoard = (SudokuBoard) objectInputStream.readObject();
            return sudokuBoard;
        } catch (IOException | ClassNotFoundException exception) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang");
            throw new FileReadException(resourceBundle.getString("fileReadException"), exception);
        }
    }

    @Override
    public void write(SudokuBoard obj) throws FileWriteException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(obj);
        } catch (IOException ioe) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang");
            throw new FileWriteException(resourceBundle.getString("fileWriteException"), ioe);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
