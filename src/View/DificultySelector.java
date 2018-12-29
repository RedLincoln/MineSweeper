package View;

import Model.Level;

import javax.swing.*;
import java.awt.*;

public class DificultySelector extends JDialog {
    private Level level;


    public DificultySelector(Frame owner) {
        super(owner);
        this.setTitle("Select Dificulty");
        this.setLocationRelativeTo(owner);
        this.setModal(true);
        this.setResizable(false);
        this.setSize(50, 150);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createButtons();
    }

    private void createButtons() {
        JButton beginner = addButton("Beginner");
        beginner.addActionListener(e -> {
            level = new Level("beginner");
            dispose();
        });
        JButton medium = addButton("Medium");
        medium.addActionListener(e -> {
            level = new Level("medium");
            dispose();
        });
        JButton expert = addButton("Expert");
        expert.addActionListener(e -> {
            level = new Level("expert");
            dispose();
        });
        JButton custom = addButton("Custom");
        custom.addActionListener(e -> {
            new CustomLevel(this).setVisible(true);
        });
    }

    private JButton addButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(button);
        return button;
    }

    public Level getLevel(){
        return level;
    }

    private class CustomLevel extends JDialog{

        public CustomLevel(JDialog owner) {
            this.setTitle("Set Parameters");
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
            this.setModal(true);
            this.setResizable(false);
            this.setLocationRelativeTo(owner);
            this.setSize(50, 170);
            createAskFields(owner);
        }

        private void createAskFields(JDialog owner) {
            JTextField rows = addTextField("Rows: ");
            JTextField cols = addTextField("Cols: ");
            JTextField mines = addTextField("Mines: ");
            JPanel panel = new JPanel();
            JButton ok = new JButton("OK");
            ok.addActionListener(e -> {
                if (!rows.getText().equals("") && !cols.getText().equals("") && !mines.getText().equals("")){
                    level = new Level(Integer.parseInt(rows.getText())
                                     ,Integer.parseInt(cols.getText())
                                     ,Integer.parseInt(mines.getText()));

                    dispose();
                    owner.dispose();
                }
            });
            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(e -> dispose());
            panel.add(ok);
            panel.add(cancel);
            this.add(panel);
        }

        private JTextField addTextField(String text){
            JPanel panel = new JPanel();
            JLabel label = new JLabel(text);
            JTextField textField = new JTextField();
            textField.setColumns(10);
            panel.add(label);
            panel.add(textField);
            this.add(panel);
            return textField;
        }

    }
}
