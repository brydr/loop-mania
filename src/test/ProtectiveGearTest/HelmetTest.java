package test.ProtectiveGearTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Helmet;
import unsw.loopmania.PathPosition;
import unsw.loopmania.ProtectiveGear;
import unsw.loopmania.Slug;

public class HelmetTest {
	private final PathPosition pos = new PathPosition(0,
			Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
	BasicEnemy slug = new Slug(pos);

	@Test
	public void damageReductionTest() {
		SimpleIntegerProperty one = new SimpleIntegerProperty(1);
		ProtectiveGear helmet = new Helmet(one, one);

		assertTrue(helmet.calculateDamage(slug.getAttack()) == Math.round(slug.getAttack() * .7));

	}
}
