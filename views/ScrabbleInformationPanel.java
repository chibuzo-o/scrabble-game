package views;

import models.GamesEntity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ScrabbleInformationPanel extends JPanel {
    private boolean isFirst;
    private ScrabbleScorePanel first;
    private ScrabbleScorePanel second;

    public ScrabbleInformationPanel() {
        super();
        this.setPreferredSize(new Dimension(200,800));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(new BorderLayout());
        this.isFirst = false;

    }

    public void addElement(JPanel comp){
        if(!isFirst){
            this.first = (ScrabbleScorePanel) comp;
            this.add(comp, BorderLayout.NORTH);
            isFirst = true;
            return;
        }

        this.second = (ScrabbleScorePanel) comp;
        this.add(comp, BorderLayout.SOUTH);
    }

    public void updateScore(int index, GamesEntity ge){
        if(index == 0 || first != null){
            first.setScore(ge.getScore());
        }
        else {
            if(second != null) second.setScore(ge.getScore());
        }
    }
}
