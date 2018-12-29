package View;

import Model.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class DifficultySelector extends JDialog {
    private Level level;
    private Container pane;

    public DifficultySelector(Frame owner) {
        super(owner);
        pane = this.getContentPane();
        this.setModal(true);
        this.setTitle("Difficulty");
        this.setSize(50, 150);
        this.setLocationRelativeTo(owner);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(4,1,0, 5));
        createOptions();
    }

    private void createOptions() {
        addButton("Beginner");
        addButton("Medium");
        addButton("Expert");
        addCustomButton();
    }

    private void addCustomButton() {
        JButton button = new JButton("Custom");
        button.addActionListener(e -> {
            customLevelParameter();
        });
        pane.add(button);
    }

    private void customLevelParameter() {
        pane.removeAll();
        JTextField rows = addTextField("Rows");
        JTextField cols = addTextField("Cols");
        JTextField mines = addTextField("Mines");
        JButton ok = new JButton("OK");
        ok.addActionListener(e ->{
            try{
                level = new Level(Integer.parseInt(rows.getText().trim())
                        ,Integer.parseInt(cols.getText().trim())
                        ,Integer.parseInt(mines.getText().trim()));
                dispose();
            }
            catch(Exception exception){
                JOptionPane.showMessageDialog(this, "Wrong Parameters");
            }
        });
        this.add(rows);
        this.add(cols);
        this.add(mines);
        this.add(ok);
        pane.revalidate();
        pane.repaint();
    }

    private JTextField addTextField(String text){
        JTextField textField = new JTextField(text);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(text))
                    textField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {}
        });
        return textField;
    }

    private void addButton(String text){
        JButton button = new JButton(text);
        button.addActionListener(e -> {
            level = new Level(text);
            dispose();
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(button);
    }

    public Level getLevel() {
        return level;
    }

}
