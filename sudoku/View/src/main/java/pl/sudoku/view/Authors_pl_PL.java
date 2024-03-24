package pl.sudoku.view;

import java.util.ListResourceBundle;

public class Authors_pl_PL extends ListResourceBundle {

    private static final Object[][] contents = {
            {"Title", "Autorzy"},
            {"Authors", "Autorzy aplikacji: "},
            {"Author1", "Wojciech Florczak"},
            {"Author2", "Jakub Poddemski"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
