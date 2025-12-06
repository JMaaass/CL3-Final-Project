import java.util.Random;
// DO NOT ADD ANY ADDITIONAL IMPORTS ------
// NOTE: NO USER INPUT LOGIC SHOULD BE HANDLED IN THIS CLASS AT ALL - DO NOT IMPORT SCANNER.

public class Character {
    // Attributes
    // We use 'private' so other classes can't mess with these variables directly.
    // They have to use the "getters" and "setters" below.
    private String name;
    private int maxHp;
    private int currHp;
    private int attack;
    private int defense;

    // Default Constructor - DO NOT MODIFY
    public Character() {};

    // Constructor 
    // This allows us to create a character in one line: new Character("Pikachu", 50, 10, 5);
    public Character(String name, int maxHp, int attack, int defense) {
        this.name = name;
        this.maxHp = maxHp;
        // When a character is first created, their current HP is full (equal to maxHp)
        this.currHp = maxHp; 
        this.attack = attack;
        this.defense = defense;
    }

    // Getters
    // These allow other classes (like Game.java) to READ the values.
    public String getName() { 
        return this.name; 
    }
    
    public int getMaxHp() { 
        return this.maxHp; 
    }
    
    public int getCurrHp() { 
        return this.currHp; 
    }
    
    public int getAttack() { 
        return this.attack; 
    }
    
    public int getDefense() { 
        return this.defense; 
    }

    // Setters
    // These allow other classes to CHANGE the values.
    public void setName(String name) { 
        this.name = name; 
    }
    
    public void setMaxHp(int maxHp) { 
        this.maxHp = maxHp; 
    }
    
    public void setCurrHp(int currHp) { 
        this.currHp = currHp; 
    }
    
    public void setAttack(int attack) { 
        this.attack = attack; 
    }
    
    public void setDefense(int defense) { 
        this.defense = defense; 
    }

    // heal() method
    // Logic copied from CL2, but now uses 'this' to refer to the specific character calling the method.
    public void heal(int numCharacters) {
        Random randomNum = new Random();
        
        // Formula from instructions: 4 + (random number between 0 and 2)
        int healAmount = 4 + randomNum.nextInt(3);
        
        // Calculate what the new HP would be
        int potentialHp = this.currHp + healAmount;
        
        // Check if this goes over the Max HP allowed
        if (potentialHp > this.maxHp) {
            this.currHp = this.maxHp; // Set to max if it goes over
            healAmount = this.maxHp - this.currHp; // Update amount for print statement
        } else {
            this.currHp = potentialHp; // Otherwise, just set it to the new amount
        }
        
        System.out.println(this.name + " has healed by " + healAmount + "!");
    }

    // attack() method
    // Takes another Character object as a parameter (the opponent).
    public void attack(Character opponent) {
        // Calculate damage: My Attack - Opponent's Defense
        // We use opponent.getDefense() to see their stats.
        int damage = this.attack - opponent.getDefense();
        
        // Damage cannot be negative
        if (damage < 0) {
            damage = 0;
        }
        
        // Calculate opponent's new health
        int newOpponentHp = opponent.getCurrHp() - damage;
        
        // Health cannot go below 0
        if (newOpponentHp < 0) {
            newOpponentHp = 0;
        }
        
        // Update the opponent's health using their setter
        opponent.setCurrHp(newOpponentHp);
        
        System.out.println(this.name + " has attacked " + opponent.getName() + " with an attack strength of " + this.attack + ".");
        System.out.println(opponent.getName() + " has defended by " + opponent.getDefense() + ".");
        System.out.println(opponent.getName() + " now has " + opponent.getCurrHp() + " hp.");
    }

    // calculateBestMove() method
    // Returns 1 for Attack, 2 for Heal
    // Uses the "Smart" logic from CL2
    public int calculateBestMove(Character opponent) {
        // 1. If I can kill them, attack
        if ((this.attack - opponent.getDefense()) >= opponent.getCurrHp()) {
            return 1;
        }
        
        // 2. If they can kill me next turn, heal
        if (this.currHp <= (opponent.getAttack() - this.defense)) {
            return 2;
        }
        
        // 3. If I am mostly healthy (within 5 of max), attack
        if ((this.maxHp - this.currHp) <= 5) {
            return 1;
        }
        
        // 4. If I have more health than them, attack
        if (this.currHp >= opponent.getCurrHp()) {
            return 1;
        }
        
        // 5. If we are close in health (diff <= 5), use probability
        // Use Math.abs to get the positive difference
        int diff = Math.abs(this.currHp - opponent.getCurrHp());
        
        if (diff <= 5) {
            Random rand = new Random();
            double prob = rand.nextDouble(); // Random decimal between 0.0 and 1.0
            
            if (prob < 0.6) {
                return 1; // 60% chance to Attack
            } else {
                return 2; // 40% chance to Heal
            }
        }
        
        // Default behavior: Heal
        return 2;
    }

    // Default toString() method
    @Override
    public String toString() {
        return this.name + ", HP=" + this.currHp + ", Attack=" + this.attack + ", Defense=" + this.defense;
    }
}