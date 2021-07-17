package unsw.loopmania.CardTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.Test;
import unsw.loopmania.VampireCastleCard;
import unsw.loopmania.VampireCastleBuilding;
public class VampireCardTest {
    public void testCreateBuilding() {
        SimpleIntegerProperty buildingNodeX = new SimpleIntegerProperty(0);
        SimpleIntegerProperty buildingNodeY = new SimpleIntegerProperty(1);
        VampireCastleCard vampireCastleCard = new VampireCastleCard(buildingNodeX, buildingNodeY);
        VampireCastleBuilding vampireCastleBuilding = vampireCastleCard.createBuilding(buildingNodeX, buildingNodeY);

        assertTrue(vampireCastleBuilding.getClass() == ZombiePitBuilding.class);    // Test the returned building is on the class VampireCastleBuilding.
    }
}
