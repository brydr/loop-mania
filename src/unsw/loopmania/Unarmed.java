package unsw.loopmania;

public class Unarmed implements WeaponStrategy {
	private final static int ATTACK_POWER = 2;

	@Override
	public int getDamage(Enemy enemy) {
		return Unarmed.ATTACK_POWER;
	}

	@Override
	public boolean isBroken() {
		return false;
	}
}
