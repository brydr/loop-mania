package test.CharacterAndAlliedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Sword;
import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.Character;
import unsw.loopmania.Slug;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Staff;
import unsw.loopmania.Stake;

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
        assertEquals(c.getHp(), 150);
        // check neg dmg and dead
        c.takeDamage(160);
        assertEquals(c.getHp(), -10);
    }

@Test
    public void alliedAttackTest(){
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
        Slug slug = new Slug(pos);
        AlliedSoldier ally1 = new AlliedSoldier(pos);
        AlliedSoldier ally2 = new AlliedSoldier(pos);
        Character c = new Character(pos);
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
        AlliedSoldier ally1 = new AlliedSoldier(pos);
        AlliedSoldier ally2 = new AlliedSoldier(pos);
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
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
        Character c = new Character(pos);
        AlliedSoldier ally1 = new AlliedSoldier(pos);
        AlliedSoldier ally2 = new AlliedSoldier(pos);
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
 * testing convertToEnemy method used for zombie crit bite
 * should return enemy passed in
 * should remove ally from list of allied soldiers
 */
@Test
public void convertToEnemyTest(){
    PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
    Character c = new Character(pos);
    AlliedSoldier ally1 = new AlliedSoldier(pos);
    c.addAlliedSoldier(ally1);
    assertFalse(c.getListAlliedSoldiers().isEmpty());
    BasicEnemy enemy = c.convertToEnemy(ally1, new Slug(pos));
    assertTrue(c.getListAlliedSoldiers().isEmpty());
    assertTrue(enemy instanceof Slug);
}

/**
 * Integration test for converting enemy to friendly and friendly back to enemy
 */
@Test
public void convertBackToEnemyTest(){
    PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
    Character c = new Character(pos);
    Slug enemy = new Slug(pos);
    // convert to ally
    enemy.convertToFriendly(c);
    // check enemy converted to ally
    assertTrue(enemy.getInTrance());
    assertFalse(c.getListAlliedSoldiers().isEmpty());

    BasicEnemy oldEnemy = null;
    // convert back to enemy
    List<AlliedSoldier> listAlliedSoldiers = c.getListAlliedSoldiers();

    // Have to get the allied Soldier like this instead of with a for loop
    // since you called c.convertBackToEnemy(ally) which changes the array in the for loop
    // which gives an error.
    AlliedSoldier ally = listAlliedSoldiers.get(0);
    oldEnemy = c.convertBackToEnemy(ally);

    // check ally converted to enemy
    assertFalse(oldEnemy.getInTrance());
    assertTrue(c.getListAlliedSoldiers().isEmpty());
}

/**
 * Integration test for different equipped weapons and enemies
 * TODO implement different enemies, check trance
 */
@Test
public void integrationAttackTest(){
    PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
    Slug slug = new Slug(pos);
    Character c = new Character(pos);
    assertEquals(slug.getHp(), 30);        
    // unarmed does 2 dmg
    c.attack(slug);
    assertEquals(slug.getHp(), 30-2);
    // sword does 8 dmg
    Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
    c.setEquippedWeapon(sword);
    c.attack(slug);
    assertEquals(slug.getHp(), 30-2-8);
    // stake does 4 dmg
    Stake stake = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
    c.setEquippedWeapon(stake);
    c.attack(slug);
    assertEquals(slug.getHp(), 30-2-8-4);
    // staff does 3 dmg
    // Since staff has a chance to trance, put an extra field for trance chance so the chance
    // for it to trance is 0.
    Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), 0);
    c.setEquippedWeapon(staff);
    c.attack(slug);
    assertEquals(slug.getHp(), 30-2-8-4-3);
}
}
