package views;

import javax.swing.*;

public class ScrabbleMenuBar extends JMenuBar {

    public ScrabbleMenuBar(ScrabbleFrame scrabbleFrame){

        this.add(new ScrabbleFileMenu(scrabbleFrame));
        this.add(new ScrabbleHelpMenu(scrabbleFrame));
    }
}
