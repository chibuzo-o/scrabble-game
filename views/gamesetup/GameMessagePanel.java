package views.gamesetup;

import javax.swing.*;
import java.awt.*;

public class GameMessagePanel extends JPanel {

    public GameMessagePanel() {
        super(new BorderLayout());

        this.setPreferredSize(new Dimension(500,800));

        String message = "<html><h2> Welcome to the World's best Scrabble game </h2></html>";
        JLabel messageLabel = new JLabel(message,  SwingConstants.CENTER);

//        messageLabel.setBackground(Color.GREEN);
        messageLabel.setOpaque(true);
        messageLabel.setSize(500,800);

        this.add(messageLabel, BorderLayout.CENTER);
    }
}
