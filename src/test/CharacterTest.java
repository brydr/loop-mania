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
import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.Character;

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
        // check not dead
        c.takeDamage(50);
        assertEquals(c.getHp(), 50);
        // check neg dmg and dead
        c.takeDamage(60);
        assertEquals(c.getHp(), -10);
    }

    @Test
    public void alliedSoldierListTest(){
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        Character c = new Character(pos);
        assertTrue(c.getListAlliedSoldiers().isEmpty());
        int hp = 50;
        AlliedSoldier ally1 = new AlliedSoldier(hp);
        AlliedSoldier ally2 = new AlliedSoldier(hp);
        c.addAlliedSoldier(ally1);
        c.addAlliedSoldier(ally2);
        List<AlliedSoldier> listSoldiers = c.getListAlliedSoldiers();
        assertEquals(listSoldiers.get(0), ally1);
        assertEquals(listSoldiers.get(1), ally2);
        c.removeAlliedSoldier(ally1);
        listSoldiers = c.getListAlliedSoldiers();
        assertEquals(listSoldiers.get(0), ally2);
    }
}
