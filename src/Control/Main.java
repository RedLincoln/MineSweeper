package Control;

import Model.Board;
import Model.Level;
import View.BoardDisplay;
import View.DificultySelector;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

public class Main extends JFrame {
    private Board board;
    private static final int paneSize = 30;

    public static void main(String[] args) throws IOException {
        new Main().launch();
    }

    public Main() throws IOException {
        Level level = setDificulty();
        board = new Board(level);
        this.setTitle("MineSweeper");
        this.setSize( level.getCols()*paneSize, level.getRows()* paneSize + 30);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.add(new BoardDisplay(board));
    }


    private Level setDificulty() {
        JDialog gameSelector = new DificultySelector(this);
        gameSelector.setVisible(true);
        return ((DificultySelector) gameSelector).getLevel();
    }

    private void launch() {
        this.setVisible(true);
    }

}
