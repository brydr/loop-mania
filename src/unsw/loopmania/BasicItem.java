package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class BasicItem extends Item {
	public BasicItem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y);
	}
}
