package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

/**
 * a Card in the world
 * which doesn't move
 */
public abstract class Card extends StaticEntity {
    // TODO = implement other varieties of card than VampireCastleCard
    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public abstract Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY);
    public abstract Image getImage();

    /**
     * Indicates whether the card can spawn a building on the given {@code TileType} variant. 
     * @return Returns true if the given {@code TileType} is accepted, otherwise false.
     */
    abstract boolean canSpawnOnTile(TileType tileType);
}
