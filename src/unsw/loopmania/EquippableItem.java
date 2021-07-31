package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public abstract  class EquippableItem extends BasicItem {
	protected int durability = 1;
	public EquippableItem(SimpleIntegerProperty x, SimpleIntegerProperty y, int buyPrice, int sellPrice) {
		super(x, y, buyPrice, sellPrice);
	}

	/**
	 * Checks if gear is broken
	 * @return {@code true} if gear is broken else {@code false}
	 */
	public boolean isBroken() {
		return durability <= 0 ? true : false;
	}

	public abstract String getEmptySlotId();
	public abstract String getEmptySlotImage();
}
