package unsw.loopmania;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

public class ZombiePitCard extends Card {
    public ZombiePitCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        ZombiePitBuilding newBuilding = new ZombiePitBuilding(buildingNodeX, buildingNodeY);
        return newBuilding;
    }

    public String getImage() {
        String zombiePitCardImage = "src/images/zombie_pit_card.png";
        return zombiePitCardImage;
    }


    @Override
    boolean canSpawnOnTile(TileType tileType) {
        return tileType == TileType.PathAdjacentTile;
    }
}
