package unsw.loopmania;
public class GoalOr implements GoalNode {
    
    private GoalNode goal1;
    private GoalNode goal2;

    public GoalOr(GoalNode goal1, GoalNode goal2) {
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    public boolean compute() {
        return goal1.compute() || goal2.compute();
    }

    public String print() {
        return "[OR " + "(" + goal1.print() + ") (" + goal2.print() + ")" + "]";
    }
}
