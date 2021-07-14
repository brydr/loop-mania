package unsw.loopmania.weapons;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Vampire;

public class Stake extends Weapon implements AttackAbility {
	private final int VAMPIRE_DAMAGE = 16;
	private final static int ATTACK_POWER = 4;

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
