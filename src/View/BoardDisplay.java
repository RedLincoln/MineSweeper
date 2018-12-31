package View;

import Control.Observer;
import Model.Board;

import javax.swing.*;
import java.awt.*;

public class BoardDisplay extends JPanel implements Observer {
    private Board board;

    public BoardDisplay(Board board){
        this.board = board;
        this.setPreferredSize(ViewDimensions.BOARD.get());
        addButtons();
    }
    
    private void addButtons() {
        this.setLayout(new GridLayout(board.getRows(), board.getCols()));
        for (int i = 0; i < board.getRows(); i++)
            for (int j = 0; j < board.getCols(); j++)
                this.add(new Button(board.cellAt(i, j)));
    }

    public void disableAll(){
        Component[] buttons = this.getComponents();
        for (int i = 0; i < buttons.length; i++)
            buttons[i].setEnabled(false);
    }

    @Override
    public void reset() {
        this.removeAll();
        addButtons();
        this.revalidate();
        this.repaint();
    }
}
