package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
public class ZombiePitCard extends Card {
    public ZombiePitCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        ZombiePitBuilding newBuilding = new ZombiePitBuilding(buildingNodeX, buildingNodeY);
        return newBuilding;
    }
}
