package unsw.loopmania.CardTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.Test;
import unsw.loopmania.TowerCard;
import unsw.loopmania.TowerBuilding;
public class TowerCardTest {
    public void testCreateBuilding() {
        SimpleIntegerProperty buildingNodeX = new SimpleIntegerProperty(0);
        SimpleIntegerProperty buildingNodeY = new SimpleIntegerProperty(1);
        TowerCard towerCard = new TowerCard(buildingNodeX, buildingNodeY);
        TowerBuilding towerBuilding = towerCard.createBuilding(buildingNodeX, buildingNodeY);

        assertTrue(towerBuilding.getClass() == TowerBuilding.class);    // Test the returned building is of the class TowerBuilding.
    }
}
