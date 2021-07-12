package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Weapon extends EquippableItem {
	private final int attackPower;

	public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y, int attackPower) {
		super(x, y);
		this.attackPower = attackPower;
	}

	public abstract void attack(BasicEnemy enemy);

	public int getAttackPower() {
		return attackPower;
	}
}
