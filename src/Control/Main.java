package Control;

import Model.Board;
import View.BoardDisplay;

import javax.swing.*;
import java.io.IOException;

public class Main extends JFrame {
    private Board board;
    private static final int boxSize = 30;

    public static void main(String[] args) throws IOException {
        new Main().launch();
    }

    public Main() throws IOException {
        board = new Board(16,16,40);
        this.setTitle("MineSweeper");
        this.setSize( 16* boxSize, 16*boxSize + 30);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.add(new BoardDisplay(board));
    }

    private void launch() {
        this.setVisible(true);
    }

}
