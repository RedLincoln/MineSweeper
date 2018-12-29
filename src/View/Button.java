package View;

import Model.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Button extends JButton {

    private static BufferedImage mine;

    private Cell cell;
    private static final ImageIcon[] icons = new ImageIcon[3];
    private int index = 0;

    static{
        try{
            mine =  ImageIO.read(new File("Icons/mine.png"));
        }catch (Exception e){
            mine = null;
        }
        icons[0] = null;
        icons[1] = new ImageIcon(Button.class.getResource("Icons/flag.png"));
        icons[2] = new ImageIcon(Button.class.getResource("Icons/question.png"));
    }


    public Button(Cell cell) {
        this.cell = cell;
    }

    public ImageIcon getNext(){
        index = (index + 1) % 3;
        return icons[index];
    }

    private Image scaleTobutton(Image image, JButton button){
        return image.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_DEFAULT);
    }

    private Color getColorOf (int neighborsCount){

        switch (neighborsCount){
            case 1: return Color.RED;
            case 2: return Color.BLUE;
            case 3: return Color.GREEN;
            case 4: return Color.BLACK;
            case 5: return Color.PINK;
            case 6: return Color.CYAN;
            case 7: return Color.ORANGE;
            case 8: return Color.MAGENTA;
            default: return null;
        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (!isEnabled()) {
            if (cell.isMine()) {
                g.drawImage(scaleTobutton(mine, this), 0, 0, null);
            } else {
                int minesAround = cell.countMinesAround();
                if (minesAround < 1) return;
                g.setColor(getColorOf(minesAround));
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString(minesAround + "", getWidth() / 2 - 5, getHeight() / 2 + 7);

            }
        }
    }
}

