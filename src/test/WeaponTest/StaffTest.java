package test.WeaponTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.javatuples.Pair;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;
import unsw.loopmania.Staff;
import unsw.loopmania.Weapon;

public class StaffTest {
	@RepeatedTest(1000)
	@Test
	public void attackDamageTest() {
		SimpleIntegerProperty one = new SimpleIntegerProperty(1);
		Weapon staff = new Staff(one, one);

		PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
		Slug slug = new Slug(pos);

		int damage = staff.getDamage(slug);
		if (!(damage == 3 || damage == 0)) {
			System.out.println(damage);
		}

		assertTrue(damage == 3 || damage == 0);
	}
}
