package unsw.loopmania;

public interface WeaponStrategy {
	public int getDamage(BasicEnemy enemy);
	public boolean isBroken();
}
