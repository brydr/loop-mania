package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import java.io.File;

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
    public Image getImage() {
        Image trapCardImage = new Image((new File("src/images/trap_card.png")).toURI().toString());
        return trapCardImage;
    }

    @Override
    boolean canSpawnOnTile(TileType tileType) {
        return tileType == TileType.PathTile;
    }
    
}
