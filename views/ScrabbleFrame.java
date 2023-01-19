package views;

import models.Bag;
import models.Player;
import models.ScrabbleModel;
import views.gamesetup.GameTypePanel;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;

public class ScrabbleFrame extends JFrame implements ScrabbleView, Serializable {
    private static final long serialVersionUID = 4L;
    private ScrabbleModel model;
    private final GameIcons icons;

    ScrabbleInformationPanel leftPanel, rightPanel;
    ScrabbleGamePanel centerPanel;

    public ScrabbleFrame(ArrayList<Player> players, Bag  bag, GameTypePanel.BOARD_TYPE type) throws FileNotFoundException {
        super("Scrabble");
        this.icons = new GameIcons();

        this.model  = new ScrabbleModel(players, bag, type);
        this.setJMenuBar(new ScrabbleMenuBar(this));
        this.model.addScrabbleView(this);

        initGui();
    }

    public ScrabbleFrame(ScrabbleModel model) throws FileNotFoundException {
        super("Scrabble");
        this.icons = new GameIcons();

        this.model = model;
        this.setJMenuBar(new ScrabbleMenuBar(this));
        this.model.addScrabbleView(this);
        initGui();
        this.model.updateBoard();
    }

    private ArrayList<Player> createPlayers(Bag bag) {
        ArrayList<Player> pl = new ArrayList<>();
        JTextField playerNum =  new JTextField();
        String message = "Please Enter number of players: (Defaults to 2)";

        int result = JOptionPane.showOptionDialog(this, new Object[] {message, playerNum},
                "Enter number of players", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, null, null);

        if(result == JOptionPane.OK_OPTION){
            int playerNumAsInt;

            try {
                playerNumAsInt  = Integer.parseInt(playerNum.getText());
            } catch (NumberFormatException e){
                playerNumAsInt = 2;
            }


            for(int i = 0; i < playerNumAsInt; i++){
                JTextField playerName = new JTextField();

                String msg = "Enter player " + i + " name.";
                int rst = JOptionPane.showOptionDialog(this, new Object[] {msg, playerName},
                        "Enter player " + i + " name.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, null, null);

                if(rst == JOptionPane.OK_OPTION){
                    Player player = new Player(i, playerName.getText(), bag);
                    pl.add(player);
                }

                else {
                    System.exit(0);
                }
            }
        }

        else {
            System.exit(0);
        }
        return pl;
    }

    private void showWelcomeMessage() {
        String message = "Welcome to the World's best Scrabble Game";
        String title  = "Welcome to the World's best Scrabble Game";

        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE,null);
    }

    private void initGui(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        leftPanel = new ScrabbleInformationPanel();
        rightPanel = new ScrabbleInformationPanel();
        centerPanel = new ScrabbleGamePanel(model, this.icons);


        for(Player p : model.getPlayers()){
            if(p.getPlayerID() % 2 == 0){
                leftPanel.addElement(new ScrabbleScorePanel(p.getName(), String.valueOf(p.getScore())));
            }
            else rightPanel.addElement(new ScrabbleScorePanel(p.getName(), String.valueOf(p.getScore())));
        }


        this.add(leftPanel, BorderLayout.LINE_START);
        this.add(rightPanel, BorderLayout.LINE_END);
        this.add(centerPanel, BorderLayout.CENTER);

        this.setSize(1000,800);
        this.pack();
    }

    public void run(){
        SwingUtilities.invokeLater((() -> this.setVisible(true)));
    }

    @Override
    public void update(ScrabbleEvent e) {
        if(e.getEventType().equals(ScrabbleModel.EventType.UPDATE_BOARD)
                || e.getEventType().equals(ScrabbleModel.EventType.EMPTY_BOARD)){
            centerPanel.updatePanel(e);
            return;
        }

        if(e.getEventType().equals(ScrabbleModel.EventType.UNDO)) {
            updateScores();
            centerPanel.updatePanel(e);
        }

        if(e.getEventType().equals(ScrabbleModel.EventType.PLAY)){
            updateScores();
        }
        centerPanel.updatePanel(e);
    }

    private void updateScores() {
        int index = 0;
        for(Player p : model.getPlayers()){
            if(p.getPlayerID() % 2 == 0){
                leftPanel.updateScore(index, p);
            }
            else rightPanel.updateScore(index, p);

            index = (index + 1) % 2;
        }


    }

    public ScrabbleModel getModel() {
        return model;
    }
}
