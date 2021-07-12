package unsw.loopmania;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class Staff extends Weapon implements MagicAbility {
	private final double tranceChance = .2;

	public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, 3);
	}

	@Override
	public void attack(BasicEnemy enemy) {
		if (this.shouldTrance()) {
			enemy.convertToFriendly(this.generateTranceLength());
		} else {
			enemy.takeDamage(this.getAttackPower());
		}

	}

	private boolean shouldTrance() {
		// TODO global seed?
		return Math.random() < tranceChance;
	}

	private int generateTranceLength() {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(18) + 3;
	}

	@Override
	public void magicAbility(BasicEnemy enemy) {
		// TODO implement

	}
}
