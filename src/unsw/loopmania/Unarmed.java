package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Unarmed extends Weapon {
	private final static int ATTACK_POWER = 2;

	@Override
	public int getDamage(BasicEnemy enemy) {
		return Unarmed.ATTACK_POWER;
	}

	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public Unarmed(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, ATTACK_POWER, -1, -1);
	}

}
