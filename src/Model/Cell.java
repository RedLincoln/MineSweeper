package Model;

import java.util.List;

public interface Cell {
    boolean isMine();
    void open();
    void disable();

    List<Cell> neighbors();

    int countMinesAround();

    void openCellNeighbors();

    interface CellOpenListener{
        void execute();
    }

    void addCellOpenListener(CellOpenListener CellOpenListener);


}
