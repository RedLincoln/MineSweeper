package Model;

import java.util.*;

public class Board {
    private List<Cell> cells;
    private Set<Integer> mines;
    private Set<Integer> openedCells;
    private int rows;
    private int cols;
    private int nMines;
    private final Random random = new Random();

    public Board(int rows, int cols, int nMines) {
        this.rows = rows;
        this.cols = cols;
        this.nMines = nMines;
        createBoard();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    private void createBoard() {
        cells = new ArrayList<>();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                cells.add(cell(i,j));
    }

    public Cell cellAt(int i, int j) {
        return cells.get(index(i,j));
    }

    private boolean outOfBounds(int i, int j) {
        return (i < 0) || (i >= rows) || (j < 0) || (j >= cols);
    }

    private Integer index(int i, int j) {
        return (!outOfBounds(i, j)) ? (i * cols + j) : null;
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
                if (openedCells.contains(index(i, j))) return;
                this.listener.execute();
                openedCells.add(index(i, j));
                if ( countMinesAround() > 0) return;
                openNeighbors();
            }

            private void openNeighbors() {
                List<Cell> neighbors =  neighbors();
                for (Cell cell : neighbors) {
                    cell.open();
                }
            }

            public int countMinesAround(){
                if (isMine()) return -1;
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
            public void addCellOpenListener(CellOpenListener CellOpenListener) {
                this.listener = CellOpenListener;
            }
        };
    }

    private void addMines(int i, int j){
        mines =  new HashSet<>();
        openedCells =  new HashSet<>();
        while (mines.size() < nMines){
            int value =  random.nextInt(rows*cols);
            if (value == index(i,j))continue;
            mines.add(value);
        }
    }

}
