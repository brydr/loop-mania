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

	// TODO = This is a standin to get tests to compile. This should be removed as we better-define how Unarmed is implemented.
	public Unarmed() {
		super(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1), ATTACK_POWER, -1, -1);
	}

}
