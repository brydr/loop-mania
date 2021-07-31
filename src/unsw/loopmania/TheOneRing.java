package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
public class TheOneRing extends Item implements RareItem {
    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y);
	}
    
    @Override
    public boolean effect(Character character) {
        if (character.getHp() <= 0) {
            character.setEquippedRareItem(null);
            character.setHp(character.getMaxHp());
            return true;
        }
        return false;
    }

    @Override
	public String getImage() {
        String oneRingImage = "src/images/the_one_ring.png";
        return oneRingImage;
    }
}
