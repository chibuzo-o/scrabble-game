package views;

import javax.swing.*;
import java.awt.*;

public class ScrabbleScorePanel extends JPanel {

    private final JLabel scoreLabel;

    public ScrabbleScorePanel(String playerName, String score) {
       super();

       JLabel playerLabel = new JLabel(playerName + "'s" + " score");
       playerLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
       playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

       scoreLabel = new JLabel(score);
       scoreLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
       scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

       this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       this.add(playerLabel);
       this.add(scoreLabel, BorderLayout.CENTER);
    }

    public void setScore(int aScore){
        String score = Integer.toString(aScore);

        scoreLabel.setText(score);
    }
}
