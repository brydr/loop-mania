package unsw.loopmania;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class TreeStump extends ProtectiveGear implements RareItem{

    private final double allNegationChance;
	private final static double DEFAULT_ALL_NEG_CHANCE = 0.55d;
	private final static int BUY_PRICE = 0;
	private final static int SELL_PRICE = 0;

    private int addEffectVal = new Random().nextInt(2);

    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, BUY_PRICE, SELL_PRICE);
        allNegationChance = DEFAULT_ALL_NEG_CHANCE;
    }

    @Override
    public int calculateDamage(int attackPower) {
        return this.negateAllDamage() ? 0 : attackPower;
    }

    /**
	 * Randomly determines if all damage to character is negated.
	 * @return {@code true} with chance equal to {@code this.allNegationChance}
	 */
	private boolean negateAllDamage() {
		return Math.random() < allNegationChance;
	}

    @Override
    public void effect(Character character, Enemy enemy) {
        if (enemy instanceof BossEnemy) {
            int enemyAttack = enemy.getAttack();
            enemy.setAttack(enemyAttack/3);
        }
    }

    @Override
    public void addEffect(Character character, Enemy enemy) {
        if (addEffectVal == 0 && enemy instanceof BossEnemy) {
            character.setNumAttack(2);  // AndurilFlame weapon effect
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
        String treeStumpImage = "src/images/tree_stump.png";
        return treeStumpImage;
    }
    
}
