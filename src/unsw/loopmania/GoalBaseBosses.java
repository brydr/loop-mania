package unsw.loopmania;
public class GoalBaseBosses implements GoalNode {

    private int bossesKilled;

    private int bossKillRequired;


    public GoalBaseBosses(Character c) {
        this.bossesKilled = c.getBossesKilled();
        this.bossKillRequired = 2;
    }

    public boolean compute() {
        return (bossesKilled >= bossKillRequired);
    }

    public String print() {
        return "Bosses to kill: " + bossKillRequired;
    }
}
