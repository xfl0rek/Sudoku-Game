package pl.sudoku.view;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.log4j.Logger;
import pl.sudoku.*;
import pl.sudoku.exceptions.FileReadException;
import pl.sudoku.exceptions.FileWriteException;
import pl.sudoku.exceptions.JdbcReadException;
import pl.sudoku.exceptions.JdbcWriteException;

import java.io.*;
import java.util.*;

public class BoardController {
    private static final Logger logger = Logger.getLogger(BoardController.class);

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang");
    private Stage window;

    private Parent root;

    BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();

    private SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);

    private final SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();

    private final List<ObjectProperty<Integer>> propertyList = new ArrayList<>();

    private final StringConverter<Integer> converter = new SudokuConverter();

    @FXML
    Button exit = new Button("Exit");

    @FXML
    private GridPane sudokuBoardGrid;

    @FXML
    Button saveGame = new Button("Save game");

    @FXML
    Button loadGame = new Button("Load game");

    @FXML
    Button saveToDB = new Button("Save to DB");

    @FXML
    Button loadFromDB = new Button("Load from DB");

    public void exit() throws IOException {
        logger.info(resourceBundle.getString("exitInfo"));
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("menuStart-view.fxml")), resourceBundle);
        window = (Stage) exit.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    private void fillBoard() throws NoSuchMethodException {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();

                TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("[1-9]")) {
                        return change;
                    } else if (change.isDeleted()) {
                        return change;
                    } else {
                        return null;
                    }
                });

                textField.setTextFormatter(textFormatter);

                SudokuField sudokuField = sudokuBoard.getField(i, j);

                if (!sudokuField.isEditable()) {
                    textField.setDisable(true);
                }

                ObjectProperty<Integer> property = JavaBeanIntegerPropertyBuilder
                        .create()
                        .bean(sudokuField)
                        .name("fieldValue")
                        .build()
                        .asObject();

                this.propertyList.add(property);

                textField.textProperty().bindBidirectional(property, converter);

                property.addListener(observable -> {
                    Platform.runLater(() -> {
                        if (isBoardSolved()) {
                            if (sudokuBoard.isBoardValid()) {
                                logger.info(resourceBundle.getString("solved"));
                            } else {
                                logger.info(resourceBundle.getString("notSolved"));
                            }
                        }
                    });
                });

                textField.setMaxWidth(180);
                textField.setMaxHeight(180);
                textField.setAlignment(Pos.CENTER);
                sudokuBoardGrid.add(textField, i, j);
            }
        }
    }

    private boolean isBoardSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard.getValue(i, j) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void saveGame() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser
                .ExtensionFilter("Text Files", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        String fileName = file.getAbsolutePath();
        logger.info(resourceBundle.getString("saveInfo"));

        try (Dao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getFileDao(fileName)) {
            sudokuBoardDao.write(sudokuBoard);
        } catch (Exception exception) {
            logger.error(resourceBundle.getString("savingError"));
            throw new FileWriteException(resourceBundle.getString("fileWriteException"), exception);
        }
    }

    public void loadGame() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser
                .ExtensionFilter("Text Files", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        String fileName = file.getAbsolutePath();
        logger.info(resourceBundle.getString("loadInfo"));

        try (Dao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getFileDao(fileName)) {
            sudokuBoard = sudokuBoardDao.read();
            sudokuBoardGrid.getChildren().clear();
            fillBoard();
        } catch (Exception exception) {
            logger.error(resourceBundle.getString("loadingError"));
            throw new FileReadException(resourceBundle.getString("fileReadException"), exception);
        }
    }

    public void saveToDB() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setHeaderText(resourceBundle.getString("enterName"));

        Optional<String> result = textInputDialog.showAndWait();

        logger.info(resourceBundle.getString("saveInfo"));

        result.ifPresent(inputString -> {
            if (!inputString.isEmpty()) {
                Platform.runLater(() -> {
                    try (Dao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getJdbcDao(inputString)) {
                        sudokuBoardDao.write(sudokuBoard);
                    } catch (Exception exception) {
                        logger.error(resourceBundle.getString("savingError"));
                        throw new JdbcWriteException(resourceBundle.getString("DBWriteException"), exception);
                    }
                });
            }
        });
    }

    public void loadFromDB() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setHeaderText(resourceBundle.getString("enterName"));

        Optional<String> result = textInputDialog.showAndWait();

        logger.info(resourceBundle.getString("loadInfo"));

        result.ifPresent(inputString -> {
            if (!inputString.isEmpty()) {
                try (Dao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getJdbcDao(inputString)) {
                    sudokuBoard = sudokuBoardDao.read();
                    sudokuBoardGrid.getChildren().clear();
                    fillBoard();
                } catch (Exception e) {
                    logger.error(resourceBundle.getString("loadingError"));
                    throw new JdbcReadException(resourceBundle.getString("DBReadException"), e);
                }
            }
        });
    }

    public void initialize() throws NoSuchMethodException {
        sudokuBoard.solveGame();
        sudokuBoard.removeFields(MenuStartController.getLevel());
        fillBoard();
    }
}
