package unsw.loopmania;

public class Goal {
    public boolean completed;
    
    private int cyclesRequired;
    private int experienceRequired;
    private int goldRequired;


    public Goal(int cycles, int experience, int gold){
        this.cyclesRequired = cycles;
        this.experienceRequired = experience;
        this.goldRequired = gold;
    }

    public int getCycles() {
        return cyclesRequired;
    }
    public int getExperience() {
        return experienceRequired;
    }
    public int getGold() {
        return goldRequired;
    }
}
