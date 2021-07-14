package test.items;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Slug;
import unsw.loopmania.Stake;
import unsw.loopmania.Vampire;
import unsw.loopmania.weapons;

public class StakeTest {
	@Test
	public void loseHpTest() {
		SimpleIntegerProperty one = new SimpleIntegerProperty(1);
		Weapon stake = new Stake(one, one);

		BasicEnemy slug = new Slug();
		int slugHealth = slug.getHp();
		stake.getDamage(slug);
		assertTrue(slug.getHp() == slugHealth - stake.getAttackPower());
	}

	@Test
	public void vampireTest() {
		SimpleIntegerProperty one = new SimpleIntegerProperty(1);
		Weapon stake = new Stake(one, one);

		BasicEnemy vampire = new Vampire();
		int vampireHealth = vampire.getHp();
		stake.getDamage(vampire);
		assertTrue(vampire.getHp() == vampireHealth - 16);
	}
}
