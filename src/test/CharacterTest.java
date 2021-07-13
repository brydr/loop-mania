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
import unsw.loopmania.Sword;
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
    public void alliedAttackTest(){
        int allyHp = 100;
        int slugHp = 30;
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
        Slug slug = new Slug(pos, slugHp);
        AlliedSoldier ally1 = new AlliedSoldier(pos, allyHp);
        AlliedSoldier ally2 = new AlliedSoldier(pos, allyHp);
        Character c = new Character(pos);
        // TODO may need to equip character with Weapon unarmed
        c.addAlliedSoldier(ally1);
        c.addAlliedSoldier(ally2);
        assertEquals(slug.getHp(), 30);
        c.attack(slug);
        // ally 8 dmg each, character 2 unarmed dmg
        assertEquals(slug.getHp(), 30-8-8-2);
    }

    @Test
    public void alliedSoldierListTest(){
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        Character c = new Character(pos);
        assertTrue(c.getListAlliedSoldiers().isEmpty());
        int hp = 50;
        AlliedSoldier ally1 = new AlliedSoldier(pos, hp);
        AlliedSoldier ally2 = new AlliedSoldier(pos, hp);
        c.addAlliedSoldier(ally1);
        c.addAlliedSoldier(ally2);
        List<AlliedSoldier> listSoldiers = c.getListAlliedSoldiers();
        assertEquals(listSoldiers.get(0), ally1);
        assertEquals(listSoldiers.get(1), ally2);
        c.removeAlliedSoldier(ally1);
        listSoldiers = c.getListAlliedSoldiers();
        assertEquals(listSoldiers.get(0), ally2);
    }

@Test
    public void alliedSoldierMovingTest(){
        // create character add 2 AlliedSoldiers
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        Character c = new Character(pos);
        int hp = 50;
        AlliedSoldier ally1 = new AlliedSoldier(pos, hp);
        AlliedSoldier ally2 = new AlliedSoldier(pos, hp);
        c.addAlliedSoldier(ally1);
        c.addAlliedSoldier(ally2);
        List<AlliedSoldier> listSoldiers = c.getListAlliedSoldiers();
        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 1);
        assertEquals(listSoldiers.get(0).getX(), 0);
        assertEquals(listSoldiers.get(0).getY(), 1);
        assertEquals(listSoldiers.get(1).getX(), 0);
        assertEquals(listSoldiers.get(1).getY(), 1);
        // check allies and character have moved down path
        c.moveDownPath();
        listSoldiers = c.getListAlliedSoldiers();
        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 2);
        assertEquals(listSoldiers.get(0).getX(), 0);
        assertEquals(listSoldiers.get(0).getY(), 2);
        assertEquals(listSoldiers.get(1).getX(), 0);
        assertEquals(listSoldiers.get(1).getY(), 2);
        // check allies and character have moved down path
        c.moveDownPath();
        listSoldiers = c.getListAlliedSoldiers();
        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 3);
        assertEquals(listSoldiers.get(0).getX(), 0);
        assertEquals(listSoldiers.get(0).getY(), 3);
        assertEquals(listSoldiers.get(1).getX(), 0);
        assertEquals(listSoldiers.get(1).getY(), 3);
        // check allie and character have moved up path
        c.moveUpPath();
        listSoldiers = c.getListAlliedSoldiers();
        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 2);
        assertEquals(listSoldiers.get(0).getX(), 0);
        assertEquals(listSoldiers.get(0).getY(), 2);
        assertEquals(listSoldiers.get(1).getX(), 0);
        assertEquals(listSoldiers.get(1).getY(), 2);
    }

/**
 * Integration test for different equipped weapons and enemies
 * TODO implement different enemies, check trance
 */
@Test
public void integrationAttackTest(){
    int slugHp = 30;
    Slug slug = new Slug(pos, slugHp);
    PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
    Character c = new Character(pos);
    assertEquals(slug.getHp(), 30);        
    // TODO may need to equip character with Weapon unarmed
    // unarmed does 2 dmg
    c.attack(slug);
    assertEquals(slug.getHp(), 30-2);
    // sword does 8 dmg
    c.setEquippedWeapon(Sword sword = new Sword(0,0));
    c.attack(slug);
    assertEquals(slug.getHp(), 30-2-8);
    // stake does 4 dmg
    c.setEquippedWeapon(Stake stake = new Stake(0,0));
    c.attack(slug);
    assertEquals(slug.getHp(), 30-2-8-4);
    // staff does 3 dmg
    c.setEquippedWeapon(Staff staff = new Staff(0,0));
    c.attack(slug);
    assertEquals(slug.getHp(), 30-2-8-4-3);
}
}
