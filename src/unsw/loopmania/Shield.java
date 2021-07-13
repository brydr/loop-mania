package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Shield extends ProtectiveGear {
	private final double CRIT_NEGATION_CHANCE = .6;
	private final double ALL_NEGATION_CHANCE = .3;

	public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y, Character wearer) {
		super(x, y, wearer);
	}

	@Override
	public int calculateDamage(BasicEnemy attacker, int attackPower, boolean isCritical) {
		int damage = attackPower;
		if (isVampireCrit(attacker, isCritical) && negateCrit()) {
			// FIXME literals are bad
			damage = 12;
		}

		return damage;
	}

	private boolean negateAllDamage() {
		return Math.random() < ALL_NEGATION_CHANCE;
	}

	// private boolean negateCrit() {
	// 	return Math.random() < CRIT_NEGATION_CHANCE;
	// }

	private boolean isVampireCrit(BasicEnemy attacker, boolean isCritical) {
		return attacker.getClass().equals(Vampire.class) && isCritical;
	}
}
