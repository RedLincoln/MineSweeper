package View;

import Control.Observer;
import Control.ResetListener;
import Model.Board;
import Model.FlagListener;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameInformationPanel extends JPanel implements Observer {
    private int minutes;
    private int seconds;
    private Board board;
    private Timer timer = new Timer();
    private JTextField timerTextField;
    private ResetListener resetListener;
    private int flags;
    private JTextField flagCount;

    public GameInformationPanel(Board board){
        this.board = board;
        flags = board.getMines();
        board.addFlagListener(onFlag());
        this.setPreferredSize(ViewDimensions.INFO.get());
        setupParameters();
        addComponents();
        timer.scheduleAtFixedRate(timerTask(), 1000, 1000);
    }

    public FlagListener onFlag(){
        return flag -> {
            if (flags > 0 && flag) flags--;
            else if (flags < board.getMines() && !flag) flags++;
            updateFlagCount();
        };
    }

    private void updateFlagCount() {
        flagCount.setText(String.format("%02d", flags));
    }

    private void setupParameters() {
        minutes = 0;
        seconds = 0;
    }


    private TimerTask timerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                minutes += (seconds == 59) ? 1 :0;
                seconds = (seconds == 59) ? 0 : seconds + 1;
                updateTimer();
            }
        };
    }

    private void updateTimer(){
        timerTextField.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
    }

    private void addComponents() {
        this.setLayout(new BorderLayout(70, 10));
        setupTimerPanel();
        updateTimer();
        setupResetButton();
        setupFlagCount();
    }

    private void setupFlagCount() {
        JPanel panel = new JPanel();
        JLabel flag = new JLabel(ViewImages.FLAG.get());
        flagCount = new JTextField();
        flagCount.setFont(new Font("Arial", Font.BOLD, 20));
        updateFlagCount();
        panel.add(flag);
        panel.add(flagCount);
        this.add(panel, BorderLayout.EAST);
    }

    private void setupResetButton() {
        JButton reset = new JButton(ViewImages.RESET.get());
        reset.setFocusable(false);
        reset.addActionListener(e -> resetListener.reset());
        this.add(reset, BorderLayout.CENTER);
    }

    private void setupTimerPanel() {
        JPanel panel  = new JPanel();
        panel.add(new JLabel(ViewImages.CLOCK.get()));
        timerTextField = new JTextField();
        timerTextField.setEditable(false);
        timerTextField.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(timerTextField);
        this.add(panel, BorderLayout.WEST);
    }


    @Override
    public void reset() {
        flags = board.getMines();
        updateFlagCount();
        setupParameters();
        updateTimer();
        stopTimer();
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask(), 1000, 1000);
    }

    public void stopTimer(){
        timer.cancel();
    }

    public void addResetListener(ResetListener resetListener){
        this.resetListener = resetListener;
    }
}
