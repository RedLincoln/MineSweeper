package View;

import Model.Board;
import Model.Cell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class BoardDisplay extends JPanel {
    private Board board;
    private final BufferedImage mine = ImageIO.read(new File("Icons/mine.png"));

    public BoardDisplay(Board board) throws IOException {
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
        return e -> cell.open();
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


    private JButton button(int i, int j) {
        Cell cell = board.cellAt(i, j);
        JButton button = button(cell);
        button.setFocusable(false);
        cell.addChangeListener(() -> {
            button.setEnabled(false);
            button.repaint();
        });
        button.addActionListener(onClick(cell));
        return button;
   }


    private JButton button (Cell cell){
       return new JButton(){
           @Override
           public void paint(Graphics g) {
               super.paint(g);
               if (!isEnabled()){
                   if (cell.isMine()){
                       g.drawImage(mine,0 ,0,null);
                   }
                   int minesAround = cell.countMinesAround();
                   if (minesAround < 1) return;
                   g.setColor(getColorOf(minesAround));
                   g.setFont(new Font("Arial", Font.BOLD, 20));
                   g.drawString(minesAround + "", getWidth()/2 - 5, getHeight()/2 + 7);
               }
           }
       };
   }
}
