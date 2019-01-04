package Model;

import Control.ChangeListener;
import Control.GameEndedListener;
import Control.ResetComponent;

public interface IBoard extends ResetComponent {
    void openCell(int row, int col);
    void flagCell(int row, int col);
    int getNeighborsMines(int row, int col);
    CellState getCellState(int row, int col);
    Level getLevel();
    void addChangeListener(ChangeListener changeListener);
    void addGameEndedListener(GameEndedListener gameEndedListener);
    int getCellsFlagged();
}
