package test.CardTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Building;
import unsw.loopmania.VillageBuilding;
import unsw.loopmania.VillageCard;

public class VillageCardTest {

    @Test
    public void testCreateBuilding() {
        SimpleIntegerProperty buildingNodeX = new SimpleIntegerProperty(0);
        SimpleIntegerProperty buildingNodeY = new SimpleIntegerProperty(1);

        VillageCard villageCard = new VillageCard(buildingNodeX, buildingNodeY);
        Building villageBuilding = villageCard.createBuilding(buildingNodeX, buildingNodeY);

        // Test that the returned Building is of the subclass VillageBuilding
        assertTrue(villageBuilding instanceof VillageBuilding); 
    }

}
