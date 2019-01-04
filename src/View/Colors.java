package View;

import java.awt.*;

public enum Colors {
    NONE(null), RED(Color.RED),BLUE(Color.BLUE),GREEN(Color.GREEN),BLACK(Color.BLACK),PINK(Color.PINK)
    ,CYAN(Color.CYAN), ORANGE(Color.ORANGE), MAGENTA(Color.MAGENTA);

    private Color color;

    Colors(Color color) {
        this.color = color;
    }

    public static Color get(int index) {
        return (index < 0 || index > 8) ? null : values()[index].color;
    }
}