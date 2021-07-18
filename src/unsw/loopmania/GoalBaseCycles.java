package unsw.loopmania;
public class GoalBaseCycles implements GoalNode {

    private int characterCycles;

    private int requiredCharacterCycles;

    public GoalBaseCycles(Character c, int cycles) {
        this.characterCycles = c.getCycles();

        this.requiredCharacterCycles = cycles;
    }

    public boolean compute() {
        return (characterCycles >= requiredCharacterCycles);
    }

    public String print() {
        return "Cycles: " + requiredCharacterCycles;
    }
}
