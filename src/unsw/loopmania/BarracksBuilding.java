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

    /**
     * Spawn an allied soldier.
     * @precondition "The Character" is at the barracks: 
     * {@code theCharacter.getX() == getX() && theCharacter.getY() == getY()}
     * @param theCharacter reference to "The Character" of the game.
     */
    public void spawnAlliedSoldiers(Character theCharacter) {
        // New soldier has reference to PathPosition of `theCharacter`
        AlliedSoldier newSoldier = new AlliedSoldier(theCharacter.getPosition());
        theCharacter.addAlliedSoldier(newSoldier);
    }
    
}
