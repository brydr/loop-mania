package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.CacheHint;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Character;
import unsw.loopmania.HealthPotion;
import unsw.loopmania.PathPosition;
import unsw.loopmania.TheOneRing;

public class ConsumablesTest {
    @Test
    public void addHealthFull() {
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath);
        SimpleIntegerProperty one = new SimpleIntegerProperty(1);
        HealthPotion healthPotion = new HealthPotion(one, one);
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3))); 
        Character character = new Character(pos);
        assertTrue(character.getHp() == 100);
        world.consumeHealthPotion();
        assertTrue(character.getHp() == 100);
    }  
    
    @Test
    public void addHealthPartial() {
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath);
        SimpleIntegerProperty one = new SimpleIntegerProperty(1);
        HealthPotion healthPotion = new HealthPotion(one, one);
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3))); 
        Character character = new Character(pos);
        character.setHp(50)
        assertTrue(character.getHp() == 50);
        world.consumeHealthPotion();
        assertTrue(character.getHp() == 80);
    }

    public void theOneRing() {
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath);
        SimpleIntegerProperty one = new SimpleIntegerProperty(1);
        TheOneRing theOneRing= new TheOneRing(one, one);
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3))); 
        Character character = new Character(pos);
        character.setHp(0)
        assertTrue(character.getHp() == 0);
        world.useTheOneRing();
        assertTrue(character.getHp() == 100);
    }
}
