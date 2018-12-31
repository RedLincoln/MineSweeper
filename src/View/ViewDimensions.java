package View;

import java.awt.*;

public enum ViewDimensions {
    DIALOG(new Dimension(250, 100)),
    BOARD(new Dimension(400, 600)),
    INFO(new Dimension(400, 50));

    private Dimension dimension;

    ViewDimensions(Dimension dimension) {
        this.dimension = dimension;
    }

    public Dimension get() {
        return dimension;
    }
}
