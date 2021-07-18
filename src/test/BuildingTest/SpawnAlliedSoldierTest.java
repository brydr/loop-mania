package test.BuildingTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BarracksBuilding;
import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;

public class SpawnAlliedSoldierTest {
    @Test
    public void spawnAlliedSoldierTest() {
        var orderedPath = Arrays.asList(new Pair<>(0, 1), 
                                        new Pair<>(0, 2), 
                                        new Pair<>(0, 3), 
                                        new Pair<>(0, 4));
        PathPosition characterPos = new PathPosition(0, orderedPath);
        Character theCharacter = new Character(characterPos);
        LoopManiaWorld world = new LoopManiaWorld(20, 20, orderedPath);
        world.setCharacter(theCharacter);

        // Testing precondition that The Character has no allied soldiers, and is at (0, 1)
        assertTrue(theCharacter.getX() == 0 
                && theCharacter.getY() == 1);
        assertEquals(theCharacter.getListAlliedSoldiers().size(), 0);
        
        // Make a barracks
        final int barracksX = 0, barracksY = 2;
        BarracksBuilding barracks = new BarracksBuilding(new SimpleIntegerProperty(barracksX), 
                                                         new SimpleIntegerProperty(barracksY));
        world.addBuilding(barracks);

        // Make some moves
        world.runTickMoves();

        // Testing precondition that character is now at (0, 2)
        assertTrue(theCharacter.getX() == barracksX 
                && theCharacter.getY() == barracksY);
        
        // The soldier should now have an allied soldier
        assertEquals(theCharacter.getListAlliedSoldiers().size(), 1);
        
        world.runTickMoves();

        // The soldier should still have an allied soldier
        assertEquals(theCharacter.getListAlliedSoldiers().size(), 1);
    }
}
