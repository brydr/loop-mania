package unsw.loopmania;

public class Unarmed implements WeaponStrategy {
	private final static int ATTACK_POWER = 2;

	@Override
	public int getDamage(BasicEnemy enemy) {
		return Unarmed.ATTACK_POWER;
	}
}