package student.adventure;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import student.server.*;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;

/**
 * Testing Strategy: Test each of the commands in MallAdventureService tests, as well as checking for invalid IDs
 */
public class AdventureServiceTest {
    Gson gson = new Gson();
    MallMap rooms;
    Game game = new Game();
    MallAdventureService mallService = new MallAdventureService();
    Map<Integer, Game> games = new HashMap<Integer, Game>();

    @Before
    public void setUp() {
        try {
            File file = new File("src/main/java/student/adventure/mall.json");
            rooms = gson.fromJson(new FileReader(file), MallMap.class);
            game.getPlayer().setCurrentRoom(rooms.getStartingRoom());
            mallService.setMap(games);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
    @Test
    public void testNewGameId() throws AdventureException {
        int test = mallService.newGame();
        assertEquals(test, 0);
    }

    @Test
    public void testNewGameNotNull() throws AdventureException {
        int test = mallService.newGame();
        assert(games.get(test) != null );
    }

    @Test
    public void testNewGameInvalidIdNotAdded() throws AdventureException {
        int test = mallService.newGame();
        assert(games.get(test + 1) == null );
    }
    @Test
    public void testMultipleNewGamesAdded() throws AdventureException {
        int test1 = mallService.newGame();
        int test2 = mallService.newGame();
        assertEquals(games.containsKey(test1) && games.containsKey(test2), true);
    }
    @Test
    public void testMultipleNewGamesDifferentIds() throws AdventureException {
        int test = mallService.newGame();
        int test2 = mallService.newGame();
        int test3 = mallService.newGame();
        assertEquals(test == 0 && test2 == 1 && test3 == 2, true);
    }

    @Test
    public void testGetGame() throws AdventureException {
        Game testGame = new Game();
        int test = mallService.newGame();
        GameStatus testStatus = new GameStatus(false, 0, testGame.getCurrentRoom().getRoomMessage(), testGame.getPlayer().getCurrentRoomObject().getImage(), testGame.getPlayer().getCurrentRoomObject().getVideo(), new AdventureState(testGame.getPlayer().getTraversedRooms()),game.getPlayer().getControlOptions());
        assertEquals(testStatus.getMessage(), mallService.getGame(test).getMessage());
    }


    @Test(expected = NullPointerException.class)
    public void testGetGameInvalidIdNullException() throws AdventureException {
        int test = mallService.newGame();
        assertEquals(null, mallService.getGame(test + 1).getMessage());
    }

    @Test
    public void testReset() throws AdventureException {
       mallService.newGame();
        int test = mallService.newGame();
        mallService.reset();
        assertEquals(0, games.size());
    }

    @Test
    public void testExecuteCommandGo() throws AdventureException {
        int test = mallService.newGame();
        mallService.executeCommand(0, new Command("go", "east"));
        assertEquals(games.get(test).getGameStatus().getState().getRoomHistory().equals("ParkingLot MallEntry "), true);
    }

    @Test
    public void testExecuteCommandTake() throws AdventureException {
        int test = mallService.newGame();
        mallService.executeCommand(0, new Command("take", "coupon"));
        String message = "You are currently at the parking lot, outside the mall\n" +
                "From here you can go: east\n" +
                "Items visible";
        assertEquals(games.get(test).getGameStatus().getMessage().equals(message), true);
    }
    @Test
    public void testExecuteCommandInvalid() throws AdventureException {
        int test = mallService.newGame();
        mallService.executeCommand(0, new Command("go", "nowhere"));
        String message = "I cant go " + "\"nowhere\"" + "!";
        assertEquals(games.get(test).getGameStatus().getMessage().equals(message), true);
    }
    @Test
    public void testExecuteCommandDrop() throws AdventureException {
        int test = mallService.newGame();
        mallService.executeCommand(0, new Command("take", "coupon"));
        mallService.executeCommand(0, new Command("go", "east"));
        mallService.executeCommand(0, new Command("drop", "coupon"));
        String message = "You are in the west entry of the mall. You can see the escalators, the Nordstrom, and corridors to east and west with more shops.\n" +
                "From here you can go: west, northeast, north, east\n" +
                "Items visible: receipt, coupon";
        assertEquals(games.get(test).getGameStatus().getMessage().equals(message), true);
    }

    @Test
    public void testDestroyGame() throws AdventureException {
        mallService.newGame();
        int test = mallService.newGame();
        mallService.destroyGame(test);
        assertEquals(null,games.get(test));
    }

    @Test
    public void testDestroyGameIdOutOfBounds() throws AdventureException {
        mallService.newGame();
        int test = mallService.newGame();
        assertEquals(false, mallService.destroyGame(test + 1));
    }




















}
