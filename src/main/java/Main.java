import org.glassfish.grizzly.http.server.HttpServer;
import student.adventure.Game;
import student.adventure.UserConsole;
import student.server.AdventureResource;
import student.server.AdventureServer;

import java.io.Console;
import java.io.IOException;

/**
 * Runs the game.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = AdventureServer.createServer(AdventureResource.class);
        server.start();
        //UserConsole con = new UserConsole();
        //con.playGame();
    }
}
