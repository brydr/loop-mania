package test.items;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Slug;
import unsw.loopmania.weapons.Staff;
import unsw.loopmania.weapons.Weapon;

public class StaffTest {
	@Test
	public void loseHpTest() {
		// TODO handle trance case
		SimpleIntegerProperty one = new SimpleIntegerProperty(1);
		Weapon staff = new Staff(one, one);

		BasicEnemy slug = new Slug();
		int slugHealth = slug.getHp();
		staff.getDamage(slug);

		assertTrue(slug.getHp() == slugHealth - staff.getAttackPower());
	}
}
