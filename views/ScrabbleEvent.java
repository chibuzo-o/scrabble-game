package views;

import models.BoardSquare;
import models.Coordinate;
import models.ScrabbleModel;

import java.util.ArrayList;

public class ScrabbleEvent {
    private final ScrabbleModel.EventType eventType;

    private Coordinate coordinate;
    private BoardSquare boardSquare;
    private ArrayList<Coordinate> removeList;

    public ArrayList<Coordinate> getRemoveList() {
        return removeList;
    }

    public ScrabbleEvent(ScrabbleModel.EventType eventType) {
        this.eventType = eventType;
    }

    public ScrabbleEvent(ScrabbleModel.EventType eventType, Coordinate c, BoardSquare bs){
        this.eventType = eventType;
        this.coordinate = c;
        this.boardSquare = bs;
    }

    public ScrabbleModel.EventType getEventType() {
        return eventType;
    }

    public void setRemoveList(ArrayList<Coordinate> coordinates) {
        this.removeList = coordinates;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public BoardSquare getBoardSquare() {
        return boardSquare;
    }
}
