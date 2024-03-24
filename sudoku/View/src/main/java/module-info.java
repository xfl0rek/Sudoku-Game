module pl.sudoku.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;
    requires log4j;
    requires slf4j.api;


    opens pl.sudoku.view to javafx.fxml;
    exports pl.sudoku.view;
}