package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
public class CampirefireCard extends Card {
    public CampirefireCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        CampirefireBuilding newBuilding = new CampirefireBuilding(buildingNodeX, buildingNodeY);
        return newBuilding;
    }
}
