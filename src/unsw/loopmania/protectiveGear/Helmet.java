package unsw.loopmania.protectiveGear;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Character;

public class Helmet extends ProtectiveGear {
	// TODO reduce wearer's attack power
	private final static double DAMAGE_MULTIPLIER = .7;

	public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y, Character wearer) {
		super(x, y, wearer);
	}

	@Override
	public int calculateDamage(BasicEnemy attacker, int attackPower) {
		return (int) Math.round(Helmet.DAMAGE_MULTIPLIER * attackPower);
	}

}
