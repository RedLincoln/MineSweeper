package View;

import Model.Board;
import Model.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BoardDisplay extends JPanel {
    private Board board;

    public BoardDisplay(Board board) {
        this.board = board;
        this.setLayout(new GridLayout(board.getRows(), board.getCols()));
        createButtons();
    }

    private void createButtons() {
        for (int i = 0; i < board.getRows(); i++)
            for (int j = 0; j < board.getCols(); j++)
                this.add(button(i, j));
    }

    public ActionListener onClick(Cell cell){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cell.open();
            }
        };
    }


    private JButton button(int i, int j) {
        JButton button =  new JButton();
        Cell cell = board.cellAt(i, j);
        cell.addChangeListener(new Cell.ChangeListener() {
            @Override
            public void execute() {
                int nMines = cell.countMinesAround();
                button.setText(nMines + " ");
                button.setEnabled(false);
            }
        });
        button.addActionListener(onClick(cell));
        return button;
   }
}
