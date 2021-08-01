package unsw.loopmania;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;
public class TheOneRing extends Item implements RareItem {

    private int addEffectVal = new Random().nextInt(2);
    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y);
	}
    
    @Override
    public void effect(Character character, Enemy enemy) {
        if (character.getHp() <= 0) {
            RareItem oneRing = character.getEquippedRareItem();
            // Remove the rare item from the characters equipped rare item list and set its equipped ring to null.
            character.removeRareItem(oneRing);
            character.setEquippedRareItem(null);
            character.setOneRingUsed(true); 
            character.setHp(character.getMaxHp());
        }
    }

    @Override
    public void addEffect(Character character, Enemy enemy) {
        if (enemy instanceof BossEnemy) {
            if (addEffectVal == 0) {
                character.setNumAttack(2);  // AndurilFlame weapon effect
            } else {
                int enemyAttack = enemy.getAttack();
                enemy.setAttack(enemyAttack/3);         //Tree stump effect
            }
        }
    }

    @Override
	public String getImage() {
        return "src/images/the_one_ring.png";
    }
}
