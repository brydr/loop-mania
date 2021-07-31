package test.BuildingTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.javatuples.Pair;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.GoalEvaluator;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.TrapBuilding;
import unsw.loopmania.Vampire;

public class TrapDamagesEnemyTest {
    @Test
    public void trapDamagesEnemyTest() {
        // Setup world
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
        LoopManiaWorld world = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));

        Character c = new Character(pos);

        world.setCharacter(c);

        String file_name = this.getClass().getResource("/basic_world_with_player.json").getFile();
        JSONObject JSONGoals = GoalEvaluator.parseJSON(file_name);
        world.setGoals(JSONGoals);

        // Setup trap
        TrapBuilding trap = new TrapBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(2));
        world.addBuilding(trap);

        // Spawn a vampire near a trap
        Vampire vampire = new Vampire(pos);
        world.addEnemies(vampire);

        // Assert pre-condition that Vampire has full health
        // No tick has occured yet so Vampire should have full HP
        assertEquals(vampire.getHp(), Vampire.STARTING_HP);

        // Tick until Vampire steps on trap
        while (!(vampire.getX() == trap.getX() && vampire.getY() == trap.getY())) {
            world.runTickMoves();
        }
        // When Vampire lands on Trap, 30 damage is dealt - no need to go to next tick
        assertEquals(vampire.getHp(), Vampire.STARTING_HP - TrapBuilding.TRAP_DAMAGE);

    }
}
