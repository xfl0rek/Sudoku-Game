package pl.sudoku.view;

import java.util.ListResourceBundle;

public class Authors_en_EN extends ListResourceBundle {

    private static final Object[][] contents = {
            {"Title", "Authors"},
            {"Authors", "Authors of the application: "},
            {"Author1", "Wojciech Florczak"},
            {"Author2", "Jakub Poddemski"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
