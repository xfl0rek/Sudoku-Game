module ModelProject {
    requires org.apache.commons.lang3;
    requires log4j;
    requires slf4j.api;
    requires java.sql;
    requires org.postgresql.jdbc;

    exports pl.sudoku;
    exports pl.sudoku.exceptions;
}