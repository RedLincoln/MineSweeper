package View;

import Control.ResetComponent;
import Control.ResetGameListener;
import Model.IBoard;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class InfoDisplay extends JPanel implements ResetComponent {
    private IBoard board;
    private Timer timer;
    private int minutes;
    private int seconds;
    private JTextField timerTextField;
    private JTextField flagCounterTF;
    private ResetGameListener resetGameListener;

    public InfoDisplay(IBoard board) {
        this.board = board;
        this.setPreferredSize(Dimensions.INFO.getDimesion());
        addComponents();
    }

    private void addComponents() {
        this.setLayout(new BorderLayout(100, 10));
        addTimerPanel();
        addResetButton();
        addFlagCounter();
    }

    private void addTimerPanel() {
        JPanel panel = new JPanel();
        timerTextField = new JTextField();
        timerTextField.setFont(new Font("SERIF", Font.BOLD, 20));
        timerTextField.setEditable(false);
        updateTimer();
        panel.add(new JLabel(new ImageIcon(Images.CLOCK.getImage())));
        panel.add(timerTextField);
        setTimer();
        this.add(panel, BorderLayout.WEST);
    }

    private void setTimer(){
        minutes = 0;
        seconds = 0;
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask(), 0, 1000);
    }

    private TimerTask timerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                minutes += (seconds == 59) ? 1 : 0;
                seconds = (seconds == 59) ? 0 : seconds + 1;
                updateTimer();
            }
        };
    }

    private void updateTimer() {
        timerTextField.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
    }

    public void updateFlagCounter(){
        flagCounterTF.setText(board.getLevel().getMines() - board.getCellsFlagged()+"");
    }

    private void addResetButton(){
        JButton resetButton = new JButton(new ImageIcon(Images.RESET.getImage()));
        resetButton.addActionListener(e -> {
            resetGameListener.reset();
        });
        this.add(resetButton, BorderLayout.CENTER);
    }

    private void addFlagCounter() {
        JPanel panel = new JPanel();
        flagCounterTF = new JTextField();
        flagCounterTF.setEditable(false);
        flagCounterTF.setFont(new Font("SERIF", Font.BOLD, 20));
        updateFlagCounter();
        panel.add(new JLabel(new ImageIcon(Images.FLAG.getImage())));
        panel.add(flagCounterTF);
        this.add(panel, BorderLayout.EAST);
    }

    public void stopTimer(){
        timer.cancel();
    }

    @Override
    public void reset() {
        setTimer();
        updateFlagCounter();
    }

    public void addResetGameListener(ResetGameListener resetGameListener){
        this.resetGameListener = resetGameListener;
    }

}
