package test.EnemyTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Zombie;

public class ZombieEnemyTest {

    @Test
    public void testRadius() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3)) );
        Zombie zombie = new Zombie(pos);

        assertEquals(zombie.getBattleRadius(), 2);

        assertEquals(zombie.getSupportRadius(), 2);

    }

    @Test
    public void testMove() {
        PathPosition pos = new PathPosition( 2, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3),
                                                              new Pair<>(0, 4)) );
        Zombie zombie = new Zombie(pos);
        zombie.move();
        // Zombies are slow so they do not move on the first tick.
        assertEquals(zombie.getX(), 0);
        assertEquals(zombie.getY(), 3);
        zombie.move();
        // Since the zombie moves in a random direction, up or down the path, the valid y values will be 2, 3 or 4 since it started at 3.
        List<Integer> validValues = Arrays.asList(2, 3, 4);

        assertEquals(zombie.getX(), 0);
        assertTrue(validValues.contains(zombie.getY()));
    }

    @Test
    public void testAttackAndTakeDamage() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3)) );
        Zombie zombie = new Zombie(pos);
        Character c = new Character(pos);

        zombie.attack(c);

        assertEquals(c.getHp(), 194);

        zombie.takeDamage(5);

        assertEquals(zombie.getHp(), 25);
    }

    @RepeatedTest(1000)
    // @Test
    public void testCriticalBite() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3)) );
        long seed = System.nanoTime();
        Zombie zombie = new Zombie(pos, seed);
        Character c = new Character(pos);
        AlliedSoldier alliedSoldier = new AlliedSoldier(pos);
        c.addAlliedSoldier(alliedSoldier);

        zombie.attack(c);
        Random r = new Random(seed);
        int i = r.nextInt(10);
        // System.out.println("Generated " + i);

        if (i == 0) {
            assertEquals(0, c.getListAlliedSoldiers().size());  // The allied soldier should be removed since it has transformed.
        } else {
            assertNotEquals(0, c.getListAlliedSoldiers().size());
        }
    }
}
