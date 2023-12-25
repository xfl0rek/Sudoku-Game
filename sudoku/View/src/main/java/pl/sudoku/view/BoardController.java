package pl.sudoku.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.sudoku.*;

import java.io.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class BoardController {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang");
    private Stage window;

    private Parent root;

    BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();

    private SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);

    @FXML
    Button exit = new Button("Exit");

    @FXML
    private GridPane sudokuBoardGrid;

    @FXML
    Button saveGame = new Button("Save game");

    @FXML
    Button loadGame = new Button("Load game");

    public void exit() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("menuStart-view.fxml")), resourceBundle);
        window = (Stage) exit.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    private void fillBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();

                if (sudokuBoard.getValue(i, j) != 0) {
                    textField.setText(String.valueOf(sudokuBoard.getValue(i, j)));
                    textField.setEditable(false);
                    textField.setDisable(true);
                } else {
                    TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
                       String newText = change.getControlNewText();
                       if (newText.matches("[1-9]")) {
                           return change;
                       } else if (change.isDeleted()){
                           return change;
                       } else {
                           return null;
                       }
                    });

                    textField.setTextFormatter(textFormatter);

                    int x = i;
                    int y = j;

                    textField.textProperty().addListener((obs, oldValue, newValue) -> {
                        int value = 0;
                        if (newValue.matches("1-9")) {
                            value = Integer.parseInt(newValue);
                        }
                        sudokuBoard.setValue(x, y, value);
                    });
                }

                textField.setMaxWidth(180);
                textField.setMaxHeight(180);
                textField.setAlignment(Pos.CENTER);
                sudokuBoardGrid.add(textField, i, j);
            }
        }
    }

    public void saveGame() {

    }

    public void loadGame() {

    }

    public void initialize() {
        sudokuBoard.solveGame();
        sudokuBoard.removeFields(MenuStartController.getLevel());
        fillBoard();
    }
}
