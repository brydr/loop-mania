package test.LoopManiaWorldTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.PathPosition;
import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.Character;
import unsw.loopmania.Slug;
import unsw.loopmania.Zombie;
import unsw.loopmania.Staff;
import unsw.loopmania.Sword;

public class runBattlesTest {

    // Test if the correct enemies die.
    @Test 
    public void testEnemiesDead() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 2)));
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));

        Character c = new Character(pos);
        
        newWorld.setCharacter(c);

        while (newWorld.getEnemies().size() == 0) {
            newWorld.possiblySpawnEnemies();
        }
        List<BasicEnemy> deadEnemies = newWorld.runBattles();

        assertTrue(deadEnemies.get(0) instanceof Slug);  
        assertTrue(c.getHp() == 155);    // Each slug should do 45 damage since it takes the character 15 hits to kill the slug and slugs deal 3 damage.
    }

    // Test if the supporting enemies die.
    @Test 
    public void testSupportingEnemiesDead() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 2)));
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));

        Character c = new Character(pos);
        
        newWorld.setCharacter(c);

        while (newWorld.getEnemies().size() <= 1) {
            newWorld.possiblySpawnEnemies();            // Spawn 2 enemies
        }

        List<BasicEnemy> deadEnemies = newWorld.runBattles();

        assertTrue(deadEnemies.get(0) instanceof Slug);  
        assertTrue(deadEnemies.get(1) instanceof Slug); 
        assertTrue(c.getHp() == 110);    // Each slug should do 45 damage since it takes the character 15 hits to kill the slug and slugs deal 3 damage.
    }

    // There should be no fight between the slug and character since it is out of the slugs battle radius.
    @Test
    public void outOfRange() {
        PathPosition pos2 = new PathPosition(0, Arrays.asList(new Pair<>(1, 10)));
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 0), new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3), new Pair<>(0, 4), new Pair<>(0, 5),
                                                                           new Pair<>(1, 0), new Pair<>(1, 1), new Pair<>(1, 2), new Pair<>(1, 3), new Pair<>(1, 4), new Pair<>(1, 5)));

        Character c = new Character(pos2);
        
        newWorld.setCharacter(c);
        while (newWorld.getEnemies().size() == 0) {
            newWorld.possiblySpawnEnemies();
        }
        List<BasicEnemy> deadEnemies = newWorld.runBattles();

        assertTrue(deadEnemies.size() == 0);   

        assertTrue(c.getHp() == 200);   
    }

    @Test
    public void testAlliedSoldiers() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 2)));
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));

        Character c = new Character(pos);
        AlliedSoldier allied = new AlliedSoldier(pos);

        newWorld.setCharacter(c);
        c.addAlliedSoldier(allied);
        while (newWorld.getEnemies().size() == 0) {
            newWorld.possiblySpawnEnemies();
        }

        List<BasicEnemy> deadEnemies = newWorld.runBattles();

        assertTrue(deadEnemies.get(0) instanceof Slug); 

        assertTrue(c.getHp() == 200);   
        // Since it is assumed soldiers are equipped with a sword they will deal 8 damage. The character without weapons will deal 2 damage.
        // Therefore, in total they will deal 10 damage to the slug each attack and therefore there will only be 3 attacks in total.
        // The slug deals 3 damage so the allied soldier must have taken 9 damage and allied soldiers have 50 hp.
        assertTrue(allied.getHp() == 41);  
    
    }

    @Test
    public void testCharacterDead() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 2)));
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));

        Character c = new Character(pos);
        
        newWorld.setCharacter(c);

        while (newWorld.getEnemies().size() == 0) {
            newWorld.possiblySpawnEnemies();
        }

        newWorld.runBattles();              // Run the battle

        while (newWorld.getEnemies().size() == 0) {
            newWorld.possiblySpawnEnemies();
        }

        newWorld.runBattles();

        while (newWorld.getEnemies().size() == 0) { 
            newWorld.possiblySpawnEnemies();
        }   

        newWorld.runBattles();

        while (newWorld.getEnemies().size() == 0) { 
            newWorld.possiblySpawnEnemies();
        }   

        newWorld.runBattles();

        while (newWorld.getEnemies().size() == 0) {     // 5th slug should kill the character.
            newWorld.possiblySpawnEnemies();
        }   

        newWorld.runBattles();

        assertTrue(c.getHp() == -1);    // Each slug should do 45 damage since it takes the character 15 hits to kill the slug and slugs deal 3 damage.
    }

    @Test
    public void testEnemyTranced() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 2)));
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));

        Character c = new Character(pos);
        // This staff has a 100% chance to trance an enemy instead of the assumed 20%.
        Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0), 1);
        c.setEquippedWeapon(staff);
        
        newWorld.setCharacter(c);

        while (newWorld.getEnemies().size() == 0) {
            newWorld.possiblySpawnEnemies();
        }

        newWorld.runBattles();         
        // If the enemy got tranced on the first attack then it should have only been able to deal 3 damage since it is a slug.
        // The enemy will then die since If the fight ends whilst the enemy is in a trance, the enemy dies.
        assertTrue(c.getHp() == 197);  
    }

    @Test
    public void testAllyTurningIntoZombie() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 2)));
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));

        Character c = new Character(pos);
        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        c.setEquippedWeapon(sword);

        newWorld.setCharacter(c);
        AlliedSoldier allied = new AlliedSoldier(pos);
        c.addAlliedSoldier(allied);

        Zombie zombie = new Zombie(pos, 0); // A zombie with a 100% chance to inflict a critical bite.
        newWorld.addEnemies(zombie);

        // On the first instance of the fight the zombie will turn the allied solider into a zombie.
        // During this first exchange the character and ally will first deal 16 damage in total to the zombie.
        // This is because both the character and ally are equipped with a sword so they deal 8 damage each.
        // After that the zombie will deal 6 damage to the ally and convert that ally into a zombie.
        // Now there is a zombie with 30 - 16 health and the newly created zombie with 50 - 6 health, 14 and 44 hp respectively.
        // For the rest of the fight the character should be alone since it has no allied soldiers now.
        // Since the first zombie has 14 hp it will take the character 2 rounds to kill it taking a total damage of 6 * 2 = 12.
        // The characters hp is now 200 - 12 = 188.
        // The second zombie has 44 hp so it will take 6 rounds to kill it taking a total damage of 6 * 6 = 36.
        // Therefore the character in the end should have 152 hp at the end.
        newWorld.runBattles(); 

        assertTrue(c.getHp() == 152);   
    }


    
}
