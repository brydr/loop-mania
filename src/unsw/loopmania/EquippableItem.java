package unsw.loopmania;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract  class EquippableItem extends BasicItem {
	protected IntegerProperty durability;
	private final static int MAX_DURABILITY = 30;
	public EquippableItem(SimpleIntegerProperty x, SimpleIntegerProperty y, int buyPrice, int sellPrice) {
		super(x, y, buyPrice, sellPrice);
		this.durability = new SimpleIntegerProperty();
		this.durability.setValue(MAX_DURABILITY);
	}

	/**
	 * Checks if gear is broken
	 * @return {@code true} if gear is broken else {@code false}
	 */
	public boolean isBroken() {
		return durability.getValue() <= 0 ? true : false;
	}

	public IntegerProperty getDurability() {
		return this.durability;
	}

	public abstract String getEmptySlotId();
	public abstract String getEmptySlotImage();
}
