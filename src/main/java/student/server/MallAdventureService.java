package student.server;

import student.adventure.Game;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

/**
 * MAllAdventureService class implements the API
 */
public class MallAdventureService implements AdventureService {
    private static AdventureService service = new MallAdventureService(); // = new YourAdventureServiceHere();
    private Map<Integer,Game> gameMap = new HashMap<Integer, Game>();
    private int id = 0;

    /**
     * Sets the map to given map
     * @param map the map to set to
     */
    public void setMap(Map<Integer, Game> map) {
        gameMap = map;
    }

    @Override
    public void reset() {
        gameMap.clear();
        id = 0;
    }

    @Override
    public int newGame() throws AdventureException {
        Game newGame = new Game();
        gameMap.put(id, newGame);
        id++;
        return id - 1;
    }

    @Override
    public GameStatus getGame(int id) {
        return gameMap.get(id).getGameStatus();
    }

    @Override
    public boolean destroyGame(int id) {
        if (!gameMap.containsKey(id)) {
            return false;
        }
       gameMap.remove(id);
       return true;
    }

    @Override
    public void executeCommand(int id, Command command) {
        Game current = gameMap.get(id);
        current.setId(id);
        if (command.getCommandName().equals("go")) {
            current.go(command.getCommandValue());
        } else if (command.getCommandName().equals("take")) {
            current.take(command.getCommandValue());
        } else if (command.getCommandName().equals("drop")) {
            current.drop(command.getCommandValue());
        } else if (command.getCommandName().equals("examine")) {
            current.examine();
        }
    }

    @Override
    public SortedMap<String, Integer> fetchLeaderboard() {
        return null;
    }
}
