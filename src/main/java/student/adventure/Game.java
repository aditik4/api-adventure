package student.adventure;
import com.google.gson.Gson;
import student.server.AdventureState;
import student.server.GameStatus;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


/**
 * Game class processes user's input and changes player state accordingly.
 */
public class Game {
    private MallMap rooms;
    private List<String> currentItems;
    private GameStatus gameStatus;
    private int id;
    private Player player;
    private boolean isOver = false;

    /**
     * Constructs a new game, which handles scanner, deserializes input, and holds a list of current items.
     */
    public Game() {
        currentItems = new ArrayList();
        player = new Player();
        deserialize();
        gameStatus = new GameStatus(false, id, getCurrentRoom().getRoomMessage(), getCurrentRoom().getImage(), getCurrentRoom().getVideo(), new AdventureState(player.getTraversedRooms()), player.getControlOptions());
    }
    public void setId(int currId) {
        id = currId;
    }
    public boolean getIsOver() {
        return isOver;
    }
    public GameStatus getGameStatus() {
        return gameStatus;
    }
    public List<String> getCurrentItems() { return currentItems; }
    public Player getPlayer() { return player; }

    /**
     * Constructs JSON file into a MallMap object
     */
    public void deserialize() {
        Gson gson = new Gson();
        try {
            File file = new File("src/main/java/student/adventure/mall.json");
            rooms = gson.fromJson(new FileReader(file), MallMap.class);
            player.setCurrentRoom(rooms.getStartingRoom());
            player.setCurrentRoom(getCurrentRoom());
            player.addRoomHistory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes room from current to the one in specified direction.
     * @param direction the direction to travel rooms
     */
    public String go(String direction) {
        if (getCurrentRoom().isValidGoRequest(direction)) {
           setNextRoom(direction);
           player.addRoomHistory();
            if (hasWon()) {
                isOver = true;
                gameStatus = new GameStatus(false, gameStatus.getId(), "Congratulations you have won!", getCurrentRoom().getImage(), getCurrentRoom().getVideo(), new AdventureState(player.getTraversedRooms()), player.getControlOptions());
                return "Congratulations! You have won";
            } else {
                gameStatus = new GameStatus(false, gameStatus.getId(), getCurrentRoom().getRoomMessage(), getCurrentRoom().getImage(), getCurrentRoom().getVideo(), new AdventureState(player.getTraversedRooms()), player.getControlOptions());
                return getCurrentRoom().getRoomMessage();
            }
        } else {
            gameStatus = new GameStatus(false, gameStatus.getId(), "I cant go " + "\"" + direction + "\"" + "!", getCurrentRoom().getImage(), getCurrentRoom().getVideo(), new AdventureState(player.getTraversedRooms()), player.getControlOptions());
            return "I cant go " + "\"" + direction + "\"" + "!";
        }
    }

    /**
     * Shows user information about current room.
     */
    public String examine() {
        gameStatus = new GameStatus(false, id, getCurrentRoom().getRoomMessage(), getCurrentRoom().getImage(), getCurrentRoom().getVideo(), new AdventureState(player.getTraversedRooms()), player.getControlOptions());
        return getCurrentRoom().getRoomMessage();
    }

    /**
     * Adds an item to personal inventory of items.
     * @param item the item to take.
     */
    public String take(String item) {
        boolean added = player.addToInventory(item);
        if (!added) {
            gameStatus = new GameStatus(false, id, "There is no " + item + " in the room", getCurrentRoom().getImage(), getCurrentRoom().getVideo(), new AdventureState(player.getTraversedRooms()), player.getControlOptions());
            return ("There is no " + item + " in the room");
        } else {
            getCurrentRoom().removeItems(item);
            gameStatus = new GameStatus(false, id, "You just took the " + item, getCurrentRoom().getImage(), getCurrentRoom().getVideo(),new AdventureState(player.getTraversedRooms()), player.getControlOptions());
            return "You just took the " + item;
        }
    }

    /**
     * Places specified item in current room.
     * @param item the item to drop
     */
    public String drop(String item) {
        boolean removed = player.removeFromInventory(item);
        if (!removed) {
            gameStatus = new GameStatus(false, id, "You do not have " + item + "!", getCurrentRoom().getImage(), getCurrentRoom().getVideo(), new AdventureState(player.getTraversedRooms()), player.getControlOptions());
            return "You do not have " + item + "!";
        } else {
            getCurrentRoom().getItems().add(item);
            gameStatus = new GameStatus(false, id, "You just dropped the " + item, getCurrentRoom().getImage(), getCurrentRoom().getVideo(), new AdventureState(player.getTraversedRooms()), player.getControlOptions());
            return "You just dropped the " + item;
        }
    }

    /**
     * Obtains the current room the user is in
     * @return the current room object.
     */
    public Room getCurrentRoom() {
        Room room = new Room();
        for (Room r : rooms.getRooms()) {
            if (r.getName().equals(player.getCurrentRoomName())) {
                room = r;
                break;
            }
        }
        return room;
    }

    /**
     * Sets the current room to the room in the specified direction
     * @param direction the direction of the next room.
     */
    public void setNextRoom(String direction) {
        for (Direction d : getCurrentRoom().getDirections()) {
            if (d.getDirectionName().equals(direction.toLowerCase(Locale.ROOT))) {
                player.setCurrentRoom(d.getRoom());
                player.setCurrentRoom(getCurrentRoom());
            }
        }
    }
    /**
     * Determines whether the player has won by reaching the end goal room.
     * @return true if the current room is the same as ending room; otherwise,
     *         false.
     */
    public boolean hasWon() {
        return player.getCurrentRoomName().equals(rooms.getEndingRoom());
    }


}
