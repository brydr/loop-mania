package test.CardTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Building;
import unsw.loopmania.ZombiePitBuilding;
import unsw.loopmania.ZombiePitCard;
public class ZombieCardTest {
 
    @Test
    public void testCreateBuilding() {
        SimpleIntegerProperty buildingNodeX = new SimpleIntegerProperty(0);
        SimpleIntegerProperty buildingNodeY = new SimpleIntegerProperty(1);

        ZombiePitCard zombiePitCard = new ZombiePitCard(buildingNodeX, buildingNodeY);
        Building zombiePitBuilding = zombiePitCard.createBuilding(buildingNodeX, buildingNodeY);

        // Test that the returned Building is of the subclass ZombiePitBuilding
        assertTrue(zombiePitBuilding instanceof ZombiePitBuilding);
    }
}
