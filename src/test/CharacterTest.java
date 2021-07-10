package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Character;

/**
 * this class is a dummy class demonstrating how to setup tests for the project
 * you should setup additional test classes in a similar fashion, aiming to achieve high coverage.
 * A clickable "Run Test" link should appear if you have installed the Java Extension Pack properly.
 */
public class CharacterTest {
    @Test
    public void moveUpDownPathTest(){
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
        Character c = new Character(pos);
        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 1);
        c.moveDownPath();
        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 2);
        c.moveDownPath();
        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 3);
        c.moveUpPath();
        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 2);
    }

    @Test
    public void takeDamageTest(){
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        Character c = new Character(pos);
        Boolean isAlive = c.takeDamage(50);
        assertTrue(isAlive);
        assertEquals(c.getHp(), 50);
        isAlive = c.takeDamage(50);
        assertFalse(isAlive);
        assertEquals(c.getHp(), 0);
    }

    @Test
    public void alliedSoldierListTest(){
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        Character c = new Character(pos);
        assertTrue(c.getAlliedSoldiersList().isEmpty());
        int hp = 100;
        AlliedSoldier ally1 = new AlliedSoldier(hp);
        AlliedSoldier ally2 = new AlliedSoldier(hp);
        c.addAlliedSoldier(ally1);
        c.addAlliedSoldier(ally2);
        List<AlliedSoldier> listSoldiers = c.getAlliedSoldiersList();
        assertEquals(listSoldiers[0].equals(ally1));
        assertEquals(listSoldiers[1].equals(ally2));
        c.removeAlliedSoldier(ally1);
        List<AlliedSoldier> listSoldiers = c.getAlliedSoldiersList();
        assertEquals(listSoldiers[0].equals(ally2));
    }
}
