package student.adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.google.gson.Gson;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;

/**
 * Tests the adventure game.
 * Testing strategy: valid directions for go, invalid and valid items to drop and take, and proper deserialization.
 */
public class AdventureTest {
    Gson gson = new Gson();
    MallMap rooms;
    Game game = new Game();
    @Before
    public void setUp() {
        try {
            File file = new File("src/main/java/student/adventure/mall.json");
            rooms = gson.fromJson(new FileReader(file), MallMap.class);
            game.getPlayer().setCurrentRoom(rooms.getStartingRoom());
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Test
    public void testGoEast() {
        game.go("East");
        assertEquals(game.getCurrentRoom().getName(), "MallEntry");
    }

    @Test
    public void testGoNorth() {
        game.go("east");
        game.go("north");
        assertEquals(game.getCurrentRoom().getName(), "MallNorthCorridor");
    }

    @Test
    public void testGoWest() {
        game.go("east");
        game.go("west");
        assertEquals(game.getCurrentRoom().getName(), "MallEntry");
    }

    @Test
    public void testGoNortheast() {
        game.go("east");
        game.go("northeast");
        assertEquals(game.getCurrentRoom().getName(), "Nordstrom");
    }

    @Test
    public void testGoSouth() {
        game.go("east");
        game.go("northeast");
        game.go("south");
        assertEquals(game.getCurrentRoom().getName(), "MallEntry");
    }

    @Test
    public void testGoUp() {
        game.go("east");
        game.go("east");
        game.go("up");
        assertEquals(game.getCurrentRoom().getName(), "MallTopFloor");
    }

    @Test
    public void testInvalidInput() {
        game.go("nowhere");
        assertEquals(game.getCurrentRoom().getName(), "ParkingLot");
    }

    @Test
    public void testTakeValidItem() {
        game.go("east");
        game.take("receipt");
        assertEquals(game.getCurrentItems().contains("receipt"), true);
    }

    @Test
    public void testTakeInvalidItem() {
        game.go("east");
        game.take("candle");
        assertEquals(game.getCurrentItems().contains("candle"), false);
    }

    @Test
    public void testDropValidItem() {
        game.go("east");
        game.take("receipt");
        game.go("northeast");
        game.drop("receipt");
        assertEquals(game.getCurrentItems().contains("receipts"), false);
    }

    @Test
    public void testDropInvalidItem() {
        game.go("east");
        game.take("receipt");
        game.go("northeast");
        int sizeBefore = game.getCurrentItems().size();
        game.drop("popcorn");
        assertEquals(game.getCurrentItems().size(), sizeBefore);
    }

    @Test
    public void testExamine() {
        assertEquals(game.getCurrentRoom().getDescription(), "You are currently at the parking lot, outside the mall");
    }
}