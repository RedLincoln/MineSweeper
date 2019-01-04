package Model;

import Control.ChangeListener;
import Control.GameEndedListener;

import java.util.*;

public class Board implements IBoard{
    private List<ICell> cells;
    private Set<Integer> mines;
    private Set<Integer> openedCells;
    private Set<Integer> flaggedCell;
    private Level level;
    private static final Random random  =  new Random();
    private ChangeListener changeListener;
    private GameEndedListener gameEndedListener;

    public Board(Level level) {
        this.level = level;
        addCells();
    }

    private int index(int i, int j){
        return i * level.getCols() + j;
    }

    private void addCells() {
        cells = new ArrayList<>();
        openedCells = new HashSet<>();
        flaggedCell = new HashSet<>();
        for (int i = 0; i < level.getRows(); i++)
            for (int j = 0; j < level.getCols(); j++)
                cells.add(cell(i, j));
    }

    @Override
    public void openCell(int row, int col) {
        if (mines == null) addMines(row, col);
        ICell cell = cellAt(row, col);
        cellAt(row, col).open();
        if (cell.isMine())
            gameEndedListener.updateGame(ActionOutcomes.BOMB);
        else if (mines.size() + openedCells.size() == cells.size())
            gameEndedListener.updateGame(ActionOutcomes.OUT_OF_MINES);
    }

    private boolean outOfBounds(int row , int col){
        return (row < 0 || row >= level.getRows() || col  < 0 || col >= level.getCols());
    }

    private ICell cellAt(int row, int col){
        return (outOfBounds(row, col)) ? null : cells.get(index(row, col));
    }

    private void addMines(int row, int col) {
        mines = new HashSet<>();
        int noMineIndex = index(row, col);
        while (mines.size() < level.getMines()){
            int randomIndex = random.nextInt(level.getCols()* level.getRows());
            if (noMineIndex != randomIndex) mines.add(randomIndex);
        }
    }

    @Override
    public void flagCell(int row, int col) {
        cellAt(row, col).toggleFlag();
    }

    @Override
    public int getNeighborsMines(int row, int col) {
        int minesCount = 0;
        for (ICell cell : getNeighbors(row, col))
            minesCount += (cell.isMine()) ? 1 : 0;
        return minesCount;
    }

    @Override
    public CellState getCellState(int row, int col) {
        ICell cell = cellAt(row, col);
        if (!cell.isOpen()){
            return (cell.isFlagged()) ? CellState.FLAGGED : CellState.NO_FLAGGED;
        }else{
            if (cell.isMine()) return CellState.MINE;
            if (getNeighborsMines(row, col) > 0) return CellState.NUM;
            else return CellState.NO_MINE;
        }
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public void addChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public void addGameEndedListener(GameEndedListener gameEndedListener) {
        this.gameEndedListener = gameEndedListener;
    }

    public void openAll() {
        flaggedCell = new HashSet<>();
        for (ICell cell: cells)
            cell.open();
    }


    private List<ICell> getNeighbors(int row, int col){
        List<ICell> neighbors = new ArrayList<>(){
            @Override
            public boolean add(ICell cell) {
                return (cell == null) ? false : super.add(cell);
            }
        };
        neighbors.add(cellAt(row - 1, col - 1));
        neighbors.add(cellAt(row - 1, col));
        neighbors.add(cellAt(row - 1, col + 1));
        neighbors.add(cellAt(row + 1, col - 1));
        neighbors.add(cellAt(row + 1, col));
        neighbors.add(cellAt(row + 1, col + 1));
        neighbors.add(cellAt(row, col - 1));
        neighbors.add(cellAt(row, col + 1));
        return neighbors;
    }

    @Override
    public int getCellsFlagged() {
        return flaggedCell.size();
    }

    private ICell cell(int row , int col){
        return new ICell() {
            @Override
            public void open() {
                if (openedCells.contains(index(row, col))) return;
                openedCells.add(index(row, col));
                flaggedCell.remove(index(row, col));
                changeListener.update(row, col);
                if (getNeighborsMines(row, col) > 0 || isMine()) return;
                openRecursivily();
            }

            private void openRecursivily(){
                for (ICell cell: getNeighbors(row, col))
                    cell.open();
            }

            @Override
            public void toggleFlag() {
                if (flaggedCell.contains(index(row, col)))
                    flaggedCell.remove(index(row, col));
                else if (flaggedCell.size() < level.getMines() && !openedCells.contains(index(row, col)))
                    flaggedCell.add(index(row, col));
                changeListener.update(row, col);
            }

            @Override
            public boolean isOpen() {
                return openedCells.contains(index(row, col));
            }

            @Override
            public boolean isFlagged() {
                return flaggedCell.contains(index(row, col));
            }

            @Override
            public boolean isMine() {
                return mines.contains(index(row, col));
            }
        };
    }

    @Override
    public void reset() {
        mines = null;
        addCells();
    }
}
