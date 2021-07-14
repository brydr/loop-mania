package unsw.loopmania.weapons;

import javafx.beans.property.SimpleIntegerProperty;

public class Unarmed extends Weapon {
	private final static int attackPower = 2;

	public Unarmed(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, Unarmed.attackPower);
	}
}
