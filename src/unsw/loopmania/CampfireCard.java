package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import java.io.File;
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
    public Image getImage() {
        Image campfireCardImage = new Image((new File("src/images/campfire_card.png")).toURI().toString());
        return campfireCardImage;
    }

    @Override
    boolean canSpawnOnTile(TileType tileType) {
        return (tileType == TileType.PathAdjacentTile) 
            || (tileType == TileType.NonPathTile);
    }

    
}
