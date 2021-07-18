package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class BarracksBuilding extends Building implements BuildingHelpers {

    public BarracksBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public String getImage() {
        String barracksBuildingImage = "src/images/barracks.png";
        return barracksBuildingImage;
    }

    /**
     * Spawn an allied soldier.
     * @precondition "character" is at the barracks: 
     * {@code character.getX() == getX() && character.getY() == getY()}
     * @param theCharacter reference to "The Character" of the game.
     */
    public void helpChar(Character character) {
        // New soldier has reference to PathPosition of `character`
        AlliedSoldier newSoldier = new AlliedSoldier(character.getPosition());
        character.addAlliedSoldier(newSoldier);
    }
    
}
