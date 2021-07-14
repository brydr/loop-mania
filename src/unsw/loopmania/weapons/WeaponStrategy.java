package unsw.loopmania.weapons;

import unsw.loopmania.BasicEnemy;

public interface WeaponStrategy {
	public int getDamage(BasicEnemy enemy);
}
