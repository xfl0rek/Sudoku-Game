package pl.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameLevelTest {

    @Test
    public void levelTest() {
        GameLevel easyLevel = GameLevel.Easy;
        GameLevel mediumLevel = GameLevel.Medium;
        GameLevel hardLevel = GameLevel.Hard;

        assertEquals(20, easyLevel.getValue());
        assertEquals(40, mediumLevel.getValue());
        assertEquals(55, hardLevel.getValue());
    }
}