package test.BuildingTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Zombie;
import unsw.loopmania.ZombiePitBuilding;

public class SpawnZombieTest {
    @Test
    public void testSpawnZombie() {
        var path = Arrays.asList(
            new Pair<>(0, 1),
            new Pair<>(0, 2),
            new Pair<>(0, 3),
            new Pair<>(0, 4)
        );
        LoopManiaWorld world = new LoopManiaWorld(20, 20, path);
        ZombiePitBuilding zombiePitBuilding = new ZombiePitBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        world.addBuilding(zombiePitBuilding);
        
        assertEquals(world.getEnemies().size(), 0);
        // Trigger end-of-cycle
        world.spawnEnemies();
        assertEquals(world.getEnemies().size(), 1);
        world.spawnEnemies();
        assertEquals(world.getEnemies().size(), 2);
                
        List<BasicEnemy> enemies = world.getEnemies();
        assertTrue(enemies.get(0) instanceof Zombie);
        assertTrue(enemies.get(1) instanceof Zombie);
    }
}
