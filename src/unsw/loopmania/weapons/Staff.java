package unsw.loopmania.weapons;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;

public class Staff extends Weapon implements MagicAbility {
	private final static double TRANCE_CHANCE = .2;
	private final static int ATTACK_POWER = 3;

	public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, Staff.ATTACK_POWER);
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

}
