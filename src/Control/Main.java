package Control;

import Model.Board;
import View.BoardDisplay;

import javax.swing.*;

public class Main extends JFrame {
    private Board board;
    private static final int boxSize = 60;

    public static void main(String[] args) {
        new Main().launch();
    }

    public Main(){
        board = new Board(8,8,10);
        this.setTitle("MineSweeper");
        this.setSize( 8* boxSize, 8*boxSize);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.add(new BoardDisplay(board));
    }

    private void launch() {
        this.setVisible(true);
    }

}
