package student.adventure;

import java.util.Locale;

/**
 * Stores information about directions, such as the name of the direction and the room name.
 */
public class Direction {
    private String directionName;
    private String room;

    /**
     * Constructs a new direction object
     */
    public Direction() {}

    public String getDirectionName() { return directionName.toLowerCase(Locale.ROOT); }
    public String getRoom() { return room; }

}
