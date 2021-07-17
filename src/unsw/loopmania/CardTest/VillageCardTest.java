package unsw.loopmania.CardTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.Test;
import unsw.loopmania.VillageCard;
import unsw.loopmania.VillageBuilding;
public class VillageCardTest {
    public void testCreateBuilding() {
        SimpleIntegerProperty buildingNodeX = new SimpleIntegerProperty(0);
        SimpleIntegerProperty buildingNodeY = new SimpleIntegerProperty(1);
        VillageCard villageCard = new VillageCard(buildingNodeX, buildingNodeY);
        VillageBuilding villageBuilding = villageCard.createBuilding(buildingNodeX, buildingNodeY);

        assertTrue(villageBuilding.getClass() == VillageBuilding.class);    // Test the returned building is of the class VillageBuilding.
    }
}
