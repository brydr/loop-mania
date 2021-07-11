package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Staff extends Weapon implements MagicAbility {

	public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, 3);
	}

	@Override
	public void attack(BasicEnemy enemy) {
		// TODO implement

	}

	@Override
	public void magicAbility(BasicEnemy enemy) {
		// TODO implement

	}
}
