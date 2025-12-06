import java.io.File;
import java.util.Scanner;
import java.util.Random;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
// DO NOT ADD ANY ADDITIONAL IMPORTS ------

public class Game {
    // Attributes
    File file;
    int numCharacters;
    Character[] characters; // An array that holds Character objects
    Character playerOne;    // Tracks who Player 1 is
    Character playerTwo;    // Tracks who Player 2 is

    // Default Constructor - DO NOT MODIFY
    public Game() {};

    // Implement setupGame()
    public void setupGame() {
        Scanner input = new Scanner(System.in);
        String filename = "";
        boolean validChoice = false;

        System.out.println("Welcome to the Battle Simulator!");
        System.out.println("Which universe would you like to battle in?");
        
        // Keep asking until we get a valid number (1-4)
        while (!validChoice) {
            System.out.println("1. Pokemon");
            System.out.println("2. Star Wars");
            System.out.println("3. Marvel");
            System.out.println("4. DC");
            
            if (input.hasNextInt()) {
                int choice = input.nextInt();
                if (choice == 1) {
                    filename = "pokemon.txt";
                    validChoice = true;
                } else if (choice == 2) {
                    filename = "starwars.txt";
                    validChoice = true;
                } else if (choice == 3) {
                    filename = "marvel.txt";
                    validChoice = true;
                } else if (choice == 4) {
                    filename = "dc.txt";
                    validChoice = true;
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.next(); // Clear the bad input
            }
        }

        // Initialize the File object and load the data
        // These lines are required by the instructions
        this.file = new File(filename); 
        this.countCharactersInFile(); 
        this.readCharacters();  
    }

    // countCharactersInFile()
    // Reads the file just to count how many lines actally have text.
    private void countCharactersInFile() {
        try {
            Scanner fileScanner = new Scanner(this.file);
            int count = 0;
            
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                // Only count lines that aren't empty
                if (line.length() > 0) {
                    count = count + 1;
                }
            }
            
            this.numCharacters = count;
            // Now that we know the count, we can set the size of our array
            this.characters = new Character[this.numCharacters];
            
        } catch (FileNotFoundException e) {
            // This runs if the file (e.g., pokemon.txt) doesn't exist
            System.out.println("File not found: " + this.file.getName());
            this.numCharacters = 0;
        }
    }

    // readCharacters()
    // Reads the file again, but this time grabs the Name and Stats to create Objects.
    private void readCharacters() {
        try {
            Scanner fileScanner = new Scanner(this.file);
            int index = 0;
            
            // Loop while there is data and we haven't filled the array
            while (fileScanner.hasNext() && index < this.numCharacters) {
                // Read the 4 pieces of data for one character
                String name = fileScanner.next();
                int hp = 0;
                int atk = 0;
                int def = 0;
                
                // Safety checks to make sure integers exist
                if (fileScanner.hasNextInt()) hp = fileScanner.nextInt();
                if (fileScanner.hasNextInt()) atk = fileScanner.nextInt();
                if (fileScanner.hasNextInt()) def = fileScanner.nextInt();
                
                // Create a NEW Character object and put it in the array
                this.characters[index] = new Character(name, hp, atk, def);
                
                index = index + 1;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading character data.");
        }
    }
    
    // Play Option 1: 1 Player vs Computer
    public void playOptionOne() {
        // We reload stats at the start so everyone is at full HP from the previous game
        this.readCharacters(); 
        
        Scanner input = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("Please select your difficulty level:");
        System.out.println("1.) Easy");
        System.out.println("2.) Medium");
        int difficulty = 1; // Default to easy
        if(input.hasNextInt()) {
            difficulty = input.nextInt();
        }

        System.out.println("Player 1 (You), choose your character:");
        this.playerOne = this.selectCharacter();
        
        // Computer picks a random index
        int compIndex = rand.nextInt(this.numCharacters);
        this.playerTwo = this.characters[compIndex];
        System.out.println("Computer has selected " + this.playerTwo.getName());

        boolean gameOver = false;
        
        // Main Battle Loop
        while (!gameOver) {
            // --- Player Turn ---
            System.out.println("1. Attack  2. Heal");
            int move = 1;
            if(input.hasNextInt()) move = input.nextInt();
            
            if (move == 1) {
                this.playerOne.attack(this.playerTwo);
            } else {
                // The numCharacters parameter isn't really used by logic, but required by instructions
                this.playerOne.heal(this.numCharacters);
            }

            // Check if Computer died
            if (this.playerTwo.getCurrHp() <= 0) {
                System.out.println("You won!");
                gameOver = true;
            } else {
                // --- Computer Turn ---
                int compMove = 1; 
                
                if (difficulty == 1) {
                    // Easy = Random move
                    compMove = rand.nextInt(2) + 1; 
                } else {
                    // Medium = Smart AI method inside Character class
                    compMove = this.playerTwo.calculateBestMove(this.playerOne); 
                }

                if (compMove == 1) {
                    this.playerTwo.attack(this.playerOne);
                } else {
                    this.playerTwo.heal(this.numCharacters);
                }

                // Check if Player died
                if (this.playerOne.getCurrHp() <= 0) {
                    System.out.println("Computer won!");
                    gameOver = true;
                }
            }
        }
    }

    // Play Option 2: 2 Player Game
    public void playOptionTwo() {
        this.readCharacters(); // Reset HP
        Scanner input = new Scanner(System.in);
        
        System.out.println("Player 1, select character:");
        this.playerOne = this.selectCharacter();
        
        System.out.println("Player 2, select character:");
        this.playerTwo = this.selectCharacter();

        boolean gameOver = false;
        
        while (!gameOver) {
            // P1 Turn
            System.out.println(this.playerOne.getName() + ", 1. Attack 2. Heal");
            int move1 = input.nextInt();
            
            if (move1 == 1) {
                this.playerOne.attack(this.playerTwo);
            } else {
                this.playerOne.heal(this.numCharacters);
            }

            if (this.playerTwo.getCurrHp() <= 0) {
                System.out.println(this.playerOne.getName() + " wins!");
                gameOver = true;
            } else {
                // P2 Turn
                System.out.println(this.playerTwo.getName() + ", 1. Attack 2. Heal");
                int move2 = input.nextInt();
                
                if (move2 == 1) {
                    this.playerTwo.attack(this.playerOne);
                } else {
                    this.playerTwo.heal(this.numCharacters);
                }

                if (this.playerOne.getCurrHp() <= 0) {
                    System.out.println(this.playerTwo.getName() + " wins!");
                    gameOver = true;
                }
            }
        }
    }

    // Play Option 3: Recursive Battle Wrapper
    // This sets up the random characters and calls the private recursive method
    public int playOptionThree() {
        this.readCharacters(); // Reset stats
        Random rand = new Random();
        
        // Select two random DIFFERENT characters
        int p1Index = rand.nextInt(this.numCharacters);
        int p2Index = rand.nextInt(this.numCharacters);
        
        // Loop until p2 is different from p1
        while (p1Index == p2Index) {
            p2Index = rand.nextInt(this.numCharacters);
        }
        
        this.playerOne = this.characters[p1Index];
        this.playerTwo = this.characters[p2Index];

        System.out.println("Recursive Battle: " + this.playerOne.getName() + " (Medium) vs " + this.playerTwo.getName() + " (Easy)");
        
        // Call the recursive method with a limit of 50 rounds
        int winner = playRecursively(50); 
        
        if (winner == -1) {
            System.out.println("It was a draw!");
            return -1;
        } else if (winner == 1) {
            System.out.println("Winner is: " + this.playerOne.getName());
            return 1;
        } else {
            System.out.println("Winner is: " + this.playerTwo.getName());
            return 2;
        }
    }

    // NEW GAME MODE: Sudden Death
    // Required by instructions: A new unique mode.
    // In this mode, we force everyone's HP to 10. The stakes are high!
    public void playSuddenDeath() {
        this.readCharacters(); 
        Scanner input = new Scanner(System.in);
        System.out.println("--- SUDDEN DEATH MODE ---");
        System.out.println("All characters reduced to 10 HP!");
        
        System.out.println("Player 1, select character:");
        this.playerOne = this.selectCharacter();
        // Manually force HP to 10 using Setters
        this.playerOne.setCurrHp(10);
        this.playerOne.setMaxHp(10);
        
        System.out.println("Player 2, select character:");
        this.playerTwo = this.selectCharacter();
        this.playerTwo.setCurrHp(10);
        this.playerTwo.setMaxHp(10);
        
        boolean gameOver = false;
        while (!gameOver) {
            // Player 1
            System.out.println(this.playerOne.getName() + " (HP: " + this.playerOne.getCurrHp() + "), 1. Attack 2. Heal");
            int move = input.nextInt();
            if(move == 1) this.playerOne.attack(this.playerTwo);
            else this.playerOne.heal(this.numCharacters);
            
            if(this.playerTwo.getCurrHp() <= 0) {
                System.out.println(this.playerOne.getName() + " WINS SUDDEN DEATH!");
                return; // Exit method immediately
            }
            
            // Player 2
            System.out.println(this.playerTwo.getName() + " (HP: " + this.playerTwo.getCurrHp() + "), 1. Attack 2. Heal");
            move = input.nextInt();
            if(move == 1) this.playerTwo.attack(this.playerOne);
            else this.playerTwo.heal(this.numCharacters);
            
            if(this.playerOne.getCurrHp() <= 0) {
                System.out.println(this.playerTwo.getName() + " WINS SUDDEN DEATH!");
                return;
            }
        }
    }

    // playRecursively implementation
    // This calls itself repeatedly until someone dies or rounds run out
    private int playRecursively(int maxRounds) {
        // Base Cases (The stop conditions)
        if (this.playerOne.getCurrHp() <= 0) return 2; // P2 wins (return 2)
        if (this.playerTwo.getCurrHp() <= 0) return 1; // P1 wins (return 1)
        if (maxRounds == 0) return -1; // Draw (return -1)

        // P1 (Medium Comp) goes first
        int move1 = this.playerOne.calculateBestMove(this.playerTwo);
        
        if (move1 == 1) {
            this.playerOne.attack(this.playerTwo);
        } else {
            this.playerOne.heal(this.numCharacters);
        }

        // Check if P1 killed P2 immediately
        if (this.playerTwo.getCurrHp() <= 0) return 1;

        // P2 (Easy Comp) goes second - completely random
        Random rand = new Random();
        int move2 = rand.nextInt(2) + 1; // 1 or 2
        
        if (move2 == 1) {
            this.playerTwo.attack(this.playerOne);
        } else {
            this.playerTwo.heal(this.numCharacters);
        }

        // RECURSIVE CALL: call the same method again, but with 1 less round
        return playRecursively(maxRounds - 1);
    }

    // selectCharacter helper method
    // Prints the list and handles the user input loop
    private Character selectCharacter() {
        Scanner input = new Scanner(System.in);
        
        // Print all available characters
        for (int i = 0; i < this.numCharacters; i++) {
            // We use (i+1) so the list is 1, 2, 3... instead of 0, 1, 2...
            System.out.println((i + 1) + ". " + this.characters[i].getName());
        }

        int selectedIndex = -1;
        boolean valid = false;
        
        // Input validation loop
        while (!valid) {
            System.out.print("Enter the number of your character: ");
            if (input.hasNextInt()) {
                int choice = input.nextInt();
                // Check if choice is within the valid range
                if (choice >= 1 && choice <= this.numCharacters) {
                    selectedIndex = choice - 1; // Convert back to array index (0-based)
                    valid = true;
                } else {
                    System.out.println("Invalid selection. Try again.");
                }
            } else {
                System.out.println("Invalid input.");
                input.next();
            }
        }
        // Return the actual Character object found at that index
        return this.characters[selectedIndex];
    }
}