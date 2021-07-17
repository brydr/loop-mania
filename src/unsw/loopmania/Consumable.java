package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Consumable extends BasicItem {
	public Consumable(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y);
	}
}
