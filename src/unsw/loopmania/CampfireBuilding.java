package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class CampfireBuilding extends Building implements BuildingHelpers {

    public CampfireBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public String getImage() {
        String campfireBuildingImage = "src/images/campfire.png";
        return campfireBuildingImage;
    }

    public void helpChar(Character character) {
        if (Math.pow((character.getX()-this.getX()), 2) +  Math.pow((character.getY()-this.getY()), 2) <= Math.pow(3, 2)) {
            character.setAttackTwice(true);
        } else {
            character.setAttackTwice(false);
        }
    }
    
}
