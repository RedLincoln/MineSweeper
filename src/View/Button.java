package View;

import Model.Cell;
import Model.ClickListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Button extends JButton {
    private Cell cell;

    public void nextFlag(boolean state){
        if (state) setIcon(getScaledIcon(ViewImages.FLAG.get()));
        else setIcon(null);
    }

    public Button(Cell cell){
        this.cell = cell;
        this.setFocusable(false);
        this.addActionListener(onClick());
        this.addMouseListener(onRighClick());
        cell.addClickListener(onOpen());
    }

    private MouseListener onRighClick() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) nextFlag(cell.changeFlag());
            }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) {  }

            @Override
            public void mouseExited(MouseEvent e) {  }
        };
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (!isEnabled()){
            setIcon(null);
            if(cell.isMine())
                g.drawImage(getScaledIcon(ViewImages.MINE.get()).getImage(), 0, 0, null);
            else {
                int minesAround = cell.minesAround();
                if (minesAround == 0) return;
                g.setColor(Colors.get(minesAround));
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString(String.valueOf(minesAround), getWidth() / 2 - 3, getHeight() / 2 + 6);
            }
        }
    }

    private ImageIcon getScaledIcon(ImageIcon icon){
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(getWidth(), getHeight(),  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    private ClickListener onOpen() {
        return () -> this.setEnabled(false);
    }

    private ActionListener onClick() {
        return e -> cell.open();
    }

}
