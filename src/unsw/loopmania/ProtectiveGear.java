package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class ProtectiveGear extends EquippableItem {
	// private final Character wearer;

	public ProtectiveGear(SimpleIntegerProperty x, SimpleIntegerProperty y, int buyPrice, int sellPrice) {
		super(x, y, buyPrice, sellPrice);
	}

	public abstract int calculateDamage(int attackPower);
}
