package test.EnemyBossTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.Character;
import unsw.loopmania.Doggie;
import unsw.loopmania.DoggieCoinMarket;
import unsw.loopmania.PathPosition;


public class DoggieTest {
    DoggieCoinMarket coinMarket = new DoggieCoinMarket();

    @Test
    public void testRadius() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3)) );
        Doggie doggie = new Doggie(pos, coinMarket);

        assertEquals(doggie.getBattleRadius(), 1);

        assertEquals(doggie.getSupportRadius(), 1);

    }

    @Test
    public void testMove() {
        PathPosition pos = new PathPosition( 2, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3),
                                                              new Pair<>(0, 4)) );
        Doggie doggie = new Doggie(pos, coinMarket);
        doggie.move();

        // Since doggie moves in a random direction, up or down the path, the valid y values will be 2, 3 or 4 since it started at 3.
        List<Integer> validValues = Arrays.asList(2, 3, 4);

        assertEquals(doggie.getX(), 0);
        assertTrue(validValues.contains(doggie.getY()));
    }

    @Test
    public void testAttackAndTakeDamage() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3)) );
        Doggie doggie = new Doggie(pos, coinMarket);
        Character c = new Character(pos);

        doggie.attack(c);

        assertEquals(c.getHp(), 180);

        doggie.takeDamage(5);

        assertEquals(doggie.getHp(), 395);
    }

    @Test
    public void testStunCharacter() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3)) );
        Doggie doggie = new Doggie(pos, coinMarket);
        Character c = new Character(pos);

        doggie.stunAttack(c);   // An attack with 100% chance to stun.

        assertEquals(c.getHp(), 180);

        c.attack(doggie);   // Character should be stunned so attack does not go through.

        assertEquals(doggie.getHp(), 400);

        c.attack(doggie);

        assertEquals(doggie.getHp(), 400);

        c.attack(doggie);  // Stunned for 3 attacks, next attack should deal damage.

        assertEquals(doggie.getHp(), 400);

        c.attack(doggie);   // Character should be stunned so attack does not go through.

        assertEquals(doggie.getHp(), 398);
    }
}
