package views;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.*;
import  models.*;

public class ScrabbleRackLabel extends JLabel implements Transferable {

    private final IconedTile tile;
    public ScrabbleRackLabel(IconedTile iconedTile, ScrabbleModel model) {
        super(iconedTile.getIcon());
        this.tile = iconedTile;

        DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, new DragGestureController(model));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                model.addToExchangeList(tile.getTileValue());
            }
        });
    }

    public static final DataFlavor tileFlavor = new DataFlavor(ScrabbleRackLabel.class,
            "A tile label");

    private static final DataFlavor[] supportedFlavors = {
            tileFlavor,
            DataFlavor.stringFlavor,
    };

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors;
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(tileFlavor) || flavor.equals(DataFlavor.stringFlavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if(flavor.equals(tileFlavor)){
            return this;
        } else if (flavor.equals(DataFlavor.stringFlavor)) {
            return this.toString();
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(tile.getTileValue());
    }

    public IconedTile getIconnedTile() {
        return tile;
    }
}
