package View;

import Control.ResetComponent;
import Model.CellState;
import Model.IBoard;
import Model.Level;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class BoardDisplay extends JPanel implements ResetComponent {
    private List<Button> buttons;
    private final IBoard board;
    private Level level;

    public BoardDisplay(IBoard board) {
        this.board = board;
        this.level = board.getLevel();
        this.setPreferredSize(Dimensions.BOARD.getDimesion());
        addButtons();
    }

    private void addButtons() {
        this.setLayout(new GridLayout(level.getRows(), level.getCols()));
        buttons = new ArrayList<>();
        for (int row = 0; row < level.getRows(); row++)
            for (int col = 0; col < level.getCols(); col++)
                this.add(button(row, col));
    }

    private JButton button(int row, int col) {
        Button button = new Button();
        button.setFocusable(false);
        buttons.add(button);
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e){ }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e))
                    board.openCell(row, col);
                else if (SwingUtilities.isRightMouseButton(e))
                    board.flagCell(row, col);
            }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) { }

            @Override
            public void mouseExited(MouseEvent e) { }
        });
        return button;
    }

    private int index(int row, int col){
        return row * level.getCols() + col;
    }

    public void drawCell(int row, int col){
        CellState cellState = board.getCellState(row, col);
        Button button = buttons.get(index(row, col));
        button.setCellState(cellState);
        if (!(cellState == CellState.NO_FLAGGED) && !(cellState == CellState.FLAGGED)){
            if (cellState == CellState.NUM)
                button.setMinesAround(board.getNeighborsMines(row, col));
            button.setEnabled(false);
        }
        repaint();
    }

    @Override
    public void reset() {
        removeAll();
        addButtons();
        revalidate();
        repaint();
    }
}
