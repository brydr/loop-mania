package unsw.loopmania.protectiveGear;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Character;

public class Shield extends ProtectiveGear {
	// Critical bite negation is handled in the vampire class
	private final double ALL_NEGATION_CHANCE = .3;

	public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y, Character wearer) {
		super(x, y, wearer);
	}

	@Override
	public int calculateDamage(BasicEnemy attacker, int attackPower) {
		return this.negateAllDamage() ? 0 : attackPower;
	}

	private boolean negateAllDamage() {
		return Math.random() < ALL_NEGATION_CHANCE;
	}
}
