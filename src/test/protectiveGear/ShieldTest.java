package test.protectiveGear;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.Vampire;
import unsw.loopmania.protectiveGear.ProtectiveGear;
import unsw.loopmania.protectiveGear.Shield;

public class ShieldTest {
	private final PathPosition pos = new PathPosition(0,
			Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
	private final Character character = new Character(pos);
	BasicEnemy slug = new Slug(pos);
	private final SimpleIntegerProperty one = new SimpleIntegerProperty(1);
	private final ProtectiveGear armour = new Shield(one, one);

	@Test
	public void damageReductionTest() {
		int calculatedDamage = armour.calculateDamage(slug, slug.getAttack());
		assertTrue(calculatedDamage == 0 || calculatedDamage == slug.getAttack());

	}

	@Test
	public void negateCriticalBiteTest() {
		BasicEnemy vampire = new Vampire(pos);

		// TODO test negation of critical bite
		assertTrue(false);
	}

}
