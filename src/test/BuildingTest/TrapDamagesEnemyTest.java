package test.BuildingTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Character;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.TrapBuilding;
import unsw.loopmania.Vampire;

public class TrapDamagesEnemyTest {
    @Test
    public void trapDamagesEnemyTest() {
        // Setup world
        var path = Arrays.asList(
            new Pair<>(0, 1),
            new Pair<>(0, 2),
            new Pair<>(0, 3),
            new Pair<>(0, 4),
            new Pair<>(0, 5),
            new Pair<>(0, 6),
            new Pair<>(0, 7)
        );
        PathPosition characterPos = new PathPosition(path.indexOf(new Pair<>(0, 7)), path);
        Character theCharacter = new Character(characterPos);
        LoopManiaWorld world = new LoopManiaWorld(20, 20, path);
        world.setCharacter(theCharacter);

        // Setup trap
        TrapBuilding trap = new TrapBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(2));
        world.addBuilding(trap);
        
        // Spawn a vampire near a trap
        Vampire vampire = new Vampire(
            new PathPosition(path.indexOf(new Pair<>(0, 1)), path)
        );
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
