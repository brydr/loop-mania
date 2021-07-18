package test.BuildingTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Vampire;
import unsw.loopmania.VampireCastleBuilding;
import unsw.loopmania.BasicEnemy;

public class SpawnVampireTest {
    @Test
    public void testSpawnVampire() {
        var path = Arrays.asList(
            new Pair<>(0, 1),
            new Pair<>(0, 2),
            new Pair<>(0, 3),
            new Pair<>(0, 4)
        );
        LoopManiaWorld world = new LoopManiaWorld(20, 20, path);
        VampireCastleBuilding vampireCastleBuilding = new VampireCastleBuilding(new SimpleIntegerProperty(0),
                                                                                new SimpleIntegerProperty(0));
        world.addBuilding(vampireCastleBuilding);

        assertEquals(world.getEnemies().size(), 0);
        // Trigger end-of-cycle
        world.spawnEnemies();
        assertEquals(world.getEnemies().size(), 0);
        
        // Should only trigger spawn after 5 runs of cycle
        for (int i = 0; i < 4; i++)
            world.spawnEnemies();
        
        List<BasicEnemy> enemies = world.getEnemies();
        assertEquals(enemies.size(), 1);
        assertTrue(enemies.get(0) instanceof Vampire);
    }
}
