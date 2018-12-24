package Model;

import java.util.List;

public interface Cell {
    boolean isMine();
    void open();
    List<Cell> neighbors();

    int countMinesAround();

    interface ChangeListener{
        void execute();
    }

    void addChangeListener(ChangeListener changeListener);


}
