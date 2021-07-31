package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class ZombiePitCard extends Card {
    public ZombiePitCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        ZombiePitBuilding newBuilding = new ZombiePitBuilding(buildingNodeX, buildingNodeY);
        return newBuilding;
    }

    @Override
    public String getImage() {
        return "src/images/zombie_pit_card.png";
    }


    @Override
    boolean canSpawnOnTile(TileType tileType) {
        return tileType == TileType.PathAdjacentTile;
    }
}
