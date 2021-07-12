package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Weapon implements AttackAbility {
	// TODO = add more weapon/item types
	public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, 8);
	}

	/**
	 * @param enemy The enemy being attacked
	 */
	@Override
	public void attack(BasicEnemy enemy) {
		enemy.takeDamage(this.getAttackPower());
	}

	@Override
	public void attackAbility(BasicEnemy target) {
		// Sword has no attack ability
	}
}
