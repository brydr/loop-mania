package unsw.loopmania.protectiveGear;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;

public class Armour extends ProtectiveGear {
	public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y);
	}

	@Override
	public int calculateDamage(BasicEnemy attacker, int attackPower) {
		return attackPower / 2;
	}
}
