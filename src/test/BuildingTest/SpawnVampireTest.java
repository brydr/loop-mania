package test.BuildingTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.json.JSONObject;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Vampire;
import unsw.loopmania.VampireCastleBuilding;
import unsw.loopmania.Character;
import unsw.loopmania.Enemy;
import unsw.loopmania.GoalEvaluator;

public class SpawnVampireTest {
    @Test
    public void testSpawnVampire() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 2)));
        LoopManiaWorld world = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));

        Character c = new Character(pos);

        world.setCharacter(c);

        VampireCastleBuilding vampireCastleBuilding = new VampireCastleBuilding(new SimpleIntegerProperty(0),
        new SimpleIntegerProperty(0));
        world.addBuilding(vampireCastleBuilding);

        // World with goals 100 cycles to win.
        JSONObject JSONGoals = new JSONObject();
        JSONGoals.put("goal", "cycles");
        JSONGoals.put("quantity", 100);
        world.setGoals(JSONGoals);

        assertEquals(world.getEnemies().size(), 0);
        // Trigger end-of-cycle
        world.runTickMoves();
        assertEquals(world.getEnemies().size(), 0);

        // Should only trigger spawn after 5 runs of cycle
        for (int i = 0; i < 4; i++)
        world.runTickMoves();

        List<Enemy> enemies = world.getEnemies();
        assertEquals(enemies.size(), 1);
        assertTrue(enemies.get(0) instanceof Vampire);
    }
}
