package student.adventure;

import java.util.*;

/**
 * Player class stores information regarding the players current room, current items, and room history.
 */
public class Player {
    private String currentRoomName;
    private Room room;
    private List<String> currentItems = new ArrayList<>();
    private List<String> traversedRooms = new ArrayList<>();

    /**
     * Constructs a new Player
     */
    public Player() {
    }

    public List<String> getCurrentItems() {
        return currentItems;
    }
    public String getCurrentRoomName() { return currentRoomName; }
    public void setCurrentRoom(String name) {
        currentRoomName = name;
    }
    public void setCurrentRoom(Room nextRoom) {
        room = nextRoom;
    }
    public Room getCurrentRoomObject() { return room; }

    /**
     * Adds a new element to history list
     */
    public void addRoomHistory() {
        traversedRooms.add(room.getName());
    }

    /**
     * Returns the history list in string form
     * @return a string of room history
     */
    public String getTraversedRooms() {
        String history = "";
        for (String r : traversedRooms) {
            history += r + " ";
        }
        return history;
    }

    /**
     * Returns the possible control options for each command.
     * @return the control options
     */
    public Map<String, List<String>> getControlOptions() {
        Map<String, List<String>> controlMap = new HashMap<>();
        controlMap.put("go", room.getStringDirections());
        controlMap.put("examine", new ArrayList<>());
        controlMap.put("take", room.getItems());
        controlMap.put("drop", currentItems);
        return controlMap;
    }

    /**
     * Adds item to list of current items
     * @param item the item to add
     * @return true if the item was added, otherwise false if the item doesnt exist.
     */
    public boolean addToInventory(String item) {
        for (String i : room.getItems()) {
            if (i.equals(item.toLowerCase(Locale.ROOT))) {
                currentItems.add(item);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the given item from list of current items
     * @param item the item to remove
     * @return true if the item was reemoved, otheerwise false if it doesnt exist.
     */
    public boolean removeFromInventory(String item) {
        for (String i : currentItems) {
            if (i.equals(item)) {
                currentItems.remove(item);
                return true;
            }
        }
        return false;
    }



}
