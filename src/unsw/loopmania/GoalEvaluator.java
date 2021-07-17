package unsw.loopmania;

import java.util.Arrays;

import org.javatuples.Pair;

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

    public static void main(String[] args) {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1), 
        new Pair<>(0, 2), 
        new Pair<>(0, 3)) );
        Character c = new Character(pos);

        // Set goals, goal g1 requires cycles of 5 
        // goal g2 requires gold of 300
        // goal g3 requires experience of 50
        // goal g4 requires gold of 400 and experience of 100
        Goal g1 = new Goal(5, 0, 0);
        Goal g2 = new Goal(0, 0, 300);
        Goal g3 = new Goal(0, 50, 0);
        Goal g4 = new Goal(0, 100, 400);

        GoalNode goal1 = new GoalBase(c, g1);
        GoalNode goal2 = new GoalBase(c, g2);

        // See if goals g AND g1 are accomplished
        GoalAnd goalAnd = new GoalAnd(goal1, goal2);
        System.out.println(GoalEvaluator.evaluate(goalAnd));    // Should be false
        GoalEvaluator.prettyPrint(goalAnd);

        c.addGold(300); // add 300 gold to the character
        goal2 = new GoalBase(c, g2);
        System.out.println(GoalEvaluator.evaluate(goal2));  // Should be true  now
        GoalEvaluator.prettyPrint(goal2);

        // Add 10 cycles
        for (int i = 0; i < 10; i++) {
            c.addCycles();
        } 

        goal1 = new GoalBase(c, g1);
        goal2 = new GoalBase(c, g2);
        goalAnd = new GoalAnd(goal1, goal2);
        System.out.println(GoalEvaluator.evaluate(goalAnd));    // Should be true now
        GoalEvaluator.prettyPrint(goalAnd);

        GoalNode goal3 = new GoalBase(c, g3);
        GoalAnd goalAnd2 = new GoalAnd(goal3, goalAnd); // Create a new goal where goal1 AND goal2 AND goal3 all must be completed.
        System.out.println(GoalEvaluator.evaluate(goalAnd2));    // Should be false now
        GoalEvaluator.prettyPrint(goalAnd2);

        c.addExperience(50);

        goal1 = new GoalBase(c, g1);
        goal2 = new GoalBase(c, g2);
        goalAnd = new GoalAnd(goal1, goal2);
        goal3 = new GoalBase(c, g3);
        goalAnd2 = new GoalAnd(goal3, goalAnd); 
        System.out.println(GoalEvaluator.evaluate(goalAnd2));    // Should be true now
        GoalEvaluator.prettyPrint(goalAnd2);

        GoalNode goal4 = new GoalBase(c, g4);   // Goal 4 should not be achieved but if you OR it with goalAnd2 then it should be true.
        GoalOr goalOr = new GoalOr(goal4, goalAnd2);
        System.out.println(GoalEvaluator.evaluate(goalOr));    // Should be true now
        GoalEvaluator.prettyPrint(goalOr);
    }
}
