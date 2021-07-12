package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class ProtectiveGear extends EquippableItem {
	private final Character wearer;

	public ProtectiveGear(SimpleIntegerProperty x, SimpleIntegerProperty y, Character wearer) {
		super(x, y);
		this.wearer = wearer;
	}

	public Character getWearer() {
		return this.wearer;
	}

	public int calculateDamage(BasicEnemy attacker, int attackPower) {
		return this.calculateDamage(attacker, attackPower, false);
	};

	public abstract int calculateDamage(BasicEnemy attacker, int attackPower, boolean isCritical);
}
