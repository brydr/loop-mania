package unsw.loopmania;

import unsw.loopmania.weapons.WeaponStrategy;

public class Unarmed implements WeaponStrategy {
	private final static int ATTACK_POWER = 2;

	@Override
	public int getDamage(BasicEnemy enemy) {
		// TODO Auto-generated method stub
		return ATTACK_POWER;
	}
}
