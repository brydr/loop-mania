package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Consumable extends EquippableItem {
	public Consumable(SimpleIntegerProperty x, SimpleIntegerProperty y, int buyPrice, int sellPrice) {
		super(x, y, buyPrice, sellPrice);
	}
}
