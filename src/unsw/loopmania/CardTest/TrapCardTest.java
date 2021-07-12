package unsw.loopmania.CardTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.Test;
import unsw.loopmania.TrapCard;
import unsw.loopmania.TrapBuilding;
public class TrapCardTest {
    public void testCreateBuilding() {
        SimpleIntegerProperty buildingNodeX = new SimpleIntegerProperty(0);
        SimpleIntegerProperty buildingNodeY = new SimpleIntegerProperty(1);
        TrapCard trapCard = new TrapCard(buildingNodeX, buildingNodeY);
        TrapBuilding trapBuilding = trapCard.createBuilding(buildingNodeX, buildingNodeY);

        assertTrue(trapBuilding.getClass() == TrapBuilding.class);    // Test the returned building is of the class TrapBuilding.
    }
}
