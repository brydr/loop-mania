package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Shield extends ProtectiveGear {
	// Critical bite negation is handled in the vampire class
	private final double allNegationChance = .3d;
	private final static int BUY_PRICE = 200;
	private final static int SELL_PRICE = 100;

	public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, BUY_PRICE, SELL_PRICE);
	}

	public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y, double negation_chance) {
		this(x, y);
		allNegationChance = negation_chance;
	}

	@Override
	public int calculateDamage(int attackPower) {
		return this.negateAllDamage() ? 0 : attackPower;
	}

	private boolean negateAllDamage() {
		return Math.random() < allNegationChance;
	}

	@Override
	public String getImage() {
        String shieldImage = "src/images/shield.png";
        return shieldImage;
    }
}
