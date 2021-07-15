package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Shield extends ProtectiveGear {
	// Critical bite negation is handled in the vampire class
	private double ALL_NEGATION_CHANCE = .3;

	public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y);
	}

	public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y, double negation_chance) {
		this(x, y);
		ALL_NEGATION_CHANCE = negation_chance;
	}

	@Override
	public int calculateDamage(int attackPower) {
		return this.negateAllDamage() ? 0 : attackPower;
	}

	private boolean negateAllDamage() {
		return Math.random() < ALL_NEGATION_CHANCE;
	}
}
