package test.BuildingTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Zombie;
import unsw.loopmania.Character;
import unsw.loopmania.Enemy;
import unsw.loopmania.GoalEvaluator;
import unsw.loopmania.ZombiePitBuilding;

public class SpawnZombieTest {
    @Test
    public void testSpawnZombie() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 2)));
        LoopManiaWorld world = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));

        Character c = new Character(pos);

        world.setCharacter(c);

        ZombiePitBuilding zombiePitBuilding = new ZombiePitBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        world.addBuilding(zombiePitBuilding);


        String file_name = this.getClass().getResource("/basic_world_with_player.json").getFile();
        JSONObject JSONGoals = GoalEvaluator.parseJSON(file_name);
        world.setGoals(JSONGoals);



        assertEquals(world.getEnemies().size(), 0);
        // Trigger end-of-cycle
        world.runTickMoves();
        assertEquals(world.getEnemies().size(), 1);
        world.runTickMoves();
        assertEquals(world.getEnemies().size(), 2);

        List<Enemy> enemies = world.getEnemies();
        assertTrue(enemies.get(0) instanceof Zombie);
        assertTrue(enemies.get(1) instanceof Zombie);
    }
}
