package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * a Card in the world
 * which doesn't move
 */
public abstract class Card extends StaticEntity {
    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public abstract Building createBuilding(SimpleIntegerProperty buildingNodeX, SimpleIntegerProperty buildingNodeY);

    public abstract String getImage();

    /**
     * Indicates whether the card can spawn a building on the given {@code TileType} variant.
     * @return Returns true if the given {@code TileType} is accepted, otherwise false.
     */
    abstract boolean canSpawnOnTile(TileType tileType);
}
