package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class CampfireBuilding extends Building {

    public CampfireBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public String getImage() {
        String campfireBuildingImage = "src/images/campfire.png";
        return campfireBuildingImage;
    }
    
}
