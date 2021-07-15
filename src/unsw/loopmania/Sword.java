package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Weapon implements AttackAbility {
	private final static int ATTACK_POWER = 8;

	public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, Sword.ATTACK_POWER);
	}
}
