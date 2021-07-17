package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class TrapCard extends Card {
    public TrapCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        TrapBuilding newBuilding = new TrapBuilding(buildingNodeX, buildingNodeY);
        return newBuilding;
    }

    public String getImage() {
        String trapCardImage = "src/images/trap_card.png";
        return trapCardImage;
    }


    @Override
    boolean canSpawnOnTile(TileType tileType) {
        return tileType == TileType.PathTile;
    }
}
    
