package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public abstract  class EquippableItem extends BasicItem {
	public EquippableItem(SimpleIntegerProperty x, SimpleIntegerProperty y, int buyPrice, int sellPrice) {
		super(x, y, buyPrice, sellPrice);
	}
}