package unsw.loopmania.protectiveGear;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Character;

public class Armour extends ProtectiveGear {
	public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y, Character wearer) {
		super(x, y, wearer);
	}

	@Override
	public int calculateDamage(BasicEnemy attacker, int attackPower) {
		return attackPower / 2;
	}
}
