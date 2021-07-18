package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Weapon extends EquippableItem implements WeaponStrategy {

	private final int attackPower;

	public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y, int attackPower, int buyPrice, int sellPrice) {
		super(x, y, buyPrice, sellPrice);
		this.attackPower = attackPower;
	}

	public int getDamage(BasicEnemy enemy) {
		return attackPower;
	}

	public int getAttackPower() {
		return attackPower;
	}
}