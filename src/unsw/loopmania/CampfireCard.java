package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class CampfireCard extends Card {
    
    public CampfireCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        CampfireBuilding newBuilding = new CampfireBuilding(buildingNodeX, buildingNodeY);
        return newBuilding;
    }

    @Override
    public String getImage() {
        return "src/images/campfire_card.png";
    }

    @Override
    boolean canSpawnOnTile(TileType tileType) {
        return (tileType == TileType.PathAdjacentTile) 
            || (tileType == TileType.NonPathTile);
    }

}
