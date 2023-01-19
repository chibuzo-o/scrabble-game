package views.gamesetup;

import controller.gamesetup.GameTypeController;
import controller.gamesetup.GetPlayerNumController;
import models.Player;
import models.Bag;
import views.GameIcons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class GameTypePanel extends JPanel {
    private final GameSetupFrame gameSetupFrame;
    private final JPanel playerForm;
    private final ArrayList<Player> players;
    private final Bag bag;
    private BOARD_TYPE boardType;

    public enum BOARD_TYPE {
        Default,
        Custom_One,
        Custom_Two
    }

    public GameTypePanel(GameSetupFrame gameSetupFrame) throws FileNotFoundException {
        super(new BorderLayout());
        this.gameSetupFrame = gameSetupFrame;
        this.playerForm =  new JPanel();
        this.players = new ArrayList<>();
        this.bag = new Bag();
        this.setPreferredSize(new Dimension(500,800));

        selectGameType();
    }
    private void selectGameType(){
        JLabel gameTypeLabel = new JLabel("Please select a Game Type",  SwingConstants.CENTER);
        gameTypeLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 25));
        gameTypeLabel.setBorder(new EmptyBorder(200,10,20,20));

        JButton playerVsComputer = new JButton("PVC");
        playerVsComputer.setPreferredSize(new Dimension(200,200));
        playerVsComputer.setIcon(GameIcons.PvcIcon);
        playerVsComputer.setVerticalTextPosition(SwingConstants.BOTTOM);
        playerVsComputer.setHorizontalTextPosition(SwingConstants.CENTER);
        playerVsComputer.addActionListener(new GameTypeController(gameSetupFrame));

        JButton playerVsPlayer = new JButton("PVP");
        playerVsPlayer.setPreferredSize(new Dimension(200,200));
        playerVsPlayer.setIcon(GameIcons.PvpIcon);
        playerVsPlayer.setVerticalTextPosition(SwingConstants.BOTTOM);
        playerVsPlayer.setHorizontalTextPosition(SwingConstants.CENTER);
        playerVsPlayer.addActionListener(new GameTypeController(gameSetupFrame));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(playerVsComputer);
        buttonPanel.add(playerVsPlayer);

        this.add(gameTypeLabel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.CENTER);
    }
    public void setupGame(GameSetupFrame.GAMETYPE gameType) {

        if(gameType.equals(GameSetupFrame.GAMETYPE.PVP)){
            this.removeAll();
            setupPvp();
        } else if (gameType.equals(GameSetupFrame.GAMETYPE.PVC)) {
            setupPvc();
        }
    }
    private void setupPvc() {
        String message = """
                The Player Vs Computer game mode is currently under development.

                            Please check back later.""";
        JOptionPane.showMessageDialog(gameSetupFrame, message);
    }
    private void setupPvp() {
        JPanel playerNumPanel = new JPanel(new BorderLayout());
        playerNumPanel.setBorder(new EmptyBorder(50,10,50,10));

        JLabel selectPlayerNum = new JLabel("Please select number of players",  SwingConstants.CENTER);
        selectPlayerNum.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 15));
        selectPlayerNum.setBorder(new EmptyBorder(10,10,20,20));


        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton choiceTwo = new JRadioButton("2");
        JRadioButton choiceThree = new JRadioButton("3");
        JRadioButton choiceFour = new JRadioButton("4");

        buttonGroup.add(choiceTwo);
        buttonGroup.add(choiceThree);
        buttonGroup.add(choiceFour);

        JPanel choices  = new JPanel(new FlowLayout());
        choices.add(choiceTwo);
        choices.add(choiceThree);
        choices.add(choiceFour);

        GetPlayerNumController controller = new GetPlayerNumController(this);
        choiceTwo.addActionListener(controller);
        choiceThree.addActionListener(controller);
        choiceFour.addActionListener(controller);

        playerNumPanel.add(selectPlayerNum, BorderLayout.NORTH);
        playerNumPanel.add(choices, BorderLayout.CENTER);

        this.add(playerNumPanel, BorderLayout.NORTH);
        this.repaint();
        this.revalidate();
    }
    public void createPlayerForm(int playerNum) {
        playerForm.removeAll();

        for(int i = 0; i < playerNum; i++){
            JLabel label = new JLabel("Enter the name for player " + i);
            JTextField playerName =  new JTextField(30);
            playerForm.add(label);
            playerForm.add(playerName);
        }

        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton defaultBoard = new JRadioButton("Default Board");
        JRadioButton customBoardOne= new JRadioButton("Custom Board 1");
        JRadioButton customBoardTwo = new JRadioButton("Custom Board 2");

        buttonGroup.add(defaultBoard);
        buttonGroup.add(customBoardOne);
        buttonGroup.add(customBoardTwo);

        JPanel choices  = new JPanel(new FlowLayout());
        choices.add(defaultBoard);
        choices.add(customBoardOne);
        choices.add(customBoardTwo);

        defaultBoard.addActionListener(e -> {
            this.boardType = BOARD_TYPE.Default;
        });

        customBoardOne.addActionListener(e -> {
            this.boardType = BOARD_TYPE.Custom_One;
        });

        customBoardTwo.addActionListener(e -> {
            this.boardType = BOARD_TYPE.Custom_Two;
        });

        JLabel chooseBoard = new JLabel("Please Select a Custom Board");
        playerForm.add(chooseBoard);
        playerForm.add(choices);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(new Dimension(50, 50));
        startGameButton.addActionListener(e -> {
            try {
                AtomicInteger index = new AtomicInteger();
                Stream.of(playerForm.getComponents())
                        .filter(c -> c instanceof JTextField)
                        .map(c -> ((JTextField) c).getText())
                        .map(c -> new Player(index.getAndIncrement(), c, bag))
                        .forEach(players::add);

                gameSetupFrame.startGame(players, bag, boardType);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        this.add(playerForm, BorderLayout.CENTER);
        this.add(startGameButton, BorderLayout.SOUTH);

        this.repaint();
        this.revalidate();
    }
}
