package pl.sudoku;

public enum GameLevel {
    Easy(20),
    Medium(40),
    Hard(55);

    private final int value;

    GameLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
