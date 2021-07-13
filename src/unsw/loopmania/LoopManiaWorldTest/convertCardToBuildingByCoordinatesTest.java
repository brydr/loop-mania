package unsw.loopmania.LoopManiaWorldTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Card;
import unsw.loopmania.Building;

public class convertCardToBuildingByCoordinatesTest {
    
    @Test
    public void testCorrectBuilding() {
        LoopManiaWorld newWorld = new LoopManiaWorld(20, 20, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3), new Pair<>(0, 4)));
        Card newCard = newWorld.loadRandomCard();
        
        Building newBuilding = convertCardToBuildingByCoordinates(newCard.getX(), newCard.getY(), new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        if (newCard.getClass() == BarracksCard.class) {
            assertTrue(newBuilding.getClass() == BarracksBuilding.class);
        } else if (newCard.getClass() == CampfireCard.class) {
            assertTrue(newBuilding.getClass() == CampfireBuilding.class);
        } else if (newCard.getClass() == TowerCard.class) {
            assertTrue(newBuilding.getClass() == TowerBuilding.class);
        } else if (newCard.getClass() == TrapCard.class) {
            assertTrue(newBuilding.getClass() == TrapBuilding.class);
        } else if (newCard.getClass() == VampireCastleCard.class) {
            assertTrue(newBuilding.getClass() == VampireCastleBuilding.class);
        } else if (newCard.getClass() == VillageCard.class) {
            assertTrue(newBuilding.getClass() == VillageBuilding.class);
        } else if (newCard.getClass() == ZombiePitCard.class) {
            assertTrue(newBuilding.getClass() == ZombiePitBuilding.class);
        } 

        List<Card> cardEntities = newWorld.getCards();

        assertTrue(cardEntities.size() == 0);   // The card should have been destroyed.
    }
}
