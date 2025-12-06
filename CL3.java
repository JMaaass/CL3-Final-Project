import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
// DO NOT ADD ANY ADDITIONAL IMPORTS ------

public class CL3 {
    // MAIN METHOD
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
      
        // Create the Game object. This is the "Coordinator" for everything.
        Game game = new Game(); 
      
        // Setup the game first (Ask user for Universe, load the file)
        game.setupGame();

        boolean keepPlaying = true;
      
        // Main Menu Loop
        while (keepPlaying) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Play a one player game");
            System.out.println("2. Play a two player game");
            System.out.println("3. Watch a Recursive Battle");
            System.out.println("4. Play Sudden Death (New Mode!)"); 
            System.out.println("5. Change Universe");
            System.out.println("6. Exit");

            int choice = 0;
            // Safe input check
            if (input.hasNextInt()) {
                choice = input.nextInt();
            } else {
                input.next(); // clear bad input
            }

            // Call the appropriate method in the game object based on choice
            if (choice == 1) {
                game.playOptionOne();
            }
            else if (choice == 2) {
                game.playOptionTwo();
            }
            else if (choice == 3) {
                game.playOptionThree();
            }
            else if (choice == 4) {
                // This is the new custom game mode required by instructions
                game.playSuddenDeath();
            }
            else if (choice == 5) {
                // Run setup again to pick a different file
                game.setupGame();
            }
            else if (choice == 6) {
                System.out.println("Goodbye!");
                keepPlaying = false;
            }
            else {
                System.out.println("Invalid option.");
            }
        }
    }
}