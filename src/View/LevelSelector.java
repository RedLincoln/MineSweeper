package View;

import Control.StartGameListener;
import Model.Level;

import javax.swing.*;
import java.awt.*;

public class LevelSelector extends JPanel {
    private static final int nButtons = 4;
    private StartGameListener startGameListener;
    private Level level;

    public LevelSelector() {
        this.setPreferredSize(Dimensions.FULLFRAME.getDimesion());
        this.setLayout(new GridLayout(nButtons, 1, 0, 80));
        setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        addButtons();
    }


    public Level getLevel() {
        return level;
    }


    private void addButtons() {
        addStandardButtons("Beginner");
        addStandardButtons("Medium");
        addStandardButtons("Expert");
        addCustomButton("Custom");
    }

    private void addStandardButtons(String difficulty){
        JButton button =  new JButton(difficulty);
        button.addActionListener(e -> {
            level = new Level(difficulty);
            startGameListener.startGame();
        });
        this.add(button);
    }

    private void addCustomButton(String difficulty) {
        JButton custom = new JButton(difficulty);
        custom.addActionListener((e) -> setupCustomComponents());
        this.add(custom);
    }

    public void addStartGameListener(StartGameListener startGameListener){
        this.startGameListener = startGameListener;
    }

    private void setupCustomComponents() {
        this.removeAll();
        addCustomComponents();
        this.revalidate();
        this.repaint();
    }

    private void addCustomComponents() {
        JTextField rows = addPanels("Rows: ");
        JTextField cols = addPanels("Cols: ");
        JTextField mines = addPanels("Mines: ");
        JButton ok  = new JButton("OK");
        ok.addActionListener(e -> {
            if (goodParams(rows.getText(), cols.getText(), mines.getText())){
                level = new Level(Integer.parseInt(rows.getText()),
                                  Integer.parseInt(cols.getText()),
                                 Integer.parseInt(mines.getText()));
                startGameListener.startGame();
            }else{
                JOptionPane.showMessageDialog(this,
                        "Wrong Params :\n" +
                                "rows and cols >=  5 and <= 30\n" +
                                "mines >=5 and <= 99" );
            }
        });
        this.add(ok);
    }


    private JTextField addPanels(String text){
        JPanel panel =  new JPanel(new GridLayout(1, 2));
        JTextField textField = new JTextField();
        textField.setFont(new Font("SERIF", Font.BOLD, 18));
        panel.add(new JLabel(text));
        panel.add(textField);
        this.add(panel);
        return textField;
    }

    private boolean goodParams(String rowsString, String colString, String mineString) {
        try{
            int rows = Integer.parseInt(rowsString);
            int cols = Integer.parseInt(colString);
            int mines = Integer.parseInt(mineString);
            return (rows >= 5 && rows <= 30 && cols >= 5 && cols <= 30 && mines >=5 && mines <= 99) ? true : false;
        }catch (Exception e){
            return false;
        }
    }
}
