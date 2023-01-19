package views;

import controller.ScrabbleButtonController;
import models.ScrabbleModel;

import javax.swing.*;
import java.awt.*;

public class ScrabbleGameButtonPanel extends JPanel {
    public ScrabbleGameButtonPanel(ScrabbleModel model) {
        super();
        this.setPreferredSize(new Dimension(600,100));
        this.setMaximumSize(new Dimension(600,100));
        this.setLayout(new FlowLayout());

        JButton playButton = new JButton("Play");
        JButton passButton = new JButton("Pass");
        JButton exchangeButton = new JButton("Exchange");

        ScrabbleButtonController controller = new ScrabbleButtonController(model);
        playButton.addActionListener(controller);
        passButton.addActionListener(controller);
        exchangeButton.addActionListener(controller);


        playButton.setPreferredSize(new Dimension(120,85));
        passButton.setPreferredSize(new Dimension(120,  85));
        exchangeButton.setPreferredSize(new Dimension(140, 85));

        ImageIcon playImage = GameIcons.createImageIcon("/assets/play.png", "play button");
        ImageIcon passImage = GameIcons.createImageIcon("/assets/pass.png", "pass button");
        ImageIcon exchangeImage = GameIcons.createImageIcon("/assets/exchange.png", "exchange button");

        assert playImage != null;
        assert passImage != null;
        assert exchangeImage != null;

        Icon playIcon = GameIcons.resizeImage(playImage.getImage(), 50, 50);
        Icon passIcon = GameIcons.resizeImage(passImage.getImage(), 50,  50);
        Icon exchangeIcon = GameIcons.resizeImage(exchangeImage.getImage(), 50, 50);

        playButton.setIcon(playIcon);
        passButton.setIcon(passIcon);
        exchangeButton.setIcon(exchangeIcon);

        playButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        playButton.setHorizontalTextPosition(SwingConstants.CENTER);

        passButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        passButton.setHorizontalTextPosition(SwingConstants.CENTER);

        exchangeButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        exchangeButton.setHorizontalTextPosition(SwingConstants.CENTER);

        this.add(playButton);
        this.add(passButton);
        this.add(exchangeButton);
    }
}
