package test.EnemyBossTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Character;
import unsw.loopmania.ElanMuske;
import unsw.loopmania.Enemy;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Zombie;


public class ElanMuskeTest {

    @Test
    public void testRadius() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3)) );
        ElanMuske elan = new ElanMuske(pos);

        assertEquals(elan.getBattleRadius(), 1);

        assertEquals(elan.getSupportRadius(), 1);

    }

    @Test
    public void testMove() {
        PathPosition pos = new PathPosition( 6, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3),
                                                              new Pair<>(0, 4),
                                                              new Pair<>(0, 5),
                                                              new Pair<>(0, 6),
                                                              new Pair<>(0, 7),
                                                              new Pair<>(0, 8),
                                                              new Pair<>(0, 9),
                                                              new Pair<>(0, 10),
                                                              new Pair<>(0, 11),
                                                              new Pair<>(0, 12),
                                                              new Pair<>(0, 13)) );
        ElanMuske elan = new ElanMuske(pos);
        elan.move();

        // Elan moves 5 tiles, either up or down at 100% chance so valid y values are 2 and 12 if he started at 7.
        List<Integer> validValues = Arrays.asList(2, 12);

        assertEquals(elan.getX(), 0);
        assertTrue(validValues.contains(elan.getY()));
    }

    @Test
    public void testAttackAndTakeDamage() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3)) );
        ElanMuske elan = new ElanMuske(pos);
        Character c = new Character(pos);

        elan.attack(c);

        assertEquals(c.getHp(), 150);

        elan.takeDamage(5);

        assertEquals(elan.getHp(), 995);
    }

    @Test
    public void healEnemies() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3)) );

        List<Enemy> enemies = new ArrayList<BasicEnemy>();
        ElanMuske elan = new ElanMuske(pos);
        Zombie zombie = new Zombie(pos);
        enemies.add(zombie);

        zombie.takeDamage(25);
        assertEquals(zombie.getHp(), 5);
        elan.healEnemies(enemies);  // Elan heals enemies for 20 hp.

        assertEquals(zombie.getHp(), 25);

        elan.healEnemies(enemies);  // Shouldnt heal above their max hp.

        assertEquals(zombie.getHp(), 30);

    }

}
