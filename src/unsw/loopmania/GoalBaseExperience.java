package unsw.loopmania;
public class GoalBaseExperience implements GoalNode {

    private int characterExperience;

    private int requiredCharacterExperience;

    public GoalBaseExperience(Character c, int exp) {
        this.characterExperience = c.getExperience();

        this.requiredCharacterExperience = exp;
    }

    public boolean compute() {
        return (characterExperience >= requiredCharacterExperience);
    }

    public String print() {
        return "Experience: " + requiredCharacterExperience;
    }
}
