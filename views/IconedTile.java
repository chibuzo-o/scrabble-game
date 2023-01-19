package views;

import models.Tile;

import javax.swing.*;
import java.io.Serializable;

public class IconedTile extends Tile implements Serializable {

    private static final long serialVersionUID = 4L;

    private Icon icon;

    public IconedTile(String tileValue, GameIcons icons) {
        super(tileValue);
        this.icon = icons.getIcon(tileValue);
    }

    public IconedTile(String tileValue, GameIcons icons, int width, int height){
        this(tileValue, icons);
        this.icon = GameIcons.resizeImage(((ImageIcon) icon).getImage(), width, height);
    }

    public Icon getIcon() {
        return icon;
    }

    public Icon getBoardIcon(){
        return GameIcons.resizeImage(((ImageIcon) icon).getImage(), 35, 30);
    }
}
