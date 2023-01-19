package controller;

import views.*;
import models.*;

import java.awt.*;
import java.awt.dnd.*;

public class DragGestureController implements DragGestureListener {

    private final ScrabbleModel model;

    public DragGestureController(ScrabbleModel model) {
        this.model = model;
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent event) {
        Cursor cursor = Cursor.getDefaultCursor();

            ScrabbleRackLabel label = (ScrabbleRackLabel) event.getComponent();

            IconedTile iconedTile = label.getIconnedTile();
            if(event.getDragAction() == DnDConstants.ACTION_MOVE){
                cursor = DragSource.DefaultMoveDrop;
            }
            event.startDrag(cursor, new ScrabbleRackLabel(iconedTile, model));
        }
}
