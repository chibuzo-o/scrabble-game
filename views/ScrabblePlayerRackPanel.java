package views;

import models.GamesEntity;
import models.ScrabbleModel;
import models.Tile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ScrabblePlayerRackPanel extends JPanel {
    private final JPanel rackPanel;
    private final GameIcons icons;
    private final ScrabbleModel model;

    public ScrabblePlayerRackPanel(ScrabbleModel model, GameIcons icons) {
        super();
        this.icons = icons;
        this.model = model;

        this.setPreferredSize(new Dimension(600,100));
        this.setMaximumSize(new Dimension(600,100));
        this.setBorder(new EmptyBorder(20,20,0,20));

        this.setLayout(new BorderLayout());

        rackPanel = new JPanel(new FlowLayout());

        for(Tile t : model.getCurrentPlayer().getRack()){
            IconedTile icon  = new IconedTile(String.valueOf(t.getTileValue()), icons);
            ScrabbleRackLabel label = new ScrabbleRackLabel(icon, model);
            rackPanel.add(label);
        }

        this.add(rackPanel, BorderLayout.NORTH);
    }

    public void update(GamesEntity ge){
        this.rackPanel.removeAll();

        for(Tile t : ge.getRack()){
            IconedTile icon  = new IconedTile(String.valueOf(t.getTileValue()), icons);
            ScrabbleRackLabel label = new ScrabbleRackLabel(icon, model);
            rackPanel.add(label);
        }

    }
}
