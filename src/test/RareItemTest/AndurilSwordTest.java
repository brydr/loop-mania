package test.RareItemTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.AndurilFlameOfTheWest;
import unsw.loopmania.Character;
import unsw.loopmania.ElanMuske;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;

public class AndurilSwordTest {
    @Test
    public void testDamage() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3)) );

        Character c = new Character(pos);
        AndurilFlameOfTheWest AndurilSword = new AndurilFlameOfTheWest(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        ElanMuske elan = new ElanMuske(pos);
        Slug slug = new Slug(pos);

        c.addRareItem(AndurilSword);
        c.setEquippedWeapon(AndurilSword);

        assertEquals(elan.getHp(), 1000);
        assertEquals(slug.getHp(), 30);

        AndurilSword.effect(c, slug);

        c.attack(slug); // Only do 10 damage to normal monsters.
        assertEquals(slug.getHp(), 20);

        AndurilSword.effect(c, elan);

        c.attack(elan); // Deal 30 damage to bosses.
        assertEquals(elan.getHp(), 970);

    }
}
