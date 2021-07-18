package unsw.loopmania;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoalEvaluator {
    public static boolean evaluate(GoalNode expression) {
        // Return the expression evaluated
        return expression.compute();
    }

    public static String prettyPrint(GoalNode expression) {
        // Pretty print the expression
        return expression.print();
    }

    public static JSONObject parseJSON(String filename) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            JSONObject jsonObject = new JSONObject(content);
            JSONObject goals = (JSONObject) jsonObject.get("goal-condition");
            return goals;
        } catch (IOException e) {
            System.err.print(e.toString());
            return null;
        }
    }

    public static GoalNode evaluateGoals(JSONObject goal, Character c) {
        List<GoalNode> allSubGoals = new ArrayList<GoalNode>();
        GoalNode returnGoal = null;

        // Base cases when goal equals experience, gold or cycles.
        if (goal.getString("goal").equals("experience")) {
            int expValue = goal.getInt("quantity");
            returnGoal = new GoalBaseExperience(c, expValue);

        } else if (goal.getString("goal").equals("gold")) {
            int goldValue = goal.getInt("quantity");
            returnGoal = new GoalBaseGold(c, goldValue);

        } else if (goal.getString("goal").equals("cycles")) {
            int cycleValue = goal.getInt("quantity");
            returnGoal = new GoalBaseCycles(c, cycleValue);

        // Recursive cases for when goal equals an OR and AND.
        } else if (goal.getString("goal").equals("OR")) {
            // First get all the subgoals.
            JSONArray subGoals = (JSONArray) goal.get("subgoals");
            // Recursively evaluate all the subgoals and add them into the allSubGoals Array.
            for (int i = 0; i < subGoals.length(); i++) {
                GoalNode goalOr = evaluateGoals(subGoals.getJSONObject(i), c);
                allSubGoals.add(goalOr);
            }

            // Now OR all the subGoals together.
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

            // AND all the subgoals together.
            returnGoal = allSubGoals.get(0);
            for (int i = 1; i < allSubGoals.size(); i++) {
                returnGoal = new GoalAnd(returnGoal, allSubGoals.get(i));
            }
        }

        return returnGoal;
    }
}
