package test.CurrencyTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;

public class CurrencyTest {
    @Test
    public void initialiseGold() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3))); 
        Character character = new Character(pos);
        assertTrue(character.getGold() == 0);
    }

    @Test
    public void addGold() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3))); 
        Character character = new Character(pos);
        character.addGold(10);
        assertTrue(character.getGold() == 10);
    }

    @Test
    public void subtractGold() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3))); 
        Character character = new Character(pos);
        character.addGold(100);
        character.subtractGold(10);
        assertTrue(character.getGold() == 90);
    }
}
