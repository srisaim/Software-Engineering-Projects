package cs410.uno;

import java.util.Scanner;

// The Main class is used to execute the game logic in GameState by handling the user input.
public class Main {
    public static void main(String[] args) {

        // Uses Scanner to read user input.
        // Prints a welcome message and asks user to enter the number of players.
        // Checks if the number of players the user entered is valid, if not, an error message is printed.
        // If it catches the user inputting a non-integer, it informs the user it's an invalid input.
        // Then it initializes the game based on the given parameters.
        // Starts the game using GameState and runs until game is over.
        // When game is over, it prints a message stating "Game Over!".
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to UNO!");
            System.out.println("Enter the number of players (2-10): ");
            int playerCount = Integer.parseInt(scanner.nextLine());

            if (playerCount < 2 || playerCount > 10) {
                System.out.println("Sorry, invalid number of players. Please try again.");
                return;
            }

            int setCardsPerPlayer = 7;
            int setDigitsCardsPerColor = 2;
            int setSpecialCardsPerColor = 2;
            int setWildCards = 4;

            GameState gameState = GameState.startGame(playerCount, setCardsPerPlayer, setDigitsCardsPerColor, setSpecialCardsPerColor, setWildCards);
            while (!gameState.isGameOver()) {
                gameState.runOneTurn();
            }

            System.out.println("Game Over!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please try again.");
        }
    }
}
