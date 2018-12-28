package View;

import Model.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Button extends JButton {
    private final BufferedImage mine = ImageIO.read(new File("Icons/mine.png"));

    private Cell cell;
    private ImageIcon[] image = {null
            ,new ImageIcon(getClass().getResource("Icons/flag.png"))
            ,new ImageIcon(getClass().getResource("Icons/question.png"))};
    private int index = 0;

    public Button(Cell cell) throws IOException {
        this.cell = cell;
    }

    public ImageIcon getNext(){
        index = (index + 1) % 3;
        return image[index];
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
        if (!isEnabled()){
            if (cell.isMine()){
                g.drawImage(scaleTobutton(mine, this),0 ,0,null);
            }
            int minesAround = cell.countMinesAround();
            if (minesAround < 1) return;
            g.setColor(getColorOf(minesAround));
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(minesAround + "", getWidth()/2 - 5, getHeight()/2 + 7);
        }
    }
}

