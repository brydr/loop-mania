package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Weapon extends EquippableItem implements WeaponStrategy {

	private final int attackPower;

	public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y, int attackPower, int buyPrice, int sellPrice) {
		super(x, y, buyPrice, sellPrice);
		this.attackPower = attackPower;
	}

	public int getDamage(Enemy enemy) {
		durability.setValue(durability.getValue() - 1);
		return attackPower;
	}

	public int getAttackPower() {
		return attackPower;
	}

	@Override
	public String getEmptySlotId() {
		return "swordCell";
	}

	@Override
	public String getEmptySlotImage() {
		return "src/images/sword_unequipped.png";
	}
}