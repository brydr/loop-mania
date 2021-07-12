package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
public class BarracksCard extends Card {
    public BarracksCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        BarracksBuilding newBuilding = new BarracksBuilding(buildingNodeX, buildingNodeY);
        return newBuilding;
    }
}
