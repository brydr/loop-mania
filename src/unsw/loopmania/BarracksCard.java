package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class BarracksCard extends Card {
    public BarracksCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        BarracksBuilding newBuilding = new BarracksBuilding(buildingNodeX, buildingNodeY);
        return newBuilding;
    }

    @Override
    public String getImage() {
        String barracksCardImage = "src/images/barracks_card.png";
        return barracksCardImage;
    }

    @Override
    boolean canSpawnOnTile(TileType tileType) {
        return tileType == TileType.PathTile;
    }
  
    
}
