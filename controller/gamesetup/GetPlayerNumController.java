package controller.gamesetup;

import views.gamesetup.GameTypePanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetPlayerNumController implements ActionListener {
    private final GameTypePanel frame;

    public GetPlayerNumController(GameTypePanel panel) {
        this.frame  = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = e.getActionCommand();

        switch(text){
            case "2" -> frame.createPlayerForm(2);
            case "3" -> frame.createPlayerForm(3);
            case "4" -> frame.createPlayerForm(4);
        }
    }
}
