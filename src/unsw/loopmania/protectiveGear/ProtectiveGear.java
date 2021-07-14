package unsw.loopmania.protectiveGear;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.Character;

public abstract class ProtectiveGear extends EquippableItem {
	private final Character wearer;

	public ProtectiveGear(SimpleIntegerProperty x, SimpleIntegerProperty y, Character wearer) {
		super(x, y);
		this.wearer = wearer;
	}

	public Character getWearer() {
		return this.wearer;
	}

	public abstract int calculateDamage(BasicEnemy attacker, int attackPower);
}
