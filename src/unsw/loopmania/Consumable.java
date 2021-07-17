package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Consumable extends BasicItem {
	public Consumable(SimpleIntegerProperty x, SimpleIntegerProperty y, int buyPrice, int sellPrice) {
		super(x, y, buyPrice, sellPrice);
	}
}
