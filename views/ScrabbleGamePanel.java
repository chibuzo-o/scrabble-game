package views;

import models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ScrabbleGamePanel extends JPanel {

    private final JLabel playerTurnLabel;
    private final ScrabblePlayerRackPanel playerRack;
    private final ScrabbleModel model;

    private final ScrabbleBoardPanel gameBoard;

    public ScrabbleGamePanel(ScrabbleModel model, GameIcons icons) {
        super();
        this.model = model;

        this.setPreferredSize(new Dimension(600,800));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel playerTurn = new JPanel();
        playerTurn.setPreferredSize(new Dimension(600, 100));
        playerTurn.setLayout(new BoxLayout(playerTurn, BoxLayout.Y_AXIS));
        playerTurn.setBorder(new EmptyBorder(20, 10, 10, 10));

        playerTurnLabel = new JLabel();
        playerTurnLabel.setText(model.getCurrentPlayer().getName() + "'s turn");
        playerTurnLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        playerTurnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        playerTurn.add(playerTurnLabel, BorderLayout.NORTH);
        this.add(playerTurn);

        gameBoard = new ScrabbleBoardPanel(Board.BOARD_SIZE, model, icons);
        this.add(gameBoard, BorderLayout.CENTER);

        playerRack = new ScrabblePlayerRackPanel(model, icons);
        this.add(playerRack,BorderLayout.SOUTH);

        ScrabbleGameButtonPanel buttonCenter = new ScrabbleGameButtonPanel(model);
        this.add(buttonCenter, BorderLayout.LINE_END);

    }

    private void setPlayerTurn(String playerName){
        String text = playerName + "'s turn";
        playerTurnLabel.setText(text);
    }

    private void updatePlayerRack(){
        playerRack.update(model.getCurrentPlayer());
    }

    public void updatePanel(ScrabbleEvent event){
        if(event.getEventType().equals(ScrabbleModel.EventType.UPDATE_BOARD)
                || event.getEventType().equals(ScrabbleModel.EventType.EMPTY_BOARD)){
            gameBoard.updateBoard(event);
            return;
        }

        if(event.getEventType().equals(ScrabbleModel.EventType.UNDO)){
            this.updatePlayerRack();
            return;
        }

        if(event.getEventType().equals(ScrabbleModel.EventType.INVALID_MOVE)){
            gameBoard.removeMove(event.getRemoveList());
        }

        this.setPlayerTurn(model.getCurrentPlayer().getName());
        this.updatePlayerRack();
    }
}
