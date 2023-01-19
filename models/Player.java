package models;

/**
 * class player inherits from GamesEntity and constructs a player object
 *
 * @author Chibuzo Okpara
 * @version 1.0 25-10-22
 */
public class Player extends GamesEntity {
    /**
     * constructor for player object
     * @param id
     * @param name
     * @param bag
     */
    private static final long serialVersionUID = 4L;

    public Player(int id, String name, Bag bag) {
        super(id, name, bag);
    }
}
