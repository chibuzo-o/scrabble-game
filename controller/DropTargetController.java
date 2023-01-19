package controller;

import models.*;
import views.*;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

public class DropTargetController extends DropTargetAdapter {
    private final ScrabbleBoardSquareLabel label;
    private final ScrabbleModel model;
    private final Coordinate coordinate;

    public DropTargetController(ScrabbleBoardSquareLabel label, ScrabbleModel model, Coordinate c) {

        this.label = label;
        this.model = model;
        this.coordinate = c;
    }

    @Override
    public void drop(DropTargetDropEvent event) {
        try {
            Transferable tr = event.getTransferable();
            ScrabbleRackLabel label = (ScrabbleRackLabel) tr.getTransferData(ScrabbleRackLabel.tileFlavor);

            if(event.isDataFlavorSupported(ScrabbleRackLabel.tileFlavor)) {
                event.acceptDrop(DnDConstants.ACTION_MOVE);
                event.dropComplete(true);

                this.label.setIcon(label.getIconnedTile().getIcon());
                this.label.setTileValue(label.getIconnedTile().getTileValue());
                this.model.addToWordList(label.getIconnedTile().getTileValue(), coordinate);
            }
        } catch (Exception e){
            e.printStackTrace();
            event.rejectDrop();
        }
    }
}
