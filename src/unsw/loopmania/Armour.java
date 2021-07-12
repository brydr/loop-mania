package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Armour extends ProtectiveGear {
	public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y, Character wearer) {
		super(x, y, wearer);
	}

	@Override
	public int calculateDamage(BasicEnemy attacker, int attackPower, boolean isCritical) {
		return attackPower / 2;
	}
}
