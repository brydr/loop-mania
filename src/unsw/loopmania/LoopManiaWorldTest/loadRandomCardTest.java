package unsw.loopmania.LoopManiaWorldTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.Card;
import unsw.loopmania.LoopManiaWorld;

public class loadRandomCardTest {
    
    @Test
    public void testMaximumCard() {
        // NOTE: This is fixed-size, and List.of is immutable, so we should use 
        // ArrayList< >(List.of( ... )) if we want mutability & growability
        var path = Arrays.asList(
            new Pair<Integer, Integer>(0, 1), 
            new Pair<Integer, Integer>(0, 2), 
            new Pair<Integer, Integer>(0, 3), 
            new Pair<Integer, Integer>(0, 4)
        );
        LoopManiaWorld newWorld = new LoopManiaWorld(9, 9, path);
        
        for (int i = 0; i < 10; i++) {
            newWorld.loadRandomCard();
        }

        List<Card> cardEntities = newWorld.getCards();
        // Only a maximum of 9 cards so the first card should be removed.
        assertEquals(cardEntities.size(), 9);
    }

    @Test
    public void testCorrectCard() {
        var path = Arrays.asList(
            new Pair<Integer, Integer>(0, 1), 
            new Pair<Integer, Integer>(0, 2), 
            new Pair<Integer, Integer>(0, 3), 
            new Pair<Integer, Integer>(0, 4)
        );
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, path);

        newWorld.loadRandomCard();
        List<Card> cardEntities = newWorld.getCards();
        assertTrue(cardEntities.size() == 1);
    }
}
