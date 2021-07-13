package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import java.io.File;

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

    @Override
    public Image getImage() {
        Image vampireCastleCardImage = new Image((new File("src/images/vampire_castle_card.png")).toURI().toString());
        return vampireCastleCardImage;
    }
}
