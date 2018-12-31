package Model;

import Control.GameCompletedListener;
import Control.Observer;

import java.util.*;

public class Board implements Observer {
    private List<Cell> cells;
    private Set<Integer> mines;
    private Set<Integer> openedCells;
    private Set<Integer> flagedCells;
    private Level  level;
    private static final Random random = new Random();
    private GameCompletedListener gameCompletedListener;
    private FlagListener flagListener;

    public Board(Level level){
        this.level = level;
        flagedCells = new HashSet<>();
        addCells();
    }

    public int getRows(){
        return level.getRows();
    }

    public int getCols(){
        return level.getCols();
    }

    private void addCells() {
        cells = new ArrayList<>();
        for (int i = 0; i < level.getRows(); i++)
            for (int j = 0; j < level.getCols(); j++)
                cells.add(cell(i,j));
    }

    private int index(int i , int j){
        return (i * level.getCols() + j);
    }

    private boolean outOfBounds(int i, int j){
        return (i < 0) || (i >= level.getRows()) || (j < 0) || (j >= level.getCols());
    }

    private Cell cell(int i, int j) {
        return new Cell() {
            private ClickListener clickListener;
            private boolean flag = false;

            @Override
            public void open() {
                if (mines == null) createMines( i, j);
                if (openedCells.contains(index(i, j))) return;
                clickListener.execute();
                openedCells.add(index(i, j));
                if (gameFinished(this)) return;
                if (minesAround() == 0) openNeighbors();
            }

            @Override
            public boolean isMine() {
                return mines.contains(index(i, j));
            }

            @Override
            public void addClickListener(ClickListener clickListener) {
                this.clickListener = clickListener;
            }

            @Override
            public boolean changeFlag() {
                flag = !flag;
                flagListener.update(flag);
                return flag;
            }

            private void openNeighbors(){
                for (Cell cell : getNeighbors())
                    cell.open();
            }

            @Override
            public int minesAround(){
                if (isMine()) return -1;
                int count = 0;
                for ( Cell cell: getNeighbors())
                    count += (cell.isMine()) ? 1 : 0;
                return count;
            }

            private List<Cell> getNeighbors(){
                List<Cell> neighbors = new ArrayList<>(){
                    @Override
                    public boolean add(Cell cell) {
                        return (cell == null || cell == cellAt(i, j))? false : super.add(cell);
                    }
                };
                for (int row = -1; row <= 1; row++)
                    for (int col = -1; col <= 1; col++)
                        neighbors.add(cellAt(i + row, j + col));
                return neighbors;
            }
        };
    }

    private boolean gameFinished(Cell cell) {
        if (cell.isMine())
            gameCompletedListener.update(false);
        else if (mines.size() + openedCells.size() == cells.size())
            gameCompletedListener.update(true);
        else
            return false;
        return true;
    }

    private void createMines(int i , int j) {
        mines = new HashSet<>();
        openedCells = new HashSet<>();
        while (mines.size() < level.getMines()){
            int index = random.nextInt(level.getCols()*level.getRows());
            if (index == index(i, j))continue;
            mines.add(index);
        }
    }

    public Cell cellAt(int i, int j) {
        return (outOfBounds(i, j))? null : cells.get(index(i, j));
    }

    public void addGameCompletedListener(GameCompletedListener gameCompletedListener){
        this.gameCompletedListener = gameCompletedListener;
    }

    @Override
    public void reset() {
        mines = null;
        openedCells = null;
        addCells();
    }

    public void addFlagListener (FlagListener flagListener){
        this.flagListener = flagListener;
    }

    public int getMines() { return level.getMines(); }
}
