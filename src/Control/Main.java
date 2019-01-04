package Control;

import Model.ActionOutcomes;
import Model.Board;
import Model.Level;
import View.BoardDisplay;
import View.InfoDisplay;
import View.LevelSelector;
import View.MenuBar;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private Board board;
    private BoardDisplay boardDisplay;
    private InfoDisplay infoDisplay;
    private MenuBar menuBar;
    private List<ResetComponent> resetComponents = new ArrayList<>();
    private Level level;

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) { }
        new Main().launch();
    }

    public Main() throws HeadlessException {
        selectLevel();
        this.setTitle("MineSweeper");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                gameClosing();
            }
        });
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        setupMenu();
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void setupMenu() {
        menuBar = new MenuBar();
        menuBar.addStartGameListener(() -> selectLevel());
        menuBar.addExitGameListener(() -> gameClosing());
        menuBar.addResetGameListener(() -> reset());
        this.setJMenuBar(menuBar);
    }

    private void gameClosing(){
        JOptionPane.showMessageDialog(this, Messages.gameClosingMessage, "Game Closing", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }

    private void selectLevel() {
        this.getContentPane().removeAll();
        LevelSelector levelSelector = new LevelSelector();
        levelSelector.addStartGameListener(() -> {
            this.level = levelSelector.getLevel();
            newGame();
        });
        this.add(levelSelector);
        this.revalidate();
        this.repaint();
    }

    public void newGame(){
        getContentPane().removeAll();
        createComponents();
        revalidate();
        repaint();
    }

    private void createComponents() {
        board = new Board(level);
        boardDisplay = new BoardDisplay(board);
        infoDisplay = new InfoDisplay(board);
        this.add(infoDisplay);
        this.add(boardDisplay);
        addListeners();
        resetComponents.add(board);
        resetComponents.add(boardDisplay);
        resetComponents.add(infoDisplay);
    }

    private void addListeners(){
        board.addChangeListener((row, col) -> {
            boardDisplay.drawCell(row, col);
            infoDisplay.updateFlagCounter();
        });
        board.addGameEndedListener(actionOutcomes -> {
            infoDisplay.stopTimer();
            board.openAll();
            if (actionOutcomes == ActionOutcomes.BOMB)
                JOptionPane.showMessageDialog(this, Messages.gameLostMessage, "Game Ended", JOptionPane.ERROR_MESSAGE);
            else if (actionOutcomes == ActionOutcomes.OUT_OF_MINES)
                JOptionPane.showMessageDialog(this, Messages.gameWonMessage, "Game Ended", JOptionPane.INFORMATION_MESSAGE);
            reset();
        });
        infoDisplay.addResetGameListener(() -> {
            infoDisplay.stopTimer();
            reset();
        });
    }

    private void launch() {
        this.setVisible(true);
    }

    private void reset(){
        for (ResetComponent resetComponent: resetComponents)
            resetComponent.reset();
    }

}
