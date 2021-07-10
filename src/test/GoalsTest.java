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
        world.goal.experience = 50;
        assertFalse(world.goal.getCompletion());
        character.exp.setExp = 50;
        assertTrue(world.goal.getCompletion());
    }

    @Test
    public void goldGoal() {
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath);
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3))); 
        Character character = new Character(pos);
        world.goal.gold = 50;
        assertFalse(world.goal.getCompletion());
        character.gold.setGold = 50;
        assertTrue(world.goal.getCompletion());
    }

    @Test
    public void cyclesGoal() {
        LoopManiaWorld world = new LoopManiaWorld(8, 14, orderedPath);
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3))); 
        Character character = new Character(pos);
        world.goal.cycles = 3;
        assertFalse(world.goal.getCompletion());
        character.cycles.setCycles = 3;
        assertTrue(world.goal.getCompletion());
    }
}
