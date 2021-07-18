package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class VillageBuilding extends Building implements BuildingHelpers {

    public VillageBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public String getImage() {
        String VillageBuildingImage = "src/images/village.png";
        return VillageBuildingImage;
    }

    public void helpChar(Character character) {
        if (Math.pow((character.getX()-this.getX()), 2) +  Math.pow((character.getY()-this.getY()), 2) == 0) {
            int currentHp = character.getHp();
            currentHp += 20;
            character.setHp(currentHp);
        }
    }
}
