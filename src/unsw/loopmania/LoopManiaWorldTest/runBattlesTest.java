package unsw.loopmania.LoopManiaWorldTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.PathPosition;
import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.Character;
import unsw.loopmania.Slug;
import unsw.loopmania.Zombie;
import unsw.loopmania.Vampire;
public class runBattlesTest {

    // Test if the correct enemies die.
    @Test 
    public void testEnemiesDead() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 2), new Pair<>(0, 3)));
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3), new Pair<>(0, 4)));

        Character c = new Character(pos);
        
        newWorld.setCharacter(c);

        newWorld.possiblySpawnEnemies();

        c.moveDownPath();

        List<BasicEnemy> expectedDeadEnemies = new ArrayList<BasicEnemy>();
        PathPosition pos2 = new PathPosition(0, Arrays.asList(new Pair<>(0, 4)));
        Slug slug = new Slug(pos2);
        slug.takeDamage(30);
        expectedDeadEnemies.add(slug);

        List<BasicEnemy> deadEnemies = newWorld.runBattles();

        assertTrue(expectedDeadEnemies == deadEnemies);     // Both slugs should be dead.

        assertTrue(c.getHp() == 55);    // Each slug should do 45 damage since it takes the character 15 hits to kill the slug and slugs deal 3 damage.
    }

    // There should be no fight between the slug and character since it is out of the slugs battle radius.
    @Test
    public void outOfRange() {
        PathPosition pos2 = new PathPosition(0, Arrays.asList(new Pair<>(1, 5)));
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3), new Pair<>(0, 4), new Pair<>(0, 5),
                                                                           new Pair<>(1, 0), new Pair<>(1, 1), new Pair<>(1, 2), new Pair<>(1, 3), new Pair<>(1, 4), new Pair<>(1, 5)));

        Character c = new Character(pos2);
        
        newWorld.setCharacter(c);
        newWorld.possiblySpawnEnemies();

        List<BasicEnemy> expectedDeadEnemies = new ArrayList<BasicEnemy>();

        List<BasicEnemy> deadEnemies = newWorld.runBattles();

        assertTrue(expectedDeadEnemies == deadEnemies);   

        assertTrue(c.getHp() == 100);   
    }

    @Test
    public void testAlliedSoldiers() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 2), new Pair<>(0, 3)));
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3), new Pair<>(0, 4)));

        Character c = new Character(pos);
        AlliedSoldier allied = new AlliedSoldier(pos);

        newWorld.setCharacter(c);
        newWorld.addAlliedSoldier(allied);
        newWorld.possiblySpawnEnemies();

        c.moveDownPath();   // Observer pattern should be implemented so if the character moves, the allied soldier moves with it so no need to move the allied soldier.

        List<BasicEnemy> expectedDeadEnemies = new ArrayList<BasicEnemy>();
        PathPosition pos2 = new PathPosition(0, Arrays.asList(new Pair<>(0, 4)));
        Slug slug = new Slug(pos2);
        slug.takeDamage(30);
        expectedDeadEnemies.add(slug);

        List<BasicEnemy> deadEnemies = newWorld.runBattles();

        assertTrue(expectedDeadEnemies == deadEnemies);   

        assertTrue(c.getHp() == 100);   
        // Since it is assumed soldiers are equipped with a sword they will deal 8 damage. The character without weapons will deal 2 damage.
        // Therefore, in total they will deal 10 damage to the slug each attack and therefore there will only be 3 attacks in total.
        // The slug deals 3 damage so the allied soldier must have taken 9 damage and allied soldiers have 50 hp.
        assertTrue(allied.getHp() == 41);  
    
    }

    @Test
    public void testCharacterDead() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 2), new Pair<>(0, 3)));
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3), new Pair<>(0, 4)));

        Character c = new Character(pos);
        
        newWorld.setCharacter(c);

        newWorld.possiblySpawnEnemies();    // Spawn a slug on 0, 4

        c.moveDownPath();                   // Make character be in battle radius

        newWorld.runBattles();              // Run the battle

        c.moveUpPath();                     // Make the character go back to 0, 2 since possiblySpawnEnemies does not allow random spawn on tiles next to it or on the character.

        newWorld.possiblySpawnEnemies();    // Spawn another slug at 0, 4

        c.moveDownPath();

        newWorld.runBattles();

        newWorld.possiblySpawnEnemies();    // The third slug should kill the character.

        c.moveDownPath();

        newWorld.runBattles();

        assertTrue(c.getHp() == -2);    // Each slug should do 45 damage since it takes the character 15 hits to kill the slug and slugs deal 3 damage.
    }

    
}
