package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.EquippableItem;

public abstract class Weapon extends EquippableItem implements WeaponStrategy {
	private final int attackPower;

	public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y, int attackPower) {
		super(x, y);
		this.attackPower = attackPower;
	}

	public int getDamage(BasicEnemy enemy) {
		return attackPower;
	}

	public int getAttackPower() {
		return attackPower;
	}
}
