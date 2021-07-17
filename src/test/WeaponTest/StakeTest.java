package test.WeaponTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.Vampire;
import unsw.loopmania.Stake;
import unsw.loopmania.Weapon;

public class StakeTest {
	private final static int BASE_DAMAGE = 4;
	private final static int VAMPIRE_DAMAGE = 16;

	@Test
	public void attackDamageTest() {
		SimpleIntegerProperty one = new SimpleIntegerProperty(1);
		Weapon stake = new Stake(one, one);

		PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
		Slug slug = new Slug(pos);

		assertTrue(stake.getDamage(slug) == StakeTest.BASE_DAMAGE);
	}

	@Test
	public void attackDamageVampireTest() {
		SimpleIntegerProperty one = new SimpleIntegerProperty(1);
		Weapon stake = new Stake(one, one);

		PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));

		BasicEnemy vampire = new Vampire(pos);
		assertTrue(stake.getDamage(vampire) == StakeTest.VAMPIRE_DAMAGE);
	}
}
