package test.EnemyTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;


public class SlugEnemyTest {

    @Test
    public void testRadius() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3)) );
        Slug slug = new Slug(pos);

        assertEquals(slug.getBattleRadius(), 1);

        assertEquals(slug.getSupportRadius(), 1);

    }

    @Test
    public void testMove() {
        PathPosition pos = new PathPosition( 2, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3),
                                                              new Pair<>(0, 4)) );
        Slug slug = new Slug(pos);
        slug.move();

        // Since the slug moves in a random direction, up or down the path, the valid y values will be 2, 3 or 4 since it started at 3.
        List<Integer> validValues = Arrays.asList(2, 3, 4);

        assertEquals(slug.getX(), 0);
        assertTrue(validValues.contains(slug.getY()));
    }

    @Test
    public void testAttackAndTakeDamage() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3)) );
        Slug slug = new Slug(pos);
        Character c = new Character(pos);

        slug.attack(c);

        assertEquals(c.getHp(), 97);

        slug.takeDamage(5);

        assertEquals(slug.getHp(), 25);
    }
}
