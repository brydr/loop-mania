package unsw.loopmania.CardTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.Test;
import unsw.loopmania.ZombiePitCard;
import unsw.loopmania.ZombiePitBuilding;
public class ZombieCardTest {
 
    @Test
    public void testCreateBuilding() {
        SimpleIntegerProperty buildingNodeX = new SimpleIntegerProperty(0);
        SimpleIntegerProperty buildingNodeY = new SimpleIntegerProperty(1);
        ZombiePitCard zombiePitCard = new ZombiePitCard(buildingNodeX, buildingNodeY);
        ZombiePitBuilding zombiePitBuilding = ZombiePitBuilding.createBuilding(buildingNodeX, buildingNodeY);

        assertTrue(zombiePitBuilding.getClass() == ZombiePitBuilding.class);    // Test the returned building is of the class ZombiePitBuilding.
    }
}
