package unsw.loopmania;
public class GoalBaseGold implements GoalNode {

    private int characterGold;

    private int requiredCharacterGold;

    public GoalBaseGold(Character c, int gold) {
        this.characterGold = c.getGold();

        this.requiredCharacterGold = gold;
    }

    public boolean compute() {
        return (characterGold >= requiredCharacterGold);
    }

    public String print() {
        return "Gold: " + requiredCharacterGold;
    }
}
