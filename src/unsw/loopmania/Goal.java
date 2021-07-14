package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

public class Goal {
    public boolean completed;
    public List<Goal> subGoals;
    public int value;

    public Goal(){
        this.completed = false;
        this.value = 0;
        subGoals = new ArrayList<Goal>();
    }
    
    public boolean getCompletion() {
        return completed;
    }

    public void setCompletion() {
        completed = true;
    }

    public void addSubGoal(Goal goal) {
        subGoals.add(goal);
    }

    public List<Goal> getSubGoals() {
        return subGoals;
    }
}
