package test.CardTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Building;
import unsw.loopmania.TrapBuilding;
import unsw.loopmania.TrapCard;

public class TrapCardTest {

    @Test
    public void testCreateBuilding() {
        SimpleIntegerProperty buildingNodeX = new SimpleIntegerProperty(0);
        SimpleIntegerProperty buildingNodeY = new SimpleIntegerProperty(1);
        
        TrapCard trapCard = new TrapCard(buildingNodeX, buildingNodeY);
        Building trapBuilding = trapCard.createBuilding(buildingNodeX, buildingNodeY);

        // Test that the returned Building is of the subclass TrapBuilding
        assertTrue(trapBuilding instanceof TrapBuilding);
    }

}
