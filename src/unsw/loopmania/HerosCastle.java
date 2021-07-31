package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class HerosCastle extends Building {

    public HerosCastle(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public String getImage() {
        return "src/images/heros_castle.png";
    }
    
}
