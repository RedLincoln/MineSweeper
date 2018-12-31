package Control;

import Model.Board;
import Model.Level;
import View.BoardDisplay;
import View.GameFinishedDialog;
import View.GameInformationPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private Board board;
    private BoardDisplay boardDisplay;
    private GameInformationPanel infoDisplay;
    private List<Observer> observers = new ArrayList<>();

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) { }
        new Main().launch();
    }

    private void launch() {
        this.setVisible(true);
    }

    public Main(){
        setupBoard();
        this.setTitle("MineSweeper");
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.add(infoDisplay);
        this.add(boardDisplay);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void setupBoard(){
        Level level = new Level("Medium");
        board = new Board(level);
        boardDisplay = new BoardDisplay(board);
        board.addGameCompletedListener(state -> {
            boardDisplay.disableAll();
            infoDisplay.stopTimer();
            if (!state)
                new GameFinishedDialog(this,state).setVisible(true);
            else
                new GameFinishedDialog(this,state).setVisible(true);
            reset();
        });
        infoDisplay = new GameInformationPanel(board);
        infoDisplay.addResetListener(() -> reset());
        addObserver(infoDisplay);
        addObserver(board);
        addObserver(boardDisplay);
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    private void reset(){
        for ( Observer observer: observers )
            observer.reset();
    }

}
