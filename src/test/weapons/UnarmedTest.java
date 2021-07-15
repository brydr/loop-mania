package test.weapons;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.Unarmed;
import unsw.loopmania.weapons.Weapon;

public class UnarmedTest {
	private final static int BASE_DAMAGE = 2;

	@Test
	public void attackDamageTest() {
		SimpleIntegerProperty one = new SimpleIntegerProperty(1);
		Weapon unarmed = new Unarmed(one, one);

		PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
		Slug slug = new Slug(pos);

		assertTrue(unarmed.getDamage(slug) == UnarmedTest.BASE_DAMAGE);
	}
}
