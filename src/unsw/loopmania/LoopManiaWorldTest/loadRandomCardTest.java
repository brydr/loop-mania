package unsw.loopmania.LoopManiaWorldTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Card;
public class loadRandomCardTest {
    
    @Test
    public void testMaximumCard() {
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3), new Pair<>(0, 4)));
        newWorld.loadRandomCard();
        newWorld.loadRandomCard();
        newWorld.loadRandomCard();
        newWorld.loadRandomCard();
        newWorld.loadRandomCard();
        newWorld.loadRandomCard();
        newWorld.loadRandomCard();
        newWorld.loadRandomCard();
        newWorld.loadRandomCard();
        newWorld.loadRandomCard();
        List<Card> cardEntities = newWorld.getCards();
        assertTrue(cardEntities.size() == 9);   // Only a maximum of 9 cards so the first card should be removed.
    }


    public void testCorrectCard() {
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3), new Pair<>(0, 4)));

        newWorld.loadRandomCard();
        List<Card> cardEntities = newWorld.getCards();
        assertTrue(cardEntities.size() == 1);
    }
}
