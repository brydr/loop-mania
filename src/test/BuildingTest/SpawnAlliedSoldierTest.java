package test.BuildingTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.javatuples.Pair;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BarracksBuilding;
import unsw.loopmania.Character;
import unsw.loopmania.GoalEvaluator;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

public class SpawnAlliedSoldierTest {
    @Test
    public void spawnAlliedSoldierTest() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
        LoopManiaWorld world = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));

        Character c = new Character(pos);

        world.setCharacter(c);

        // World with goals 100 cycles to win.
        JSONObject JSONGoals = new JSONObject();
        JSONGoals.put("goal", "cycles");
        JSONGoals.put("quantity", 100);
        world.setGoals(JSONGoals);

        // Testing precondition that The Character has no allied soldiers, and is at (0, 1)
        assertTrue(c.getX() == 0
                && c.getY() == 1);
        assertEquals(c.getListAlliedSoldiers().size(), 0);

        // Make a barracks
        final int barracksX = 0, barracksY = 2;
        BarracksBuilding barracks = new BarracksBuilding(new SimpleIntegerProperty(barracksX),
                                                         new SimpleIntegerProperty(barracksY));
        world.addBuilding(barracks);

        // Make some moves
        world.runTickMoves();

        // Testing precondition that character is now at (0, 2)
        assertTrue(c.getX() == barracksX
                && c.getY() == barracksY);

        // The character should now have an allied soldier
        assertEquals(c.getListAlliedSoldiers().size(), 1);

        world.runTickMoves();

        // The character should still have an allied soldier
        assertEquals(c.getListAlliedSoldiers().size(), 1);
    }
}
