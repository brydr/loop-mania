package unsw.loopmania.CardTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Building;
import unsw.loopmania.TowerBuilding;
import unsw.loopmania.TowerCard;

public class TowerCardTest {

    @Test
    public void testCreateBuilding() {
        SimpleIntegerProperty buildingNodeX = new SimpleIntegerProperty(0);
        SimpleIntegerProperty buildingNodeY = new SimpleIntegerProperty(1);
        
        TowerCard towerCard = new TowerCard(buildingNodeX, buildingNodeY);
        Building towerBuilding = towerCard.createBuilding(buildingNodeX, buildingNodeY);

        // Test that the returned Building is of the subclass TowerBuilding
        assertTrue(towerBuilding instanceof TowerBuilding);
    }

}
