package unsw.loopmania.EnemyTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.PathPosition;
import unsw.loopmania.Character;
import unsw.loopmania.Vampire;


public class VampireEnemyTest {

    @Test
    public void testRadius() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1), 
                                                              new Pair<>(0, 2), 
                                                              new Pair<>(0, 3)) );
        Vampire vampire = new Vampire(pos);

        assertEquals(vampire.getBattleRadius(), 2);

        assertEquals(vampire.getSupportRadius(), 3);

    }

    @Test
    public void testMove() {
        PathPosition pos = new PathPosition( 2, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2), 
                                                              new Pair<>(0, 3),
                                                              new Pair<>(0, 4)) );
        Vampire vampire = new Vampire(pos);
        vampire.move();

        // Since the vampire moves in a random direction, up or down the path, the valid y values will be 2, 3 or 4 since it started at 3.
        List<Integer> validValues = Arrays.asList(2, 3, 4);

        assertEquals(vampire.getX(), 0);
        assertTrue(validValues.contains(vampire.getY()));
    }

    @Test
    public void testTakeDamage() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1), 
                                                              new Pair<>(0, 2), 
                                                              new Pair<>(0, 3)) );
        Vampire vampire = new Vampire(pos);

        vampire.takeDamage(5);

        assertEquals(vampire.getHp(), 55);
    }

    @Test
    public void testCriticalBite() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1), 
                                                              new Pair<>(0, 2), 
                                                              new Pair<>(0, 3)) );
        Vampire vampire = new Vampire(pos);
        Character c = new Character(pos);

        vampire.attack(c);

        System.out.println(c.getHp());

        // The least damage a vampire can do in criticalBite state is 12 + 4 = 16 whilst the max is 12 + 20 = 32. 
        // Therefore the value must be in between 68 and 84 inclusive.
        if (c.getHp() < 88) {
            assertTrue( 68 <= c.getHp() && c.getHp() <= 84);       
        } else {
            assertEquals(c.getHp(), 88);
        }
    }
}
