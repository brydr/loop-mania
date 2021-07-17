package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class EquippableItem extends BasicItem {
	public EquippableItem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y);
	}
}
