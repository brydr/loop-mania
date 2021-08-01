package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class CampfireBuilding extends Building implements BuildingHelpers {

    public CampfireBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public String getImage() {
        return "src/images/campfire.png";
    }

    @Override
    public void helpChar(Character character) {
        final double sqrDistFromCharacter = Math.pow(character.getX() - this.getX(), 2) +
                                            Math.pow(character.getY() - this.getY(), 2);
        if (sqrDistFromCharacter <= 3 * 3) {
            character.setNumAttack(2);
        } else {
            character.setNumAttack(0);
        }
    }
    
}
