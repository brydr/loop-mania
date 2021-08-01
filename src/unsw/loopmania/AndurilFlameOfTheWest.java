package unsw.loopmania;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class AndurilFlameOfTheWest extends Weapon implements RareItem {

    private final static int ATTACK_POWER = 10;
	private final static int BUY_PRICE = 0;
	private final static int SELL_PRICE = 0;

    private int addEffectVal = new Random().nextInt(2);

    public AndurilFlameOfTheWest(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, AndurilFlameOfTheWest.ATTACK_POWER, AndurilFlameOfTheWest.BUY_PRICE, AndurilFlameOfTheWest.SELL_PRICE);
    }

    @Override
    public void effect(Character character, Enemy enemy) {
        if (enemy instanceof BossEnemy) {
            // Set it so the character attacks 2 more times for a total of 3 attacks, tripling the damage.
            character.setNumAttack(2);
        }
    }

    @Override
    public void addEffect(Character character, Enemy enemy) {
        if (addEffectVal == 0 && enemy instanceof BossEnemy) {
            int enemyAttack = enemy.getAttack();            // Tree stump effect
            enemy.setAttack(enemyAttack/3);
        } else {
            if (character.getHp() <= 0) {
                character.setEquippedRareItem(null);        // One Ring effect
                character.setOneRingUsed(true); 
                character.setHp(character.getMaxHp());
            }
        }
    }

    @Override
    public String getImage() {
        String andurilImage = "src/images/anduril_flame_of_the_west.png";
        return andurilImage;
    }
    
}
