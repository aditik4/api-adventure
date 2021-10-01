package student.adventure;

import java.util.Locale;
import java.util.Scanner;

/**
 * User Console class handles input and output through the console.
 */
public class UserConsole {
    Scanner scanner = new Scanner(System.in);
    Game game = new Game();
    boolean isOver;
    public UserConsole() {

    }
    /**
     * Runs the game, takes in user input and acts accordingly until game is over.
     */
    public void playGame() {
        System.out.println(game.examine());
        while (!(game.getIsOver())) {
            String[] input = getUserInput();
            if (input[0].equals("examine")) {
                System.out.println(game.examine());
            } else if (input[0].equals("quit") || input[0].equals("exit")) {
                System.out.println("Goodbye! Hope you enjoyed the mall.");
                isOver = true;
                break;
            } else if (input.length == 2 && input[0].equals("go")) {
                System.out.println(game.go(input[1]));
            } else if (input.length == 2 && input[0].equals("take")) {
                System.out.println(game.take(input[1]));
            } else if (input.length == 2 && input[0].equals("drop")) {
                System.out.println(game.drop(input[1]));
            } else {
                if (input.length == 2) {
                    System.out.println("I do not understand " + input[0] + " " + input[1]);
                } else {
                    System.out.println("I do not understand " + input[0]);
                }
            }
        }
    }

    /**
     * Obtains input from the console and formats it correctly.
     * @return a String array holding the
     */
    public String[] getUserInput() {
        System.out.print(">");
        String full = scanner.nextLine();
        full = full.trim().toLowerCase(Locale.ROOT);
        String[] split = full.split(" ", 2);
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].trim();
        }
        return split;
    }






}
