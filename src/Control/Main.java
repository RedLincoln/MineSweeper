package Control;

import Model.Board;
import Model.Level;
import View.BoardDisplay;
import View.DifficultySelector;

import javax.swing.*;

public class Main extends JFrame {
    private Board board;
    private BoardDisplay boardDisplay;
    private static final int blockSize = 30;
    private Level level;


    public static void main(String[] args) {
        new Main().launch();
    }

    public Main(){
        newGame();
        this.setTitle("MineSweeper");
        this.setSize( level.getCols()*blockSize, level.getRows()* blockSize + 30);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.add(boardDisplay);
    }


    private void newGame() {
        level = setDificulty();
        board = new Board(level);
        board.addGameCompletedListener((e) -> {
            if (!e)
                JOptionPane.showMessageDialog(this, "OH! You lose",
                        "Game Over", JOptionPane.INFORMATION_MESSAGE);

            else
                JOptionPane.showMessageDialog(this, "Congratulation! You Win",
                        "Congratulation!!!!", JOptionPane.INFORMATION_MESSAGE);
            reset();
        });
        boardDisplay = new BoardDisplay(board);
    }

    private void reset(){
        board.reset();
        boardDisplay.reset();
    }

    private Level setDificulty() {
        JDialog gameSelector = new DifficultySelector(this);
        gameSelector.setVisible(true);
        return ((DifficultySelector) gameSelector).getLevel();
    }

    private void launch() {
        this.setVisible(true);
    }
}
