package unsw.loopmania.LoopManiaWorldTest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BarracksBuilding;
import unsw.loopmania.BarracksCard;
import unsw.loopmania.Building;
import unsw.loopmania.CampfireBuilding;
import unsw.loopmania.CampfireCard;
import unsw.loopmania.Card;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.TowerBuilding;
import unsw.loopmania.TowerCard;
import unsw.loopmania.TrapBuilding;
import unsw.loopmania.TrapCard;
import unsw.loopmania.VampireCastleBuilding;
import unsw.loopmania.VampireCastleCard;
import unsw.loopmania.VillageBuilding;
import unsw.loopmania.VillageCard;
import unsw.loopmania.ZombiePitBuilding;
import unsw.loopmania.ZombiePitCard;

public class convertCardToBuildingByCoordinatesTest {
    
    @Test
    public void testCorrectBuilding() {
        var path = Arrays.asList(
            new Pair<Integer, Integer>(0, 1), 
            new Pair<Integer, Integer>(0, 2),
            new Pair<Integer, Integer>(0, 3), 
            new Pair<Integer, Integer>(0, 4)
        );
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, path);
        Card newCard = newWorld.loadRandomCard();
        
        Building newBuilding = newWorld.convertCardToBuildingByCoordinates(newCard.getX(), newCard.getY(), 1, 1);

        // We require that BOTH or NEITHER are true; i.e. logical XNOR or `==`
        assertTrue((newCard instanceof BarracksCard)      == (newBuilding instanceof BarracksBuilding));
        assertTrue((newCard instanceof CampfireCard)      == (newBuilding instanceof CampfireBuilding));
        assertTrue((newCard instanceof TowerCard)         == (newBuilding instanceof TowerBuilding));
        assertTrue((newCard instanceof TrapCard)          == (newBuilding instanceof TrapBuilding));
        assertTrue((newCard instanceof VampireCastleCard) == (newBuilding instanceof VampireCastleBuilding));
        assertTrue((newCard instanceof ZombiePitCard)     == (newBuilding instanceof ZombiePitBuilding));

        
        List<Card> cardEntities = newWorld.getCards();
        // The card should have been destroyed.
        assertTrue(cardEntities.size() == 0);
    }


    // TODO = Test that card at same co-ordinates cannot be spawned twice

    // Tests that building is spawned correctly 
    @Test
    public void testTileTypeBarracks() {
        // Path-tiles are as below
        // Path-adjacent tiles are (1, 1), (1, 2), ..., (1, 5) and (0, 5)
        // Non-path tiles are all other tiles
        var path = List.of(
            new Pair<>(0, 1),
            new Pair<>(0, 2),
            new Pair<>(0, 3),
            new Pair<>(0, 4)
        );
        LoopManiaWorld world = new LoopManiaWorld(20, 20, path);

        // Make a new BarracksCard at initial position in inventory
        Card barracksCard = new BarracksCard(new SimpleIntegerProperty(0),
                                             new SimpleIntegerProperty(0));
        world.addCard(barracksCard);
        
        // Attempt to place a `BarracksCard` (0, 0) on a non-path tile: (1, 1)
        Building newBarracks0 = world.convertCardToBuildingByCoordinates(0, 0, 1, 1);
        assertNull(newBarracks0);
        
        // Attempt to place a `BarracksCard` (0, 1) on a path tile: (0, 1)
        Building newBarracks1 = world.convertCardToBuildingByCoordinates(0, 0, 0, 1);
        assertTrue(newBarracks1 instanceof BarracksBuilding); 
    }

    @Test
    public void testTileTypeCampfire() {
        // Path-tiles are as below
        // Path-adjacent tiles are (1, 1), (1, 2), ..., (1, 5) and (0, 5)
        // Non-path tiles are all other tiles
        var path = List.of(
            new Pair<>(0, 1),
            new Pair<>(0, 2),
            new Pair<>(0, 3),
            new Pair<>(0, 4)
        );
        LoopManiaWorld world = new LoopManiaWorld(20, 20, path);

        // Make a new CampfireCard at initial position in inventory
        Card campfireCard = new CampfireCard(new SimpleIntegerProperty(0),
                                             new SimpleIntegerProperty(0));
        world.addCard(campfireCard);

        // Attempt to place a `CampfireCard` (0, 0) on a pathtile (0, 1)
        Building newCampfire0 = world.convertCardToBuildingByCoordinates(0, 0, 0, 1);
        assertNull(newCampfire0);
        
        // Attempt to place a `CampfireCard` (0, 0) on a non-path tile (path-adjacent): (1, 1)
        Building newCampfire1 = world.convertCardToBuildingByCoordinates(0, 0, 0, 1);
        assertTrue(newCampfire1 instanceof CampfireBuilding);

        // Re-add our `CampfireCard` since it would've been removed from the world
        world.addCard(campfireCard);

        // Attempt to place a `CampfireCard` (0, 0) on a non-path tile: (10, 10)
        Building newCampfire2 = world.convertCardToBuildingByCoordinates(0, 0, 10, 10);
        assertTrue(newCampfire2 instanceof CampfireBuilding);
    }

    @Test
    public void testTileTypeTower() {
        // Path-tiles are as below
        // Path-adjacent tiles are (1, 1), (1, 2), ..., (1, 5) and (0, 5)
        // Non-path tiles are all other tiles
        var path = List.of(
            new Pair<>(0, 1),
            new Pair<>(0, 2),
            new Pair<>(0, 3),
            new Pair<>(0, 4)
        );
        LoopManiaWorld world = new LoopManiaWorld(20, 20, path);

        // Make a new TowerCard at initial position in inventory
        Card towerCard = new TowerCard(new SimpleIntegerProperty(0),
                                       new SimpleIntegerProperty(0));
        world.addCard(towerCard);

        // Attempt to place a `TowerCard` (0, 0) on a pathtile (0, 1)
        Building newTower0 = world.convertCardToBuildingByCoordinates(0, 0, 0, 1);
        assertNull(newTower0);
        
        // Attempt to place a `TowerCard` (0, 0) on a non-adjacent, non-path tile: (10, 10)
        Building newTower1 = world.convertCardToBuildingByCoordinates(0, 0, 10, 10);
        assertNull(newTower1);

        // Attempt to place a `TowerCard` (0, 0) on an adjacent, non-path tile: (1, 1)
        Building newTower2 = world.convertCardToBuildingByCoordinates(0, 0, 1, 1);
        assertTrue(newTower2 instanceof TowerBuilding);
    }


    @Test
    public void testTileTypeTrap() {
        // Path-tiles are as below
        // Path-adjacent tiles are (1, 1), (1, 2), ..., (1, 5) and (0, 5)
        // Non-path tiles are all other tiles
        var path = List.of(
            new Pair<>(0, 1),
            new Pair<>(0, 2),
            new Pair<>(0, 3),
            new Pair<>(0, 4)
        );
        LoopManiaWorld world = new LoopManiaWorld(20, 20, path);

        // Make a new TrapCard at initial position in inventory
        Card trapCard = new TrapCard(new SimpleIntegerProperty(0),
                                     new SimpleIntegerProperty(0));
        world.addCard(trapCard);
        
        // Attempt to place a `TrapCard` (0, 0) on a non-path tile: (1, 1)
        Building newTrap0 = world.convertCardToBuildingByCoordinates(0, 0, 1, 1);
        assertNull(newTrap0);
        
        // Attempt to place a `TrapCard` (0, 1) on a path tile: (0, 1)
        Building newTrap1 = world.convertCardToBuildingByCoordinates(0, 0, 0, 1);
        assertTrue(newTrap1 instanceof TrapBuilding); 
    }

    @Test
    public void testTileTypeVampireCastle() {
        // Path-tiles are as below
        // Path-adjacent tiles are (1, 1), (1, 2), ..., (1, 5) and (0, 5)
        // Non-path tiles are all other tiles
        var path = List.of(
            new Pair<>(0, 1),
            new Pair<>(0, 2),
            new Pair<>(0, 3),
            new Pair<>(0, 4)
        );
        LoopManiaWorld world = new LoopManiaWorld(20, 20, path);

        // Make a new VampireCastleCard at initial position in inventory
        Card vampireCastleCard = new VampireCastleCard(new SimpleIntegerProperty(0),
                                                       new SimpleIntegerProperty(0));
        world.addCard(vampireCastleCard);

        // Attempt to place a `VampireCastleCard` (0, 0) on a pathtile (0, 1)
        Building newVampireCastle0 = world.convertCardToBuildingByCoordinates(0, 0, 0, 1);
        assertNull(newVampireCastle0);
        
        // Attempt to place a `VampireCastleCard` (0, 0) on a non-adjacent, non-path tile: (10, 10)
        Building newVampireCastle1 = world.convertCardToBuildingByCoordinates(0, 0, 10, 10);
        assertNull(newVampireCastle1);

        // Attempt to place a `VampireCastleCard` (0, 0) on an adjacent, non-path tile: (1, 1)
        Building newVampireCastle2 = world.convertCardToBuildingByCoordinates(0, 0, 1, 1);
        assertTrue(newVampireCastle2 instanceof VampireCastleBuilding);
    }

    @Test
    public void testTileTypeVillage() {
        // Path-tiles are as below
        // Path-adjacent tiles are (1, 1), (1, 2), ..., (1, 5) and (0, 5)
        // Non-path tiles are all other tiles
        var path = List.of(
            new Pair<>(0, 1),
            new Pair<>(0, 2),
            new Pair<>(0, 3),
            new Pair<>(0, 4)
        );
        LoopManiaWorld world = new LoopManiaWorld(20, 20, path);

        // Make a new VillageCard at initial position in inventory
        Card villageCard = new VillageCard(new SimpleIntegerProperty(0),
                                           new SimpleIntegerProperty(0));
        world.addCard(villageCard);
        
        // Attempt to place a `VillageCard` (0, 0) on a non-path tile: (1, 1)
        Building newVillage0 = world.convertCardToBuildingByCoordinates(0, 0, 1, 1);
        assertNull(newVillage0);
        
        // Attempt to place a `VillageCard` (0, 1) on a path tile: (0, 1)
        Building newVillage1 = world.convertCardToBuildingByCoordinates(0, 0, 0, 1);
        assertTrue(newVillage1 instanceof VillageBuilding); 
    }

    @Test
    public void testTileTypeZombiePit() {
        // Path-tiles are as below
        // Path-adjacent tiles are (1, 1), (1, 2), ..., (1, 5) and (0, 5)
        // Non-path tiles are all other tiles
        var path = List.of(
            new Pair<>(0, 1),
            new Pair<>(0, 2),
            new Pair<>(0, 3),
            new Pair<>(0, 4)
        );
        LoopManiaWorld world = new LoopManiaWorld(20, 20, path);

        // Make a new ZombiePitCard at initial position in inventory
        Card zombiePitCard = new ZombiePitCard(new SimpleIntegerProperty(0),
                                               new SimpleIntegerProperty(0));
        world.addCard(zombiePitCard);

        // Attempt to place a `ZombiePitCard` (0, 0) on a pathtile (0, 1)
        Building newZombiePit0 = world.convertCardToBuildingByCoordinates(0, 0, 0, 1);
        assertNull(newZombiePit0);
        
        // Attempt to place a `ZombiePitCard` (0, 0) on a non-adjacent, non-path tile: (10, 10)
        Building newZombiePit1 = world.convertCardToBuildingByCoordinates(0, 0, 10, 10);
        assertNull(newZombiePit1);

        // Attempt to place a `ZombiePitCard` (0, 0) on an adjacent, non-path tile: (1, 1)
        Building newZombiePit2 = world.convertCardToBuildingByCoordinates(0, 0, 1, 1);
        assertTrue(newZombiePit2 instanceof ZombiePitBuilding);
    }
}
