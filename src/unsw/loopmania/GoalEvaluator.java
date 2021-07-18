package unsw.loopmania;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

public class GoalEvaluator {
    public static boolean evaluate(GoalNode expression) {
        // Return the expression evaluated
        return expression.compute();
    }

    public static String prettyPrint(GoalNode expression) {
        // Pretty print the expression
        System.out.println(expression.print());
        return expression.print();
    }

    public static JSONObject parseJSON(String filename) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            JSONObject jsonObject = new JSONObject(content);
            JSONObject goals = (JSONObject) jsonObject.get("goal-condition");
            return goals;
        } catch (IOException e) {
            return null;
        }
    }

    public static GoalNode evaluateGoals(JSONObject goal, Character c) {
        List<GoalNode> allSubGoals = new ArrayList<GoalNode>();
        GoalNode returnGoal = null;

        if (goal.getString("goal").equals("experience")) {
            int expValue = goal.getInt("quantity");
            returnGoal = new GoalBaseExperience(c, expValue);

        } else if (goal.getString("goal").equals("gold")) {
            int goldValue = goal.getInt("quantity");
            returnGoal = new GoalBaseGold(c, goldValue);

        } else if (goal.getString("goal").equals("cycles")) {
            int cycleValue = goal.getInt("quantity");
            returnGoal = new GoalBaseCycles(c, cycleValue);

        } else if (goal.getString("goal").equals("OR")) {
            JSONArray subGoals = (JSONArray) goal.get("subgoals");
            for (int i = 0; i < subGoals.length(); i++) {
                GoalNode goalOr = evaluateGoals(subGoals.getJSONObject(i), c);
                allSubGoals.add(goalOr);
            }

            returnGoal = allSubGoals.get(0);
            for (int i = 1; i < allSubGoals.size(); i++) {
                returnGoal = new GoalOr(returnGoal, allSubGoals.get(i));
            }

        } else if (goal.getString("goal").equals("AND")) {
            JSONArray subGoals = (JSONArray) goal.get("subgoals");
            for (int i = 0; i < subGoals.length(); i++) {
                GoalNode goalAnd = evaluateGoals(subGoals.getJSONObject(i), c);
                allSubGoals.add(goalAnd);
            }

            returnGoal = allSubGoals.get(0);
            for (int i = 1; i < allSubGoals.size(); i++) {
                returnGoal = new GoalAnd(returnGoal, allSubGoals.get(i));
            }
        }

        return returnGoal;
    }

    public static void main(String[] args) {

        String file_name = "C:\\Users\\jaeff\\Comp2511\\Project\\21T2-cs2511-project\\worlds\\new_world.json";
        JSONObject JSONGoals = parseJSON(file_name);

        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1), 
        new Pair<>(0, 2), 
        new Pair<>(0, 3)) );
        Character c = new Character(pos);

        c.addGold(900000);
        for (int i = 0; i < 100; i++) {
            c.addCycles();
        }
        GoalNode finalGoal = GoalEvaluator.evaluateGoals(JSONGoals, c);
        System.out.println(GoalEvaluator.evaluate(finalGoal));
    }
}
