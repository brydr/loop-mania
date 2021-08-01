package test.RareItemTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.TheOneRing;


public class TheOneRingTest {

    @Test
    public void testRevive() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3)) );

        Character c = new Character(pos);
        TheOneRing theOneRing = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        c.addRareItem(theOneRing);
        c.setEquippedRareItem(theOneRing);

        assertEquals(c.getHp(), 200);

        c.takeDamage(200);
        assertEquals(c.getHp(), 0);

        theOneRing.effect(c, null);
        assertEquals(c.getHp(), 200);
    }
}
