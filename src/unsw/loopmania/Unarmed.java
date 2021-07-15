package unsw.loopmania;

import unsw.loopmania.WeaponStrategy;
import javafx.beans.property.SimpleIntegerProperty;
public class Unarmed extends Weapon {
	private final static int ATTACK_POWER = 2;

	public Unarmed(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, ATTACK_POWER);
	}
	@Override
	public int getDamage(BasicEnemy enemy) {
		// TODO Auto-generated method stub
		return ATTACK_POWER;
	}
}
