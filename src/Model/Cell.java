package Model;

import java.util.List;

public interface Cell {
    boolean isMine();
    void open();
    List<Cell> neighbors();

    int countMinesAround();

    interface CellOpenListener{
        void execute();
    }

    void addCellOpenListener(CellOpenListener CellOpenListener);


}
