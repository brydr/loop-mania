package test.protectiveGear;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import unsw.loopmania.Character;
import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.protectiveGear.Armour;
import unsw.loopmania.protectiveGear.ProtectiveGear;

public class ArmourTest {
	private final PathPosition pos = new PathPosition(0,
			Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
	private final Character character = new Character(pos);
	BasicEnemy slug = new Slug(pos);

	@Test
	public void damageReductionTest() {
		SimpleIntegerProperty one = new SimpleIntegerProperty(1);
		ProtectiveGear armour = new Armour(one, one, character);

		assertTrue(armour.calculateDamage(slug, slug.getAttack()) == slug.getAttack() / 2);

	}
}
