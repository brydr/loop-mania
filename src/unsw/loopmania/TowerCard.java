package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class TowerCard extends Card {
    public TowerCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        TowerBuilding newBuilding = new TowerBuilding(buildingNodeX, buildingNodeY);
        return newBuilding;
    }
    
    public String getImage() {
        String towerCardImage = "src/images/tower_card.png";
        return towerCardImage;
    }
}
