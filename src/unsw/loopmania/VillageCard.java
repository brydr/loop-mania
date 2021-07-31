package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
public class VillageCard extends Card {
    public VillageCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        VillageBuilding newBuilding = new VillageBuilding(buildingNodeX, buildingNodeY);
        return newBuilding;
    }

    @Override
    public String getImage() {
        return "src/images/village_card.png";
    }


    @Override
    boolean canSpawnOnTile(TileType tileType) {
        return tileType == TileType.PathTile;
    }
}
