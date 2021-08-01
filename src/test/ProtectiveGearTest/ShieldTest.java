package test.ProtectiveGearTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import unsw.loopmania.Shield;

public class ShieldTest {
	private final PathPosition pos = new PathPosition(0,
			Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
	private final Character character = new Character(pos);
	BasicEnemy slug = new Slug(pos);
	private final SimpleIntegerProperty one = new SimpleIntegerProperty(1);
	private final Shield shield = new Shield(one, one, 1); // Create a shield that has 100% negation chance.

	@Test
	public void damageReductionTest() {
		int calculatedDamage = shield.calculateDamage(slug.getAttack());
		assertTrue(calculatedDamage == 0);
	}

	@Test
	public void negateCriticalBiteTest() {
		Vampire vampire = new Vampire(pos);
		character.setEquippedShield(shield);

		vampire.attack(character);
		assertEquals(.08, vampire.criticalChance);
	}

}
