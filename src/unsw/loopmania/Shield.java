package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Shield extends ProtectiveGear {
	// Note: Critical bite negation is handled in the Vampire class
	private final double allNegationChance;
	private final static double DEFAULT_ALL_NEG_CHANCE = 0.3d;
	private final static int BUY_PRICE = 200;
	private final static int SELL_PRICE = 100;

	public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, BUY_PRICE, SELL_PRICE);
		allNegationChance = DEFAULT_ALL_NEG_CHANCE;
	}

	
	public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y, double negationChance) {
		super(x, y, BUY_PRICE, SELL_PRICE);
		allNegationChance = negationChance;
	}

	@Override
	public int calculateDamage(int attackPower) {
		return this.negateAllDamage() ? 0 : attackPower;
	}

	/**
	 * Randomly determines if all damage to character is negated.
	 * @return {@code true} with chance equal to {@code this.allNegationChance}
	 */
	private boolean negateAllDamage() {
		return Math.random() < allNegationChance;
	}

	@Override
	public String getImage() {
        return "src/images/shield.png";
    }
}
