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
        int currentHp = character.getHp();
        currentHp += 20;
        character.setHp(currentHp);
    }
}
