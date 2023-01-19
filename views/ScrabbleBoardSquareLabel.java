package views;

import controller.DropTargetController;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ScrabbleBoardSquareLabel extends JLabel {

    private final Coordinate coordinate;
    private final GameIcons icons;
    private final boolean droppable;
    private boolean active;
    private char tileValue;

    public ScrabbleBoardSquareLabel(boolean droppable, Coordinate c, ScrabbleModel model, GameIcons icons) {
        super();
        this.coordinate = c;
        this.icons = icons;
        this.droppable = droppable;
        this.active = true;

        if(droppable)
            new DropTarget(this, DnDConstants.ACTION_MOVE, new DropTargetController(this, model, c), true);


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(droppable && active){
                    removeIcon();
                    model.removeFromWordList(tileValue, c);
                }
            }
        });

        this.setSize(new Dimension(30,  25));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setHorizontalAlignment(JLabel.CENTER);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void removeIcon(){
        int center = (int) Math.ceil(Board.BOARD_SIZE  / 2.0);
        if(this.coordinate.equals(new Coordinate(center, center))){
            this.setIcon(GameIcons.CenterIcon);
        }
        else this.setIcon(null);
    }

    public void setTileValue(char tileValue) {
        this.tileValue = tileValue;
    }

    public void updateLabel(BoardSquare bs){
        if(bs.isEmpty()){
            this.removeIcon();
            this.active = true;
            this.tileValue = '\0';
            return;
        }
        IconedTile iconedTile = new IconedTile(String.valueOf(bs.getTile().getTileValue()), icons);
        this.setIcon(iconedTile.getIcon());
        this.setTileValue(bs.getTile().getTileValue());
        this.active = false;
    }

    public void setBack(Color pink) {
        this.setBackground(pink);
        this.setOpaque(true);
    }
}
