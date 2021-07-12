package unsw.loopmania;

public abstract class ProtectiveGear {
	private final Character wearer;

	public ProtectiveGear(Character wearer) {
		this.wearer = wearer;
	}

	public Character getWearer() {
		return this.wearer;
	}

	public int calculateDamage(BasicEnemy attacker, int attackPower) {
		return this.calculateDamage(attacker, attackPower, false);
	};

	public abstract int calculateDamage(BasicEnemy attacker, int attackPower, boolean isCritical);
}
