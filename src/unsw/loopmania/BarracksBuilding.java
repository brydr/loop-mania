package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class BarracksBuilding extends Building {

    public BarracksBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getImage() {
        String barracksBuildingImage = "src/images/barracks.png";
        return barracksBuildingImage;
    }
    
}
