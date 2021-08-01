package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.HealthPotion;
import unsw.loopmania.PathPosition;
import unsw.loopmania.TheOneRing;

public class ConsumablesTest {
    @Test
    public void equippedHealthPotion() {
        SimpleIntegerProperty one = new SimpleIntegerProperty(1);
        HealthPotion healthPotion = new HealthPotion(one, one);
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
        Character character = new Character(pos);
        character.setEquippedHealthPotion(healthPotion);
        assertTrue(character.getHp() == character.getMaxHp());
        character.consumePotion();
        assertTrue(character.getHp() == character.getMaxHp());
    }

    @Test
    public void unequippedHealthPotion() {
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
        Character character = new Character(pos);
        character.setHp(50);
        assertTrue(character.getHp() == 50);
        character.consumePotion();
        assertTrue(character.getHp() == 50);
    }

    @Test
    public void theOneRing() {
        SimpleIntegerProperty one = new SimpleIntegerProperty(1);
        TheOneRing theOneRing= new TheOneRing(one, one);
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
        Character character = new Character(pos);
        character.setEquippedRareItem(theOneRing);
        character.setHp(0);
        assertTrue(character.getHp() == 0);
        theOneRing.effect(character, null);
        assertTrue(character.getHp() == character.getMaxHp());
    }
}
