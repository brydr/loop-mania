package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class VillageBuilding extends Building {

    public VillageBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public String getImage() {
        String VillageBuildingImage = "src/images/village.png";
        return VillageBuildingImage;
    }
}
