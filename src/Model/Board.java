package Model;

import java.util.*;

public class Board {
    private List<Cell> cells;
    private Set<Integer> mines;
    private Set<Integer> openedCells;
    private final Level level;
    private final Random random = new Random();
    private GameCompletedListener gameCompletedListener;

    public Board(Level level) {
        this.level = level;
        createBoard();
    }

    public int getRows() {
        return level.getRows();
    }

    public int getCols() {
        return level.getCols();
    }

    private void createBoard() {
        cells = new ArrayList<>();
        for (int i = 0; i < level.getRows(); i++)
            for (int j = 0; j < level.getCols(); j++)
                cells.add(cell(i,j));
    }

    public Cell cellAt(int i, int j) {
        return cells.get(index(i,j));
    }

    private boolean outOfBounds(int i, int j) {
        return (i < 0) || (i >= level.getRows()) || (j < 0) || (j >= level.getCols());
    }

    private Integer index(int i, int j) {
        return (!outOfBounds(i, j)) ? (i * level.getCols() + j) : null;
    }


    private Cell cell(int i, int j) {
        return new Cell() {
            private CellOpenListener listener;

            @Override
            public boolean isMine() {
                return mines.contains(index(i, j));
            }

            @Override
            public void open() {
                if (openedCells == null) addMines(i, j);
                if (isMine())
                    gameOver();
                else {
                    openCellNeighbors();
                    if (mines.size() + openedCells.size() == cells.size())
                        gameWon();
                }
            }

            public void openCellNeighbors() {
                if (openedCells.contains(index(i, j))) return;
                this.disable();
                if (this.countMinesAround() != 0) return;
                for ( Cell cell: this.neighbors())
                    cell.openCellNeighbors();
            }

            @Override
            public void disable() {
                listener.execute();
                openedCells.add(index(i, j));
            }

            public int countMinesAround(){
                List<Cell> neighbors =  neighbors();
                int count = 0;
                for (Cell cell : neighbors) {
                    if (cell.isMine()) count++;
                }
                return count;

            }

            private Cell get(int i , int j){
                if (outOfBounds(i, j)) return null;
                return cells.get(index(i,j));
            }

            @Override
            public List<Cell> neighbors() {
                List<Cell> neighbors = new ArrayList() {
                    @Override
                    public boolean add(Object o) {
                        if (o == null) return false;
                        return super.add(o);
                    }
                };
                neighbors.add(get(i, j - 1));
                neighbors.add(get(i, j + 1));
                neighbors.add(get(i + 1, j - 1));
                neighbors.add(get(i + 1, j));
                neighbors.add(get(i + 1, j + 1));
                neighbors.add(get(i - 1, j - 1));
                neighbors.add(get(i - 1, j));
                neighbors.add(get(i - 1, j + 1));
                return neighbors;
            }

            @Override
            public void addCellOpenListener(CellOpenListener cellOpenListener) {
                this.listener = cellOpenListener;
            }

        };
    }

    private void disableAllCells(){
        for (Cell cell: cells ) {
            cell.disable();
        }
    }

    private void gameWon() {
        disableAllCells();
        gameCompletedListener.execute(true);
    }

    private void gameOver(){
        disableAllCells();
        gameCompletedListener.execute(false);
    }

    private void addMines(int i, int j){
        mines =  new HashSet<>();
        openedCells =  new HashSet<>();
        while (mines.size() < level.getMines()){
            int value =  random.nextInt(level.getCols()*level.getRows());
            if (value == index(i,j))continue;
            mines.add(value);
        }
    }

    public void reset() {
        mines = null;
        openedCells = null;
        createBoard();
    }

    public interface GameCompletedListener{
        void execute(boolean state);
    }

    public void addGameCompletedListener(GameCompletedListener gameCompletedListener){
        this.gameCompletedListener = gameCompletedListener;
    }
}
