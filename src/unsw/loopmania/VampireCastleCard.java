package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents a vampire castle card in the backend game world
 */
public class VampireCastleCard extends Card {
    // TODO = add more types of card
    public VampireCastleCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }    

    @Override
    public Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY) {
        VampireCastleBuilding newBuilding = new VampireCastleBuilding(buildingNodeX, buildingNodeY);
        return newBuilding;
    }

    public String getImage() {
        String vampireCastleCardImage = "src/images/vampire_castle_card.png";
        return vampireCastleCardImage;
    }


    @Override
    boolean canSpawnOnTile(TileType tileType) {
        return tileType == TileType.PathAdjacentTile;
    }
}
