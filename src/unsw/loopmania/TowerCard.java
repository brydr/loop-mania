package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import java.io.File;
public class TowerCard extends Card {
    public TowerCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        TowerBuilding newBuilding = new TowerBuilding(buildingNodeX, buildingNodeY);
        return newBuilding;
    }
    
    @Override
    public Image getImage() {
        Image towerCardImage = new Image((new File("src/images/tower_card.png")).toURI().toString());
        return towerCardImage;
    }

    @Override
    boolean canSpawnOnTile(TileType tileType) {
        return tileType == TileType.PathAdjacentTile;
    }

    
}
