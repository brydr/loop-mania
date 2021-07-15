package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Stake extends Weapon implements AttackAbility {
	private final static int ATTACK_POWER = 4;
	private final static int VAMPIRE_DAMAGE = 16;

	public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, ATTACK_POWER);
	}

	@Override
	public int getDamage(BasicEnemy enemy) {
		if (enemy.getClass().equals(Vampire.class)) {
			// Do extra damage to vampires
			return VAMPIRE_DAMAGE;
		}
		return ATTACK_POWER;
	}

}
