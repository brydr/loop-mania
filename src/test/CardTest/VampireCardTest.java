package test.CardTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Building;
import unsw.loopmania.VampireCastleBuilding;
import unsw.loopmania.VampireCastleCard;

public class VampireCardTest {

    @Test
    public void testCreateBuilding() {
        SimpleIntegerProperty buildingNodeX = new SimpleIntegerProperty(0);
        SimpleIntegerProperty buildingNodeY = new SimpleIntegerProperty(1);

        VampireCastleCard vampireCastleCard = new VampireCastleCard(buildingNodeX, buildingNodeY);
        Building vampireCastleBuilding = vampireCastleCard.createBuilding(buildingNodeX, buildingNodeY);

        // Test that the returned Building is of the subclass VampireCastleBuilding
        assertTrue(vampireCastleBuilding instanceof VampireCastleBuilding);
    }

}
