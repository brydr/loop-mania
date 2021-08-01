package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class BasicItem extends Item {
	private final int buyPrice;
	private final int sellPrice;

	public BasicItem(SimpleIntegerProperty x, SimpleIntegerProperty y, int buyPrice, int sellPrice) {
		super(x, y);
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
	}

	public int getBuyPrice() {
		return buyPrice;
	}

	public int getSellPrice() {
		return sellPrice;
	}

}
