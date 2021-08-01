package unsw.loopmania;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract  class EquippableItem extends BasicItem {
	private IntegerProperty durabilityPercent;
	private double durability;
	private final static double MAX_DURABILITY = 50;
	private final static double DURABILITY_BAR_WIDTH = 30;
	public EquippableItem(SimpleIntegerProperty x, SimpleIntegerProperty y, int buyPrice, int sellPrice) {
		super(x, y, buyPrice, sellPrice);
		this.durabilityPercent = new SimpleIntegerProperty();
		this.durabilityPercent.setValue(DURABILITY_BAR_WIDTH);
		this.durability = MAX_DURABILITY;
	}

	/**
	 * Checks if gear is broken
	 * @return {@code true} if gear is broken else {@code false}
	 */
	public boolean isBroken() {
		return durability <= 0 ? true : false;
	}

	public IntegerProperty getDurabilityBar() {
		return this.durabilityPercent;
	}

	public void decDurability() {
		durability -= 1;
		this.durabilityPercent.setValue(durability/MAX_DURABILITY*DURABILITY_BAR_WIDTH);
	}

	public abstract String getEmptySlotId();
	public abstract String getEmptySlotImage();
}
