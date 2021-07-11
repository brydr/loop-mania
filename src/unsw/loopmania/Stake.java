package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Stake extends Weapon implements AttackAbility {
	public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, 4);
	}

	@Override
	public void attack(BasicEnemy enemy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void attackAbility(BasicEnemy target) {
		// TODO Auto-generated method stub

	}

}
