package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Staff extends Weapon implements MagicAbility {
	private double TRANCE_CHANCE = .2;
	private final static int ATTACK_POWER = 3;
	private final static int BUY_PRICE = 200;
	private final static int SELL_PRICE = 100;

	public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, Staff.ATTACK_POWER, Staff.BUY_PRICE, Staff.SELL_PRICE);
	}

	// Used for testing runBattles.
	public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y, double trance_chance) {
		this(x, y);
		this.TRANCE_CHANCE = trance_chance;
	}

	@Override
	public int getDamage(BasicEnemy enemy) {
		if (this.shouldTrance()) {
			enemy.setInTrance(true);
			return 0;
		}

		return Staff.ATTACK_POWER;
	}

	private boolean shouldTrance() {
		return Math.random() < TRANCE_CHANCE;
	}

	@Override
	public String getImage() {
        String staffImage = "src/images/staff.png";
        return staffImage;
    }

}
