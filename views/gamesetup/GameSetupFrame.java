package views.gamesetup;

import models.Bag;
import models.Player;
import views.ScrabbleFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameSetupFrame extends JFrame {

    private GAMETYPE gameType;

    private GameTypePanel rightPanel;
    public enum GAMETYPE{PVP, PVC};

    public GameSetupFrame() throws FileNotFoundException {
        super("Scrabble");

        createLayout();
    }

    private void createLayout() throws FileNotFoundException {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JLabel header = new JLabel("Game Setup", SwingConstants.CENTER);
        header.setPreferredSize(new Dimension(1000, 100));
        header.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 30));
        header.setBorder(new EmptyBorder(0,0,10,0));
        this.add(header, BorderLayout.NORTH);

        GameMessagePanel leftPanel = new GameMessagePanel();
        this.add(leftPanel,  BorderLayout.WEST);

        rightPanel = new GameTypePanel(this);
        this.add(rightPanel, BorderLayout.EAST);


        this.setPreferredSize(new Dimension(1000,800));
        this.pack();
    }

    public void setGameType(GAMETYPE gt){
        this.gameType = gt;

        updateFrame();
    }

    private void updateFrame() {
        rightPanel.setupGame(gameType);
    }

    public GAMETYPE getGameType() {
        return gameType;
    }

    public void run() {
        SwingUtilities.invokeLater((() -> this.setVisible(true)));
    }

    public void startGame(ArrayList<Player> players, Bag bag, GameTypePanel.BOARD_TYPE type)
            throws FileNotFoundException {
        this.dispose();
        new ScrabbleFrame(players, bag, type).run();
    }

    public static void main(String[] args) throws FileNotFoundException {
        new GameSetupFrame().run();
    }
}
