package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class ZombiePitBuilding extends Building {

    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getImage() {
        String zombiePitImage = "src/images/zombie_pit.png";
        return zombiePitImage;
    }
}
