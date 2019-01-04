package View;

import java.awt.*;

public enum Dimensions {
    BOARD(480, 640),
    INFO(480, 60),
    FULLFRAME(480, 700);

    private Dimension dimension;
    Dimensions(int width, int hight) {
        dimension = new Dimension(width, hight);
    }

    public Dimension getDimesion() {
        return dimension;
    }
}
