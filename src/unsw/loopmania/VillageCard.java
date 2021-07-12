package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
public class VillageCard extends Card {
    public VillageCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        VillageBuilding newBuilding = new VillageBuilding(buildingNodeX, buildingNodeY);
        return newBuilding;
    }
}
