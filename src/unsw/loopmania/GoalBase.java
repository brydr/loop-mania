package unsw.loopmania;
public class GoalBase implements GoalNode {

    private int characterCycles;
    private int characterExperience;
    private int characterGold;

    private int requiredCharacterCycles;
    private int requiredCharacterExperience;
    private int requiredCharacterGold;

    public GoalBase(Character c, Goal g) {
        this.characterCycles = c.getCycles();
        this.characterExperience = c.getExperience();
        this.characterGold = c.getGold();

        this.requiredCharacterCycles = g.getCycles();
        this.requiredCharacterExperience = g.getExperience();
        this.requiredCharacterGold = g.getGold();
    }

    public boolean compute() {
        return (characterCycles >= requiredCharacterCycles) && (characterExperience >= requiredCharacterExperience) && (characterGold >= requiredCharacterGold);
    }

    public String print() {
        return "Cycles: " + characterCycles + "/" + requiredCharacterCycles + " | " + "Experience: " + characterExperience + "/" + requiredCharacterExperience + " | " + "Gold: " + characterGold + "/" + requiredCharacterGold;
    }
}
