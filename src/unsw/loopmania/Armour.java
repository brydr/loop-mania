package unsw.loopmania;

public class Armour extends ProtectiveGear {
	public Armour(Character wearer) {
		super(wearer);
	}

	@Override
	public int calculateDamage(BasicEnemy attacker, int attackPower, boolean isCritical) {
		return attackPower / 2;
	}
}
