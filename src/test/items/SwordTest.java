package test.items;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Sword;
import unsw.loopmania.Vampire;
import unsw.loopmania.weapons;

public class SwordTest {
	@Test
	public void loseHpTest() {
		SimpleIntegerProperty one = new SimpleIntegerProperty(1);
		Weapon sword = new Sword(one, one);

		BasicEnemy vampire = new Vampire();
		int vampireHealth = vampire.getHp();
		sword.getDamage(vampire);
		assertTrue(vampire.getHp() == vampireHealth - sword.getAttackPower());
	}
}
