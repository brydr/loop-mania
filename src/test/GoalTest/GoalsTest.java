package test.GoalTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.javatuples.Pair;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import unsw.loopmania.Character;
import unsw.loopmania.GoalEvaluator;
import unsw.loopmania.GoalNode;
import unsw.loopmania.PathPosition;


public class GoalsTest {
    @Test
    public void experienceGoal() {
        // World with goals 123456 experience to win.
        String file_name = "C:\\Users\\jaeff\\Comp2511\\Project\\21T2-cs2511-project\\worlds\\basic_world_with_player.json";
        JSONObject JSONGoals = GoalEvaluator.parseJSON(file_name);

        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1), 
        new Pair<>(0, 2), 
        new Pair<>(0, 3)) );
        Character c = new Character(pos);

        GoalNode finalGoal = GoalEvaluator.evaluateGoals(JSONGoals, c);

        assertTrue(GoalEvaluator.evaluate(finalGoal) == false); // Should be false first;

        // Add 123456 experience.
        c.addExperience(123456);
        finalGoal = GoalEvaluator.evaluateGoals(JSONGoals, c);
        assertTrue(GoalEvaluator.evaluate(finalGoal) == true); // Should be true.
    }

    @Test
    public void goldGoal() {
        // World with goals 900000 gold to win.
        String file_name = "C:\\Users\\jaeff\\Comp2511\\Project\\21T2-cs2511-project\\worlds\\new_world_gold.json";
        JSONObject JSONGoals = GoalEvaluator.parseJSON(file_name);

        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1), 
        new Pair<>(0, 2), 
        new Pair<>(0, 3)) );
        Character c = new Character(pos);

        GoalNode finalGoal = GoalEvaluator.evaluateGoals(JSONGoals, c);

        assertTrue(GoalEvaluator.evaluate(finalGoal) == false); // Should be false first;

        // Add 900000 gold.
        c.addGold(900000);
        finalGoal = GoalEvaluator.evaluateGoals(JSONGoals, c);
        assertTrue(GoalEvaluator.evaluate(finalGoal) == true); // Should be true.
    }

    @Test
    public void cyclesGoal() {
        // World with goals 100 cycles to win.
        String file_name = "C:\\Users\\jaeff\\Comp2511\\Project\\21T2-cs2511-project\\worlds\\new_world_cycles.json";
        JSONObject JSONGoals = GoalEvaluator.parseJSON(file_name);

        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1), 
        new Pair<>(0, 2), 
        new Pair<>(0, 3)) );
        Character c = new Character(pos);

        GoalNode finalGoal = GoalEvaluator.evaluateGoals(JSONGoals, c);

        assertTrue(GoalEvaluator.evaluate(finalGoal) == false); // Should be false first;

        // Add 100 cycles
        for (int i = 0; i < 100; i++) {
            c.addCycles();
        }
        finalGoal = GoalEvaluator.evaluateGoals(JSONGoals, c);
        assertTrue(GoalEvaluator.evaluate(finalGoal) == true); // Should be true.
    }

    @Test
    public void orAndGoal() {
        
        // World with goals (900000 gold OR 123456 experience) AND 100 cycles to win.
        String file_name = "C:\\Users\\jaeff\\Comp2511\\Project\\21T2-cs2511-project\\worlds\\new_world.json";
        JSONObject JSONGoals = GoalEvaluator.parseJSON(file_name);

        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1), 
        new Pair<>(0, 2), 
        new Pair<>(0, 3)) );
        Character c = new Character(pos);

        // Add 100 cycles
        for (int i = 0; i < 100; i++) {
            c.addCycles();
        }
        GoalNode finalGoal = GoalEvaluator.evaluateGoals(JSONGoals, c);

        assertTrue(GoalEvaluator.evaluate(finalGoal) == false); // Should be false first since the character only has 100 cycles.

        c.addGold(900000);  // Now add gold.
        finalGoal = GoalEvaluator.evaluateGoals(JSONGoals, c);
        assertTrue(GoalEvaluator.evaluate(finalGoal) == true); // Should be true now.

        c.subtractGold(50); // Subtract gold so it should be false now.
        finalGoal = GoalEvaluator.evaluateGoals(JSONGoals, c);
        assertTrue(GoalEvaluator.evaluate(finalGoal) == false); 

        c.addExperience(123456);    // Now add the experience
        finalGoal = GoalEvaluator.evaluateGoals(JSONGoals, c);
        assertTrue(GoalEvaluator.evaluate(finalGoal) == true); // Should be true now.
    }
}
