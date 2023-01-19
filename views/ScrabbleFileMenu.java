package views;

import models.ScrabbleModel;
import views.gamesetup.GameSetupFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ScrabbleFileMenu extends JMenu {

    public ScrabbleFileMenu(ScrabbleFrame frame) {
        super("File");
        JMenuItem newGameMenuItem = new JMenuItem("New Game");
        JMenuItem saveGameMenuItem = new JMenuItem("Save Game");
        JMenuItem loadGameMenuItem = new JMenuItem("Load Game");
        JMenuItem quitMenuItem = new JMenuItem("Quit");
        JMenuItem undoMenuItem = new JMenuItem("Undo");
        JMenuItem redoMenuItem =  new JMenuItem("Redo");


        //Controller New Game Menu Item
        newGameMenuItem.addActionListener(e -> {
            String message = """
                    Are you sure you want to start a new game, all unsaved changes will be lost.
                    """;
            int result = JOptionPane.showConfirmDialog(frame, message);

            if(result == JOptionPane.YES_OPTION){
                frame.dispose();

                try {
                    new GameSetupFrame().run();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }

        });

        //Controller for Save Game Menu Item
        saveGameMenuItem.addActionListener(e -> {
            try {
                String message = " Are you sure you want to save this game?";
                int result = JOptionPane.showConfirmDialog(frame, message);

                if(result == JOptionPane.OK_OPTION){
                    JFileChooser picker = new JFileChooser(ScrabbleModel.SAVE_DIRECTORY);

                    int saveResult = picker.showSaveDialog(frame);

                    if(saveResult == JFileChooser.APPROVE_OPTION){
                        String filename = picker.getSelectedFile().getName();
                        String[] prefixArr = filename.split(".scb");
                        String filePrefix = prefixArr[0];

                        frame.getModel().serialize(filePrefix);

                        JOptionPane.showMessageDialog(frame, "Your game was saved in " +
                                filePrefix + ScrabbleModel.SAVE_EXTENSION);
                    }
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Controller Load Game Menu Item
        loadGameMenuItem.addActionListener(e -> {
            JFileChooser picker = new JFileChooser(ScrabbleModel.SAVE_DIRECTORY);

            int result = picker.showOpenDialog(frame);

            if(result == JFileChooser.APPROVE_OPTION) {
                File file = picker.getSelectedFile();

                try {
                    ScrabbleModel loadedModel = ScrabbleModel.deserialize(file);

                    frame.dispose();
                    new ScrabbleFrame(loadedModel).run();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Controller for Quit Menu Item
        quitMenuItem.addActionListener(e -> {
            String message = "Are you sure you want to quit";
            int result = JOptionPane.showConfirmDialog(frame, message);

            if(result == JOptionPane.OK_OPTION){
                frame.dispose();
                System.exit(0);
            }
        });

        // Controller for Undo Menu Item
        undoMenuItem.addActionListener(e ->  {
            frame.getModel().undoPlay();
        });

        // Controller for Redo Menu Item
        redoMenuItem.addActionListener(e -> {
            frame.getModel().redoPlay();
        });

        this.add(newGameMenuItem);
        this.add(saveGameMenuItem);
        this.add(loadGameMenuItem);
        this.add(undoMenuItem);
        this.add(redoMenuItem);
        this.add(quitMenuItem);
    }

}
