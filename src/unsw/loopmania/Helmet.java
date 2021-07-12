package unsw.loopmania;

public class Helmet extends ProtectiveGear {
	// TODO reduce wearer's attack power
	public Helmet(Character wearer) {
		super(wearer);
	}

	@Override
	public int calculateDamage(BasicEnemy attacker, int attackPower, boolean isCritical) {
		return (int) Math.round(.7 * attackPower);
	}

}
