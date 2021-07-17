package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Armour extends ProtectiveGear {
	public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y);
	}

	@Override
	public int calculateDamage(int attackPower) {
		return attackPower / 2;
	}

	public String getImage() {
        String armourImage = "src/images/armour.png";
        return armourImage;
    }
}
