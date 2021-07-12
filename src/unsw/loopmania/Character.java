package unsw.loopmania;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    // TODO = potentially implement relationships between this class and other classes
    private int experience;
    private int cycles;

    public Character(PathPosition position) {
        super(position);
        this.experience = 0;
        this.cycles = 0;
    }

    public int getCycles() {
        return cycles;
    }

    public int addCycles() {
        return cycles = cycles + 1;
    }

    public int getExperience() {
        return experience;
    }

    public int addExperience(int exp) {
        return experience = experience + exp;
         
    }
    
}
