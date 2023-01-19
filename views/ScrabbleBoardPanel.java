package views;

import models.Board;
import models.BoardSquare;
import models.Coordinate;
import models.ScrabbleModel;
import org.hamcrest.CoreMatchers;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScrabbleBoardPanel extends JPanel {
    private final ArrayList<ScrabbleBoardSquareLabel> squares;
    private final ArrayList<Coordinate> edgeOfBoard;

    public ScrabbleBoardPanel(int boardSize, ScrabbleModel model, GameIcons icons) {
        super();
        int size = boardSize + 2;
        this.squares = new ArrayList<>();
        this.edgeOfBoard = new ArrayList<>();

        edgeOfBoard.add(new Coordinate(0,0));
        edgeOfBoard.add( new Coordinate(0,16));
        edgeOfBoard.add(new Coordinate(16,0));
        edgeOfBoard.add( new Coordinate(16,16));

        this.setPreferredSize(new Dimension(600,500));
        this.setMaximumSize(new Dimension(600,500));
        this.setLayout(new GridLayout(size, size));

        for(int i = 0; i < size; i++){
            for(int  j = 0;  j < size; j++){
                boolean notFirstOrLastRow = (i != 0 && i != size - 1);
                boolean firstOrLastColumn =  (j == 0 || j == size - 1);

                if( firstOrLastColumn && notFirstOrLastRow){
                    ScrabbleBoardSquareLabel notDropabble = new ScrabbleBoardSquareLabel(false,
                            new Coordinate(i, j), model, icons);
                    notDropabble.setText(String.valueOf(i));
                    this.add(notDropabble);
                    continue;
                }

                boolean firstOrLastRow = (i == 0 || i ==  size - 1);
                boolean notFirstOrLastColumn = (j != 0 && j != size - 1);

                if(firstOrLastRow && notFirstOrLastColumn){
                    ScrabbleBoardSquareLabel notDropabble = new ScrabbleBoardSquareLabel(false,
                            new Coordinate(i, j), model, icons);
                    char alphabet = (char) ((char) 64 + j);
                    notDropabble.setText(String.valueOf(alphabet));
                    this.add(notDropabble);
                    continue;
                }

                ScrabbleBoardSquareLabel label = new ScrabbleBoardSquareLabel(true, new Coordinate(i, j),
                        model, icons);

                int boardCenter = (int) Math.ceil(boardSize / 2.0);
                boolean isBoardCenter = (i == boardCenter && j == boardCenter);

                if(isBoardCenter){
                    label.setIcon(GameIcons.CenterIcon);
                    label.setOpaque(true);
                    label.setBackground(Color.PINK);
                }

                this.add(label);
                this.squares.add(label);
            }
        }

        squares.removeIf(bs -> edgeOfBoard.contains(bs.getCoordinate()));
        int offset = 1;

        for(ScrabbleBoardSquareLabel bs : squares){
            Coordinate coordinate = new Coordinate(
                    bs.getCoordinate().getRow() - offset,
                    bs.getCoordinate().getColumn() - offset);
            Board.Bonus multiplier = model.getBoard().get(coordinate).getMultiplier();

            switch (multiplier) {
                case DW -> bs.setBack(Color.PINK);
                case DL -> bs.setBack(Color.CYAN);
                case TL -> bs.setBack(Color.BLUE);
                case TW -> bs.setBack(Color.RED);
            }
        }
    }

    public void updateBoard(ScrabbleEvent e){
        int offset = 1;

        for(ScrabbleBoardSquareLabel bs : squares){
            Coordinate coor = new Coordinate(e.getCoordinate().getRow() + offset,
                    e.getCoordinate().getColumn() + offset);

            if(bs.getCoordinate().equals(coor)){
               bs.updateLabel(e.getBoardSquare());
            }
        }
    }

    public void removeMove(ArrayList<Coordinate> removeList) {
        ArrayList<ScrabbleBoardSquareLabel> labels = new ArrayList<>();

        Stream.of(this.getComponents())
                .filter(c -> c instanceof ScrabbleBoardSquareLabel)
                .forEach(c -> labels.add(((ScrabbleBoardSquareLabel) c)));

        labels.stream()
                .filter(l -> removeList.contains(l.getCoordinate()))
                .forEach(ScrabbleBoardSquareLabel::removeIcon);
    }
}
