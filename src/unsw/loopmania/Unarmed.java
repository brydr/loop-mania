package unsw.loopmania;

<<<<<<< HEAD
public class Unarmed implements WeaponStrategy {
	private final static int ATTACK_POWER = 2;

=======
import javafx.beans.property.SimpleIntegerProperty;
public class Unarmed implements WeaponStrategy {
	private final static int ATTACK_POWER = 2;

	public Unarmed(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		
	}
>>>>>>> enemyFrontend
	@Override
	public int getDamage(BasicEnemy enemy) {
		return Unarmed.ATTACK_POWER;
	}
}