package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Helmet extends ProtectiveGear {
	// TODO reduce wearer's attack power
	private final static double DAMAGE_MULTIPLIER = .7;

	public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y);
	}

	@Override
	public int calculateDamage(int attackPower) {
		return (int) Math.round(Helmet.DAMAGE_MULTIPLIER * attackPower);
	}

}
