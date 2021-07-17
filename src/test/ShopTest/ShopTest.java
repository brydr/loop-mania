package test.ShopTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;

import unsw.loopmania.Armour;
import unsw.loopmania.BasicItem;
import unsw.loopmania.BerserkerMode;
import unsw.loopmania.Character;
import unsw.loopmania.HealthPotion;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.RandomItemGenerator;
import unsw.loopmania.Shield;
import unsw.loopmania.Shop;
import unsw.loopmania.Stake;
import unsw.loopmania.StandardMode;
import unsw.loopmania.SurvivalMode;
import unsw.loopmania.Sword;

public class ShopTest {
	private final PathPosition pos = new PathPosition(0,
			Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
	private final Character character = new Character(pos);
	private final LoopManiaWorld world = new LoopManiaWorld(20, 20,
			Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
	private final Random random = new Random();
	private final static long SEED = 22223;

	/**
	 * Shop inventory for seed 22223:
	 * 0 Armour
	 * 1 Helmet
	 * 2 HealthPotion
	 * 3 HealthPotion
	 * 4 Shield
	 * 5 Stake
	 * 6 Armour
	 * 7 Sword
	 * 8 Shield
	 */

	// Main function for printing out shop item generation for a seed
	public static void main(String args[]) {
		ShopTest test = new ShopTest();
		test.printInventory(22223);
	}

	// Helper function for setting up tests
	private void printInventory(long seed) {
		Shop shop = new Shop(world, new StandardMode(), seed);

		for (int i = 0; i < Shop.MAX_SHOP_INVENTORY; i++) {
			System.out.println(shop.getInventory().get(i).getClass());
		}
	}

	@Test
	public void testNoMoney() {
		world.setCharacter(character);
		assertTrue(world.getInventory().isEmpty());
		Shop shop = new Shop(world, new StandardMode(), SEED);

		BasicItem shield = shop.getInventory().get(8);
		assertTrue(shield instanceof Shield);
		assertTrue(character.getGold() == 0);

		// Tries to buy with 0 gold
		assertFalse(shop.buy(shield));

		character.addGold(shield.getBuyPrice() - 1);

		// Tries to buy with 1 less than the shield's price
		assertFalse(shop.buy(shield));

		// Assert the shop didn't take their money
		assertTrue(character.getGold() == shield.getBuyPrice() - 1);
	}

	@Test
	public void testStandard() {
		world.setCharacter(character);
		assertTrue(world.getInventory().isEmpty());
		Shop shop = new Shop(world, new StandardMode(), SEED);

		BasicItem shield = shop.getInventory().get(8);
		assertTrue(shield instanceof Shield);
		assertTrue(character.getGold() == 0);
		BasicItem stake = shop.getInventory().get(5);
		assertTrue(stake instanceof Stake);

		character.addGold(1000);

		// Tries to buy shield with 1000 gold
		assertTrue(shop.buy(shield));

		// Asserts the character has spent the money and it's in their inventory
		assertTrue(character.getGold() == 1000 - shield.getBuyPrice());
		assertTrue(world.getInventory().contains(shield));

		// Tries to buy stake with 990 gold
		assertTrue(shop.buy(stake));

		// Asserts the character has spent the money and it's in their inventory
		assertTrue(character.getGold() == 1000 - shield.getBuyPrice() - stake.getBuyPrice());

		// Asserts the shop no longer has those items
		assertTrue(!shop.getInventory().contains(shield) && !shop.getInventory().contains(stake));

		// Asserts trying to buy the same thing again will fail
		assertFalse(shop.buy(shield));
		assertFalse(shop.buy(stake));
	}

	@Test
	public void testBerserker() {
		world.setCharacter(character);
		assertTrue(character.getGold() == 0);
		character.addGold(1000);
		assertTrue(character.getGold() == 1000);

		assertTrue(world.getInventory().isEmpty());
		Shop shop = new Shop(world, new BerserkerMode(), SEED);

		BasicItem shield = shop.getInventory().get(4);
		BasicItem stake = shop.getInventory().get(5);
		BasicItem armour = shop.getInventory().get(0);
		BasicItem sword = shop.getInventory().get(7);

		assertTrue(shield instanceof Shield);
		assertTrue(stake instanceof Stake);
		assertTrue(armour instanceof Armour);
		assertTrue(sword instanceof Sword);

		// Should be able to buy 2 weapons no problem
		assertTrue(shop.buy(stake));
		assertTrue(shop.buy(sword));
		assertTrue(world.getInventory().contains(stake));
		assertTrue(world.getInventory().contains(sword));

		// Should be able to buy 1 protective gear
		assertTrue(shop.buy(armour));
		assertTrue(world.getInventory().contains(armour));

		// Tries to buy another piece of protective gear
		assertFalse(shop.buy(shield));
		assertFalse(world.getInventory().contains(shield));
		assertTrue(shop.getInventory().contains(shield));

		// Asserts they were charged the right amount
		assertTrue(character.getGold() == 1000 - stake.getBuyPrice() - sword.getBuyPrice() - armour.getBuyPrice());
	}

	@Test
	public void testSurvival() {
		world.setCharacter(character);
		assertTrue(character.getGold() == 0);
		character.addGold(1000);
		assertTrue(character.getGold() == 1000);

		assertTrue(world.getInventory().isEmpty());
		Shop shop = new Shop(world, new SurvivalMode(), SEED);

		BasicItem shield = shop.getInventory().get(8);
		BasicItem stake = shop.getInventory().get(5);
		BasicItem armour = shop.getInventory().get(6);
		BasicItem sword = shop.getInventory().get(7);
		BasicItem healthPotion = shop.getInventory().get(2);
		BasicItem healthPotion2 = shop.getInventory().get(3);

		assertTrue(shield instanceof Shield);
		assertTrue(stake instanceof Stake);
		assertTrue(armour instanceof Armour);
		assertTrue(sword instanceof Sword);
		assertTrue(healthPotion instanceof HealthPotion);
		assertTrue(healthPotion2 instanceof HealthPotion);

		// Should be able to buy 2 weapons no problem
		assertTrue(shop.buy(stake));
		assertTrue(shop.buy(sword));
		assertTrue(world.getInventory().contains(stake));
		assertTrue(world.getInventory().contains(sword));

		// Should be able to buy 2 protective gears no problem
		assertTrue(shop.buy(armour));
		assertTrue(shop.buy(shield));
		assertTrue(world.getInventory().contains(armour));

		// Should be able to buy 1 health potion
		assertTrue(shop.buy(healthPotion));
		assertFalse(shop.getInventory().contains(healthPotion));
		assertTrue(world.getInventory().contains(healthPotion));

		// Shouldn't be able to buy another health potion
		assertFalse(shop.buy(healthPotion2));
		assertTrue(shop.getInventory().contains(healthPotion2));
		assertFalse(world.getInventory().contains(healthPotion2));

		// Asserts they were charged the right amount
		assertTrue(character.getGold() == 1000 - stake.getBuyPrice() - sword.getBuyPrice() - armour.getBuyPrice()
				- shield.getBuyPrice() - healthPotion.getBuyPrice());
	}

	@Test
	@RepeatedTest(1000)
	public void testShopItemGeneration() {
		int seed = random.nextInt();
		RandomItemGenerator itemGenerator = new RandomItemGenerator(seed);

		Shop shop = new Shop(world, new StandardMode(), seed);
		List<BasicItem> inventory = shop.getInventory();

		for (int i = 0; i < Shop.MAX_SHOP_INVENTORY; i++) {
			assertTrue(itemGenerator.nextItem(0, 0).getClass().equals(inventory.get(i).getClass()));
		}
	}
}
