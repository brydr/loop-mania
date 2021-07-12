package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Stake extends Weapon implements AttackAbility {
	private final int vampireDamage = 16;

	public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, 4);
	}

	@Override
	public void attack(BasicEnemy enemy) {
		if (enemy.getClass().equals(Vampire.class)) {
			// Do extra damage to vampires
			enemy.takeDamage(this.vampireDamage);
		} else {
			enemy.takeDamage(this.getAttackPower());
		}
	}

	@Override
	public void attackAbility(BasicEnemy target) {
		// TODO Auto-generated method stub

	}

}
