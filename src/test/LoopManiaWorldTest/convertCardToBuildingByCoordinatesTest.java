package test.LoopManiaWorldTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

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
}
