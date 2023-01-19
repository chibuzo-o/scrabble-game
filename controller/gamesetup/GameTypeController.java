package controller.gamesetup;

import views.gamesetup.GameSetupFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTypeController implements ActionListener {

    private final GameSetupFrame setupFrame;

    public GameTypeController(GameSetupFrame frame) {
        this.setupFrame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String gameType = e.getActionCommand();

        switch (gameType){
            case "PVP" -> handlePlayerVsPlayer();
            case "PVC" -> handlePlayerVsComputer();
        }
    }

    private void handlePlayerVsComputer() {
        this.setupFrame.setGameType(GameSetupFrame.GAMETYPE.PVC);
    }

    private void handlePlayerVsPlayer() {
        this.setupFrame.setGameType(GameSetupFrame.GAMETYPE.PVP);
    }
}
