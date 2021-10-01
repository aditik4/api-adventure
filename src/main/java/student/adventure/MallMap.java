package student.adventure;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * MallMap class holds a list of rooms and useful methods to use in our game.
 */
public class MallMap {
    private String startingRoom;
    private String endingRoom;
    private ArrayList<Room> rooms;

    /**
     * Constructs a new MallMap
     */
    public MallMap() { }
    public String getStartingRoom() { return startingRoom; }
    public String getEndingRoom() { return endingRoom; }
    public ArrayList<Room> getRooms() { return rooms; }


}
