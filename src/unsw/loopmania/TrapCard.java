package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
public class TrapCard extends Card {
    public TrapCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        TrapBuilding newBuilding = new TrapBuilding(buildingNodeX, buildingNodeY);
        return newBuilding;
    }
}
