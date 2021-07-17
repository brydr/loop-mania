package unsw.loopmania.CardTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.Test;
import unsw.loopmania.CampfireCard;
import unsw.loopmania.CampfireBuilding;
public class CampfireCardTest {
    public void testCreateBuilding() {
        SimpleIntegerProperty buildingNodeX = new SimpleIntegerProperty(0);
        SimpleIntegerProperty buildingNodeY = new SimpleIntegerProperty(1);
        CampfireCard campfireCard = new CampfireCard(buildingNodeX, buildingNodeY);
        CampfireBuilding campfireBuilding = campfireCard.createBuilding(buildingNodeX, buildingNodeY);

        assertTrue(campfireBuilding.getClass() == CampfireBuilding.class);    // Test the returned building is of the class CampfirefireBuilding.
    }
}
