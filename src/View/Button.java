package View;

import Model.CellState;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Button extends JButton{
    private int minesAround;
    private CellState cellState = CellState.NO_FLAGGED;

    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }

    public void setCellState(CellState cellState){
        this.cellState = cellState;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (cellState == CellState.MINE){
            g.drawImage(scaleImage(Images.MINE.getImage()), 0, 0, null);
        }else if (cellState == CellState.NUM){
            g.setFont(new Font("SERIF", Font.BOLD, 16));
            g.setColor(Colors.get(minesAround));
            g.drawString(minesAround+"", getWidth()/2 - 4, getHeight()/2 + 7);
        }else if (cellState == CellState.FLAGGED) {
            g.drawImage(scaleImage(Images.FLAG.getImage()), 0, 0, null);
        }
    }

    private Image scaleImage(BufferedImage image){
        return image.getScaledInstance(getWidth(), getHeight(), 0);
    }
}
