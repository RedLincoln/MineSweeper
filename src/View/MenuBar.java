package View;

import Control.ExitGameListener;
import Control.ResetGameListener;
import Control.StartGameListener;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    private StartGameListener startGameListener;
    private JMenu mainMenu;
    private ExitGameListener exitGameListener;
    private ResetGameListener resetGameListener;

    public MenuBar() {
        mainMenu = new JMenu("Menu");
        this.add(mainMenu);
        setMainManuItems();
    }

    private void setMainManuItems() {
        JMenuItem selectGame = new JMenuItem("New Game");
        selectGame.addActionListener(e -> startGameListener.startGame());
        mainMenu.add(selectGame);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> exitGameListener.exit());
        mainMenu.add(exit);

        JMenuItem reset =  new JMenuItem("Reset");
        reset.addActionListener(e -> resetGameListener.reset());
        mainMenu.add(reset);
    }

    public void addStartGameListener(StartGameListener startGameListener){
        this.startGameListener = startGameListener;
    }


    public void addExitGameListener(ExitGameListener exitGameListener){
        this.exitGameListener = exitGameListener;
    }

    public void addResetGameListener(ResetGameListener resetGameListener){
        this.resetGameListener = resetGameListener;
    }

}
