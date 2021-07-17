package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
public class Unarmed implements WeaponStrategy {
	private final static int ATTACK_POWER = 2;

	public Unarmed(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		
	}
	@Override
	public int getDamage(BasicEnemy enemy) {
		// TODO Auto-generated method stub
		return ATTACK_POWER;
	}
}
