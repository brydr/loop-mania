package unsw.loopmania.CardTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.Test;
import unsw.loopmania.BarracksCard;
import unsw.loopmania.BarracksBuilding;
public class BarracksCardTest {
    public void testCreateBuilding() {
        SimpleIntegerProperty buildingNodeX = new SimpleIntegerProperty(0);
        SimpleIntegerProperty buildingNodeY = new SimpleIntegerProperty(1);
        BarracksCard barracksCard = new BarracksCard(buildingNodeX, buildingNodeY);
        BarracksBuilding barracksBuilding = barracksCard.createBuilding(buildingNodeX, buildingNodeY);

        assertTrue(barracksBuilding.getClass() == BarracksBuilding.class);    // Test the returned building is of the class BarracksBuilding.
    }
}
