package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * a basic form of building in the world
 */
public class VampireCastleBuilding extends Building {
    // TODO = add more types of building, and make sure buildings have effects on entities as required by the spec
    public VampireCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getImage() {
        String vampireCastleBuildingImage = "src/images/vampire_castle_building_transparent.png";
        //String vampireCastleBuildingImage = "src/images/vampire_castle_building_purple_background.png";
        return vampireCastleBuildingImage;
    }
}
