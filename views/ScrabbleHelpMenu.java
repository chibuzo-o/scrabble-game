package views;

import javax.swing.*;

public class ScrabbleHelpMenu extends JMenu {

    public ScrabbleHelpMenu(ScrabbleFrame frame) {
        super("Help");
        JMenuItem howToPlayMenuItem = new JMenuItem("how to play");
        JMenuItem passMenuItem = new JMenuItem("pass");
        JMenuItem exchangeMenuItem = new JMenuItem("exchange");
        JMenuItem aboutMenuItem = new JMenuItem("about");

        //Adding actions to the different JMenuItems
        howToPlayMenuItem.addActionListener( e -> JOptionPane.showMessageDialog(
                frame, "To play this game, first put in the names of all players.\n " +
                        "Each player can now drag and drop a tile from their rack unto the board then hit the 'play'" +
                        " button.")
        );
        passMenuItem.addActionListener(
                e -> JOptionPane.showMessageDialog(frame, "This will skip the current players turn and move " +
                        "to unto the next player.")
        );
        exchangeMenuItem.addActionListener(
                e -> JOptionPane.showMessageDialog(frame, "This will exchange chosen tiles of the current " +
                        "player and skip the players turn and move to the next.")
        );
        aboutMenuItem.addActionListener(
                e -> JOptionPane.showMessageDialog(frame, """
                        Scrabble is a game where opponents compete in forming words with tiles.\s
                         The player with the most points at the end of the game wins!.\s
                         Players range from 2-4
                       """)
        );

        //Adding JMenuItems to JMenu
        this.add(howToPlayMenuItem);
        this.add(passMenuItem);
        this.add(exchangeMenuItem);
        this.add(aboutMenuItem);
    }
}


