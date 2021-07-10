package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import unsw.loopmania.Character;
import unsw.loopmania.Gold;
import unsw.loopmania.PathPosition;

public class CurrencyTest {
    @Test
    public void initialiseGold() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3))); 
        Character character = new Character(pos, new Gold());
        assertTrue(character.gold.getGold() == 0);
    }

    @Test
    public void addGold() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3))); 
        Character character = new Character(pos, new Gold());
        character.gold.addGold(10);
        assertTrue(character.gold.getGold() == 10);
    }

    @Test
    public void subtractGold() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3))); 
        Character character = new Character(pos, new Gold());
        character.gold.addGold(100);
        character.gold.subtractGold(10);
        assertTrue(character.gold.getGold() == 90);
    }
}
