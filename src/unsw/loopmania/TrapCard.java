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

    @Override
    public String getImage() {
        return "src/images/trap_card.png";
    }


    @Override
    boolean canSpawnOnTile(TileType tileType) {
        return tileType == TileType.PathTile;
    }

}
