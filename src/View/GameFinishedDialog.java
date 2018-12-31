package View;

import javax.swing.*;
import java.awt.*;

public class GameFinishedDialog extends JDialog {
    private boolean state;

    public GameFinishedDialog(Frame owner, boolean state) {
        super(owner);
        this.state = state;
        this.setTitle((state) ? "You Win" : "You Lost");
        this.setModal(true);
        this.setLocationRelativeTo(owner);
        this.setSize(ViewDimensions.DIALOG.get());
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setupDialog();
    }

    private void setupDialog() {
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(new JLabel((state) ? ViewImages.SMALIEFACE.get() : ViewImages.SADFACE.get()));
        labelPanel.add(new JLabel((state) ? "Congratulations!! You Win" : "Shame!!. You Lost"), BorderLayout.WEST);
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton ok = new JButton("OK");
        ok.addActionListener(e -> {
            this.dispose();
        });
        buttonPanel.add(ok, BorderLayout.EAST);
        this.add(labelPanel);
        this.add(buttonPanel);
    }
}
