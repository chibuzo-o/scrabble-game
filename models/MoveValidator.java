package models;

import java.io.Serializable;

/**
 * This class is responsible for validating a move made by a player.
 * It ensures each move adheres to the rules of the game Scrabble.
 *
 * @author Geoffery Koranteng
 * @version v1.0
 * */
public class MoveValidator implements Serializable {
    private static final long serialVersionUID = 4L;
    private final Dictionary dict; // An internal dictionary used to validate moves
    private Board b;
    private PlayerMove move;

    /**
     * Instatiates a validator object
     * */
    public MoveValidator(Dictionary dict) {
        this.dict = dict;
    }

    public boolean validate(PlayerMove move, Board b) {
        this.b = b;
        this.move = move;

        int startRow = move.getStartCoordinate().getRow();
        int startCol = move.getStartCoordinate().getColumn();
        int endRow = move.getEndCoordinate().getRow();
        int endCol = move.getEndCoordinate().getColumn();

        // If first move, check if it covers center of board
        if(b.isFirstWord()){
            if(!dict.contains(move.getWord())) return false;
            int boardCenter = (int) Math.ceil(b.getSize() / 2.0) - 1;

            boolean coversCenterRow = startRow <= boardCenter && endRow >= boardCenter;
            boolean coversCenterCol = startCol <= boardCenter && endCol >= boardCenter;

            return coversCenterRow && coversCenterCol;
        }

        //Check if Word being played connects with word on board
        if(move.isHorizontal()){
            return connectsHorizontally(b, startRow, startCol, endRow, endCol);
        }

        return connectsVertically(b, startRow, startCol, endRow, endCol);
    }

    private boolean connectsVertically(Board b, int startRow, int startCol, int endRow, int endCol) {
        // For a Vertical Word: Connection can happen to the right or left of the word being played,
        // The top of the word being played

        boolean connectsLeft = false;
        boolean connectsTop = false;
        boolean connectsBottom = false;
        boolean connectsRight = false;
        boolean connectsStartTop = false;

        BoardSquare squareAtTop  = b.get(new Coordinate(startRow - 1, startCol));
        if(!squareAtTop.isEmpty()) connectsTop = true;

        BoardSquare squareAtBottom  = b.get(new Coordinate(endRow + 1, endCol));
        if(!squareAtBottom.isEmpty()) connectsBottom= true;

        for (int i = 0; i < move.getSize(); i++) {
            BoardSquare squareAtLeft  = b.get(new Coordinate(startRow + i , startCol - 1));
            BoardSquare squareAtRight = b.get(new Coordinate(startRow + i, startCol + 1));

            if(!squareAtLeft.isEmpty()) connectsLeft = true;
            if(!squareAtRight.isEmpty()) connectsRight = true;
        }

        StringBuilder s = new StringBuilder();
        if(!move.isSequential()){
            BoardSquare row = b.get(move.getStartCoordinate());
            while(!row.isEmpty()){
                s.append(row.getTile().getTileValue());
                row = b.get(new Coordinate(startRow + 1, startCol));
            }

            if(dict.contains(s.toString())) return true;
        }

        Connection connection = new Connection(connectsLeft, connectsRight,
                connectsTop, connectsBottom,false, connectsStartTop);

        return (connectsBottom || connectsLeft  || connectsTop || connectsRight) &&
                connectionIsValid(connection);
    }

    private boolean connectsHorizontally(Board b, int startRow, int startCol, int endRow, int endCol) {
        // For a horizontal Word: Connection can happen to the left of the word being played,
        // The top or bottom of the word being played
        boolean connectsLeft = false;
        boolean connectsTop = false;
        boolean connectsBottom = false;
        boolean connectsRight = false;
        boolean connectsStartRight = false;

        BoardSquare squareAtLeft  = b.get(new Coordinate(startRow, startCol - 1));
        if(!squareAtLeft.isEmpty()) connectsLeft = true;

        // Check if start coordinate connects right
        BoardSquare squareAtRight  = b.get(new Coordinate(startRow, startCol + 1));
        if(!squareAtRight.isEmpty()) connectsStartRight = true;

        squareAtRight  = b.get(new Coordinate(endRow, endCol + 1));
        if(!squareAtRight.isEmpty()) connectsRight = true;

        for (int i = 0; i < move.getSize(); i++) {
            BoardSquare squareAtTop  = b.get(new Coordinate(startRow - 1, startCol + i));
            BoardSquare squareAtBottom  = b.get(new Coordinate(startRow + 1, startCol + i));

            if(!squareAtTop.isEmpty()) connectsTop = true;
            if(!squareAtBottom.isEmpty()) connectsBottom = true;
        }

        StringBuilder s = new StringBuilder();
        if(!move.isSequential()){
            BoardSquare row = b.get(move.getStartCoordinate());
            while(!row.isEmpty()){
                s.append(row.getTile().getTileValue());
                row = b.get(new Coordinate(startRow, startCol + 1));
            }

            if(dict.contains(s.toString())) {
                move.setWord(s.toString());
                return true;
            }
        }

        Connection connection = new Connection(connectsLeft, connectsRight,
                connectsTop, connectsBottom, connectsStartRight, false);

        return (connectsBottom || connectsLeft  || connectsTop || connectsRight || connectsStartRight) &&
                connectionIsValid(connection);
    }

    private boolean connectionIsValid(Connection connection) {
        int startRow = move.getStartCoordinate().getRow();
        int startCol = move.getStartCoordinate().getColumn();
        int endRow = move.getEndCoordinate().getRow();
        int endCol = move.getEndCoordinate().getColumn();

        boolean connectionValid = false;
        StringBuilder word =  new StringBuilder();

        if(move.isHorizontal()){
            if(connection.left && connection.right){
                checkLeft(startRow, startCol, word, 0);

                word.reverse().append(move.getWord());

                checkRight(endRow, endCol, word, 0);

                if(dict.contains(word.toString())) {
                    move.setWord(word.toString());
                    connectionValid = true;
                }
            }

            for (int i = 0; i < move.getSize(); i++) {
                BoardSquare squareAtTop  = b.get(new Coordinate(startRow - 1, startCol + i));
                BoardSquare squareAtBottom  = b.get(new Coordinate(startRow + 1, startCol + i));

                if(connection.top && connection.bottom && !squareAtTop.isEmpty() && !squareAtBottom.isEmpty()){
                    word = new StringBuilder();
                    checkTop(startRow, startCol, word, i);

                    word.reverse().append(move.getWord().charAt(i));

                    checkBottom(startRow, startCol, word, i);
                    if(dict.contains(word.toString())) connectionValid = true;
                }

                if(!squareAtTop.isEmpty() && connection.top){
                    word = new StringBuilder();
                    checkTop(startRow, startCol, word, i);

                    word.reverse().append(move.getWord().charAt(i));
                    if(dict.contains(word.toString())) {
                        move.setWord(word.toString());
                        connectionValid = true;
                    }
                }

                if(!squareAtBottom.isEmpty() && connection.bottom){
                    word = new StringBuilder();
                    word.append(move.getWord().charAt(i));

                    checkBottom(startRow, startCol, word, i);

                    if(dict.contains(word.toString())) {
                        move.setWord(word.toString());
                        connectionValid = true;
                    }
                }
            }

            if(connection.left){
                word = new StringBuilder();

                checkLeft(startRow, startCol, word, 0);

                word.reverse().append(move.getWord());
                if(dict.contains(word.toString())) {
                    connectionValid = true;
                    move.setWord(word.toString());
                }
            }

            if(connection.right){
                word = new StringBuilder();
                word.append(move.getWord());

                checkRight(endRow, endCol, word, 0);

                if(dict.contains(word.toString())) {
                    connectionValid = true;
                    move.setWord(word.toString());
                }
            }

            if(connection.startRight){
                word = new StringBuilder();

                word.append(move.getWord().charAt(0));
                checkRight(startRow, startCol, word, 0);
                word.append(move.getWord(), 1, word.length() - 1);

                if(dict.contains(word.toString())) {
                    connectionValid = true;
                    move.setWord(word.toString());
                }
            }

            return connectionValid;
        }

        if(connection.top && connection.bottom){
            word = new StringBuilder();
            checkTop(startRow, startCol, word, 0);

            word.reverse().append(move.getWord());
            checkBottom(endRow, startCol, word, 0);

            if(dict.contains(word.toString())) {
                connectionValid = true;
                move.setWord(word.toString());
            }
        }

        if(connection.top){
            word = new StringBuilder();
            checkTop(startRow, startCol, word, 0);

            word.reverse().append(move.getWord());
            if(dict.contains(word.toString())) {
                connectionValid = true;
                move.setWord(word.toString());
            }
        }

        if(connection.bottom){
            word = new StringBuilder();
            word.append(move.getWord());

            checkBottom(endRow, startCol, word, 0);

            if(dict.contains(word.toString())) {
                connectionValid = true;
                move.setWord(word.toString());
            }
        }

        for (int i = 0; i < move.getSize(); i++) {
            BoardSquare squareAtLeft  = b.get(new Coordinate(startRow + i , startCol - 1));
            BoardSquare squareAtRight = b.get(new Coordinate(startRow + i, startCol + 1));

            if(connection.left && connection.right && !squareAtLeft.isEmpty() && !squareAtRight.isEmpty()){
                word = new StringBuilder();
                checkLeft(startRow, startCol, word, i);

                word.reverse().append(move.getWord().charAt(i));
                checkRight(startRow, startCol, word, i);

                if(dict.contains(word.toString())) {
                    move.setWord(word.toString());
                    connectionValid = true;
                }
            }

            if(!squareAtLeft.isEmpty() && connection.left) {
                word = new StringBuilder();

                checkLeft(startRow, startCol, word, i);

                word.reverse().append(move.getWord().charAt(i));
                if(dict.contains(word.toString())) {
                    connectionValid = true;
                    move.setWord(word.toString());
                }
            }

            if(!squareAtRight.isEmpty() && connection.right){
                word = new StringBuilder();
                word.append(move.getWord().charAt(i));

                checkRight(startRow, startCol, word, i);

                if(dict.contains(word.toString())) {
                    connectionValid = true;
                    move.setWord(word.toString());
                }
            }
        }

        return connectionValid;
    }

    private void checkTop(int startRow, int startCol, StringBuilder word, int i) {
        BoardSquare squareAtTop;
        int n = 1;
        squareAtTop = b.get(new Coordinate(startRow - n, startCol + i));
        while(!squareAtTop.isEmpty()){
            n++;
            word.append(squareAtTop.getTile().getTileValue());
            squareAtTop = b.get(new Coordinate(startRow - n, startCol + i));
        }
    }

    private void checkBottom(int startRow, int startCol, StringBuilder word, int i) {
        BoardSquare squareAtBottom;
        int index = 1;
        squareAtBottom = b.get(new Coordinate(startRow + index, startCol + i));
        while(!squareAtBottom.isEmpty()){
            index++;
            word.append(squareAtBottom.getTile().getTileValue());
            squareAtBottom = b.get(new Coordinate(startRow + index, startCol + i));
        }
    }

    private void checkLeft(int startRow, int startCol, StringBuilder word, int i) {
        int n = 1;
        BoardSquare squareAtLeft = b.get(new Coordinate(startRow + i, startCol - n));
        while(!squareAtLeft.isEmpty()){
            n++;
            word.append(squareAtLeft.getTile().getTileValue());
            squareAtLeft = b.get(new Coordinate(startRow + i, startCol - n));
        }
    }

    private void checkRight(int endRow, int endCol, StringBuilder word, int i) {
        int n = 1;
        BoardSquare squareAtRight = b.get(new Coordinate(endRow +  i, endCol + n));
        while(!squareAtRight.isEmpty()){
            n++;
            word.append(squareAtRight.getTile().getTileValue());
            squareAtRight = b.get(new Coordinate(endRow + i, endCol + n));
        }
    }

    private record Connection(boolean left,
                              boolean right,
                              boolean top,
                              boolean bottom,
                              boolean startRight,
                              boolean startTop){}
}
