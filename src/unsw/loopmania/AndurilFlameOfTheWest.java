package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class AndurilFlameOfTheWest extends Weapon implements AttackAbility {

    private final static int ATTACK_POWER = 10;
    private final static int BOSS_DAMAGE = 30;
	private final static int BUY_PRICE = 0;
	private final static int SELL_PRICE = 0;

    public AndurilFlameOfTheWest(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, AndurilFlameOfTheWest.ATTACK_POWER, AndurilFlameOfTheWest.BUY_PRICE, AndurilFlameOfTheWest.SELL_PRICE);
    }

    @Override
	public int getDamage(Enemy enemy) {
		if (enemy.getClass().equals(BossEnemy.class)) {
			// Do extra damage to bosses
			return AndurilFlameOfTheWest.BOSS_DAMAGE;
		}
		return AndurilFlameOfTheWest.ATTACK_POWER;
	}

    @Override
    public String getImage() {
        String andurilImage = "src/images/anduril_flame_of_the_west.png";
        return andurilImage;
    }
    
}
