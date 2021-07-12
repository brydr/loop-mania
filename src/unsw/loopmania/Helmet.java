package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Helmet extends ProtectiveGear {
	// TODO reduce wearer's attack power
	public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y, Character wearer) {
		super(x, y, wearer);
	}

	@Override
	public int calculateDamage(BasicEnemy attacker, int attackPower, boolean isCritical) {
		return (int) Math.round(.7 * attackPower);
	}

}
