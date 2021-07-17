package test.WeaponTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Vampire;
import unsw.loopmania.Sword;
import unsw.loopmania.Weapon;

public class SwordTest {
	private final static int BASE_DAMAGE = 8;
	@Test
	public void attackDamageTest() {
		SimpleIntegerProperty one = new SimpleIntegerProperty(1);
		Weapon sword = new Sword(one, one);
		PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));

		BasicEnemy vampire = new Vampire(pos);
		assertTrue(sword.getDamage(vampire) == SwordTest.BASE_DAMAGE);
	}
}
