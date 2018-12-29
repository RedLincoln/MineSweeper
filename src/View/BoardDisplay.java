package View;

import Model.Board;
import Model.Cell;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardDisplay extends JPanel {
    private Board board;

    public BoardDisplay(Board board){
        this.board = board;
        this.setLayout(new GridLayout(board.getRows(), board.getCols()));
        createButtons();
    }

    private void createButtons(){
        for (int i = 0; i < board.getRows(); i++)
            for (int j = 0; j < board.getCols(); j++)
                this.add(button(i, j));
    }

    public ActionListener onClick(Cell cell){
        return e -> cell.open();
    }

    public MouseListener onRighClick(Button button)
    {
        return new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (button.isEnabled() && SwingUtilities.isRightMouseButton(e))
                    button.setIcon(button.getNext());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }



    private JButton button(int i, int j){
        Cell cell = board.cellAt(i, j);
        JButton button = button(cell);
        button.setFocusable(false);
        cell.addCellOpenListener(() -> {
            button.setIcon(null);
            button.setEnabled(false);
            button.repaint();
        });
        button.addActionListener(onClick(cell));
        button.addMouseListener(onRighClick((Button) button));
        return button;
    }

    private JButton button (Cell cell){
        return new Button(cell);
    }

    public void reset() {
        this.removeAll();
        createButtons();
        this.revalidate();
        this.repaint();
    }
}
