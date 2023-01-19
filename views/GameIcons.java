package views;

import models.Board;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GameIcons {

    private final Map<String, Icon> icons;
    public static Icon PvcIcon = getPvcIcon();
    public static Icon PvpIcon = getPvpIcon();
    public static Icon CenterIcon = getCenterIcon();


    public GameIcons() throws FileNotFoundException {
        this.icons = generateIcons();
    }


    public Icon getIcon(String letter){
        return icons.get(letter);
    }

    private HashMap<String, Icon> generateIcons() throws FileNotFoundException{
        HashMap<String, Icon> map = new HashMap<>();
        int width = 50;
        int height = 50;

        try{
            File file = new File("config/tileIcons.cfg");
            Scanner reader = new Scanner(file);

            while(reader.hasNextLine()){
                String line = reader.nextLine();
                String[] arr = line.split("_");

                String letter = String.valueOf(arr[1].charAt(0));

                ImageIcon aIcon = createImageIcon(line, letter);
                assert aIcon != null;

                Image image = aIcon.getImage(); // transform it
                aIcon = resizeImage(image, width,  height);
                map.put(letter, aIcon);
            }

            reader.close();

        }  catch (FileNotFoundException e){
            System.out.println("Error: File Not Found");
            e.printStackTrace();
            throw new FileNotFoundException();
        }

        return map;
    }

    public static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = GameIcons.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static ImageIcon resizeImage(Image  image, int width, int height){
        Image newImage = image.getScaledInstance(width, height,  Image.SCALE_SMOOTH); // scale it the smooth way
        return new ImageIcon(newImage);
    }

    private static ImageIcon getCenterIcon(){
        ImageIcon icon = createImageIcon("/assets/center.png", "center star");
        assert icon != null;
        return GameIcons.resizeImage(icon.getImage(), Board.BOARD_SIZE + 2, Board.BOARD_SIZE + 2);
    }

    private static Icon getPvcIcon() {
        ImageIcon icon = createImageIcon("/assets/pvc.png", "pvc icon");
        assert icon != null;
        return GameIcons.resizeImage(icon.getImage(), 200, 150);
    }

    private static Icon getPvpIcon() {
        ImageIcon icon = createImageIcon("/assets/pvp.png", "pvp icon");
        assert icon != null;
        return GameIcons.resizeImage(icon.getImage(), 200, 150);
    }
}
