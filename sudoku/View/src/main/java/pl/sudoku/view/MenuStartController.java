package pl.sudoku.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import pl.sudoku.GameLevel;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuStartController {

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang");

    private static final Logger logger = Logger.getLogger(MenuStartController.class);
    private Stage window;
    private Parent root;
    private static GameLevel level;

    @FXML
    Button easyButton = new Button("Easy");

    @FXML
    Button mediumButton = new Button("Medium");

    @FXML
    Button hardButton = new Button("Hard");

    @FXML
    Button polishButton = new Button("Polish");

    @FXML
    Button englishButton = new Button("English");

    @FXML
    Button quitGameButton = new Button("Quit Game");

    @FXML
    Button authorsButton = new Button("Authors");

    public void easyGame() throws IOException {
        level = GameLevel.Easy;
        logger.info(resourceBundle.getString("chosenDifficulty") + resourceBundle.getString("easy"));
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("board-view.fxml")), resourceBundle);
        window = (Stage) easyButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void mediumGame() throws IOException {
        level = GameLevel.Medium;
        logger.info(resourceBundle.getString("chosenDifficulty") + resourceBundle.getString("medium"));
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("board-view.fxml")), resourceBundle);
        window = (Stage) mediumButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void hardGame() throws IOException {
        level = GameLevel.Hard;
        logger.info(resourceBundle.getString("chosenDifficulty") + resourceBundle.getString("hard"));
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("board-view.fxml")), resourceBundle);
        window = (Stage) hardButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setPolishLanguage() throws IOException {
        Locale.setDefault(new Locale("pl", "PL"));
        resourceBundle = ResourceBundle.getBundle("Lang");
        logger.info(resourceBundle.getString("changeLanguage") + resourceBundle.getString("polish"));
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("menuStart-view.fxml")), resourceBundle);
        window = (Stage) polishButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void setEnglishLanguage() throws IOException {
        Locale.setDefault(new Locale("en", "EN"));
        resourceBundle = ResourceBundle.getBundle("Lang");
        logger.info(resourceBundle.getString("changeLanguage") + resourceBundle.getString("english"));
        root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource("menuStart-view.fxml")), resourceBundle);
        window = (Stage) englishButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    public void authorsButton() {
        ResourceBundle listBundle = ResourceBundle.getBundle("pl.sudoku.view.Authors");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(listBundle.getObject("Title").toString());
        alert.setContentText(listBundle.getObject("Authors") + "\n" + listBundle.getObject("Author1")
                + "\n" + listBundle.getObject("Author2"));
        alert.setHeaderText(null);
        alert.show();
    }

    public void quitGame() {
        logger.info(resourceBundle.getString("quitInfo"));
        window = (Stage) quitGameButton.getScene().getWindow();
        window.close();
    }

    public static GameLevel getLevel() {
        return level;
    }
}
