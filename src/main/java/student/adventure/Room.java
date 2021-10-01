package student.adventure;

import java.util.*;

/**
 * Room class stores information about each room, such as name, description, directions, and items.
 */
public class Room {
    private String name;
    private String description;
    private List<String> items;
    private String image;
    private String video;
    private Direction[] directions;

    /**
     * Constructs a new room.
     */
    public Room() {
        items = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Direction[] getDirections() { return directions; }
    public List<String> getItems() { return items; }
    public String getImage() { return image; }
    public String getVideo() { return video; }

    /**
     * Removes items from current list in room
     * @param item the item to remove
     */
    public void removeItems(String item) {
        items.remove(item);
    }

    /**
     * Obtains the information about the room such as the descriptions, the directions, what items are available.
     * @return the room's message
     */
    public String getRoomMessage() {
        String possDir = "From here you can go: ";
        String possItems = "Items visible: ";
        for (Direction d : directions) {
            possDir += d.getDirectionName() + ", ";
        }
        for (String i : items) {
            possItems += i + ", ";
        }
        String desc = description + "\n" + possDir.substring(0, possDir.length() - 2)
                        + "\n" + possItems.substring(0, possItems.length() - 2);
        return desc;
    }

    /**
     * Determines if the go request is valid
     * @param direction to go in
     * @return true if the player can go in that direction; otherwise,
     *         false.
     */
    public boolean isValidGoRequest(String direction) {
        for (Direction d : directions) {
            if (d.getDirectionName().toLowerCase(Locale.ROOT).equals(direction.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtains the current list of directions possible from the room
     * @return a list of possiblee dirctions
     */
    public List<String> getStringDirections() {
        List<String> dirs = new ArrayList<>();
        for (Direction d : directions) {
            dirs.add(d.getDirectionName());
        }
        return dirs;
    }


}
