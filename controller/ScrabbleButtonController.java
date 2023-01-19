package controller;

import models.ScrabbleModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScrabbleButtonController implements ActionListener {

    private final ScrabbleModel model;

    public ScrabbleButtonController(ScrabbleModel model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String  eventType = e.getActionCommand();

        switch (eventType) {
            case "Play" -> model.playWord();
            case "Pass" -> model.pass();
            case "Exchange" -> model.exchangeTiles();
        }
    }
}
