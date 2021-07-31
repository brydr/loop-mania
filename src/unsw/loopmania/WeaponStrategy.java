package unsw.loopmania;

public interface WeaponStrategy {
	public boolean isBroken();
	public int getDamage(Enemy enemy);
}
