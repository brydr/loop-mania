package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;


public class GoalsTest {
    @Test
    public void experienceGoal() {
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath);
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3))); 
        Character character = new Character(pos);
        world.expGoal.experience = 50;
        assertFalse(world.expGoal.getCompletion());
        character.addExperience(50);
        assertTrue(world.expGoal.getCompletion());
    }

    @Test
    public void goldGoal() {
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath);
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3))); 
        Character character = new Character(pos);
        world.goldGoal.gold = 50;
        assertFalse(world.goldGoal.getCompletion());
        character.gold.addGold(50);
        assertTrue(world.goldGoal.getCompletion());
    }

    @Test
    public void cyclesGoal() {
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath);
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3))); 
        Character character = new Character(pos);
        world.cycleGoal.cycles = 1;
        assertFalse(world.cycleGoal.getCompletion());
        character.addCycles();
        assertTrue(world.cycleGoal.getCompletion());
    }
}
