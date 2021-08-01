package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Stake extends Weapon implements AttackAbility {
	private final static int ATTACK_POWER = 4;
	private final static int VAMPIRE_DAMAGE = 16;
	private final static int BUY_PRICE = 100;
	private final static int SELL_PRICE = 50;

	public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, Stake.ATTACK_POWER, Stake.BUY_PRICE, Stake.SELL_PRICE);
	}

	@Override
	public int getDamage(Enemy enemy) {
		decDurability();
		if (enemy.getClass().equals(Vampire.class)) {
			// Do extra damage to vampires
			return Stake.VAMPIRE_DAMAGE;
		}
		return Stake.ATTACK_POWER;
	}
	
	@Override
	public String getImage() {
		return "src/images/stake.png";
	}

}
