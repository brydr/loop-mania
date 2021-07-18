package test.LoopManiaWorldTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import unsw.loopmania.Card;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Character;

public class loadRandomCardTest {
    
    @Test
    public void testMaximumCard() {
        // NOTE: This is fixed-size, and List.of is immutable, so we should use 
        // ArrayList< >(List.of( ... )) if we want mutability & growability
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
        LoopManiaWorld world = new LoopManiaWorld(9, 9, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));

        Character c = new Character(pos);
        world.setCharacter(c);
        
        for (int i = 0; i < 10; i++) {
            world.loadRandomCard();
        }

        List<Card> cardEntities = world.getCards();
        // Only a maximum of 9 cards so the first card should be removed.
        System.out.println(cardEntities.size());
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
