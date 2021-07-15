package unsw.loopmania.CardTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Building;
import unsw.loopmania.CampfireBuilding;
import unsw.loopmania.CampfireCard;

public class CampfireCardTest {

    @Test
    public void testCreateBuilding() {
        SimpleIntegerProperty buildingNodeX = new SimpleIntegerProperty(0);
        SimpleIntegerProperty buildingNodeY = new SimpleIntegerProperty(1);

        CampfireCard campfireCard = new CampfireCard(buildingNodeX, buildingNodeY);
        Building campfireBuilding = campfireCard.createBuilding(buildingNodeX, buildingNodeY);

        // Test that the returned Building is of the subclass CampfireBuilding
        assertTrue(campfireBuilding instanceof CampfireBuilding);
    }

}
