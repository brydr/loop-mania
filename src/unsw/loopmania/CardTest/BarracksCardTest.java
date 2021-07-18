package unsw.loopmania.CardTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BarracksBuilding;
import unsw.loopmania.BarracksCard;
import unsw.loopmania.Building;

public class BarracksCardTest {

    @Test
    public void testCreateBuilding() {
        SimpleIntegerProperty buildingNodeX = new SimpleIntegerProperty(0);
        SimpleIntegerProperty buildingNodeY = new SimpleIntegerProperty(1);
        
        BarracksCard barracksCard = new BarracksCard(buildingNodeX, buildingNodeY);
        Building barracksBuilding = barracksCard.createBuilding(buildingNodeX, buildingNodeY);

        // Test that the returned Building is of the subclass BarracksBuilding
        assertTrue(barracksBuilding instanceof BarracksBuilding);
    }

}
