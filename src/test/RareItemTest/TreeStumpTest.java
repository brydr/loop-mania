package test.RareItemTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.ElanMuske;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.TreeStump;

public class TreeStumpTest {
    @Test
    public void testDefence() {
        PathPosition pos = new PathPosition( 0, Arrays.asList(new Pair<>(0, 1),
                                                              new Pair<>(0, 2),
                                                              new Pair<>(0, 3)) );

        Character c = new Character(pos);
        TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        ElanMuske elan = new ElanMuske(pos);
        Slug slug = new Slug(pos);

        c.addRareItem(treeStump);
        c.setEquippedShield(treeStump);

        assertEquals(c.getHp(), 200);

        // Loop until slug lands a hit since the tree stump can block.
        while (c.getHp() == 200) {
            treeStump.effect(c, slug);
            slug.attack(c);
        }

        assertEquals(c.getHp(), 197);    // Slugs should deal 3 damage.


        // Loop until elan lands a hit since the tree stump can block.
        while (c.getHp() == 197) {
            treeStump.effect(c, elan);
            elan.attack(c);
        }
        // Elan deals 50 damage. Since the tree stump should reduce it by 1/3, the damage should now be 16.
        assertEquals(c.getHp(), 181);

    }
}
