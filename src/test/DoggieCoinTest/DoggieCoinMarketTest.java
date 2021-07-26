package test.DoggieCoinTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;

import unsw.loopmania.DoggieCoinMarket;

public class DoggieCoinMarketTest {
	private Random randomGenerator;

	@Test
	@RepeatedTest(1000)
	public void testPrices() {
		randomGenerator = new Random();
		long seed = this.randomGenerator.nextLong();
		Random randomGenerator = new Random(seed);
		DoggieCoinMarket market = new DoggieCoinMarket(seed);
		int expectedPrice = 10;
		assertEquals(expectedPrice, market.getPrice());
		market.tickPrice();

		for (int i = 0; i < 10; ++i, market.tickPrice()) {
			int difference = randomGenerator.nextInt(10);
			boolean increasePrice = randomGenerator.nextDouble() < .5;

			if (increasePrice) {
				expectedPrice += difference;
			} else {
				expectedPrice = Math.max(0, expectedPrice - difference);
			}
			System.out.println(String.format("Expected price is %d", expectedPrice));
			assertEquals(expectedPrice, market.getPrice());
			assertTrue(market.getPrice() >= 0);

		}
	}

	@Test
	@RepeatedTest(1000)
	public void testHistory() {
		randomGenerator = new Random();
		long seed = this.randomGenerator.nextLong();
		Random randomGenerator = new Random(seed);
		DoggieCoinMarket market = new DoggieCoinMarket(seed);
		int expectedPrice = 10;
		assertEquals(expectedPrice, market.getPrice());
		List<Integer> history = new LinkedList<Integer>();
		history.add(expectedPrice);

		// Run 100 ticks
		for (int i = 0; i < 100; ++i) {
			market.tickPrice();
			int difference = randomGenerator.nextInt(10);
			boolean increasePrice = randomGenerator.nextDouble() < .5;

			if (increasePrice) {
				expectedPrice += difference;
			} else {
				expectedPrice = Math.max(0, expectedPrice - difference);
			}
			history.add(expectedPrice);
			System.out.println(String.format("Expected price is %d", expectedPrice));
			assertEquals(expectedPrice, market.getPrice());
			assertTrue(market.getPrice() >= 0);
		}

		// Simulate Elan spawn
		market.setElanAlive(true);
		expectedPrice *= 10;

		// Run another 100 ticks
		for (int i = 0; i < 100; ++i) {
			market.tickPrice();
			int difference = 10 * randomGenerator.nextInt(10);
			boolean increasePrice = randomGenerator.nextDouble() < .5;

			if (increasePrice) {
				expectedPrice += difference;
			} else {
				expectedPrice = Math.max(0, expectedPrice - difference);
			}
			history.add(expectedPrice);
			System.out.println(String.format("Expected price is %d", expectedPrice));
			assertEquals(expectedPrice, market.getPrice());
			assertTrue(market.getPrice() >= 0);
		}

		// Assert the histories are the same
		assertEquals(history, market.getPriceHistory());
	}

	@Test
	@RepeatedTest(1000)
	public void testElanPrices() {
		randomGenerator = new Random();
		long seed = this.randomGenerator.nextLong();
		Random randomGenerator = new Random(seed);
		DoggieCoinMarket market = new DoggieCoinMarket(seed);
		int expectedPrice = 10;
		assertEquals(expectedPrice, market.getPrice());

		// Run 5 ticks
		for (int i = 0; i < 5; ++i) {
			market.tickPrice();
			int difference = randomGenerator.nextInt(10);
			boolean increasePrice = randomGenerator.nextDouble() < .5;

			if (increasePrice) {
				expectedPrice += difference;
			} else {
				expectedPrice = Math.max(0, expectedPrice - difference);
			}
			System.out.println(String.format("Expected price is %d", expectedPrice));
			assertEquals(expectedPrice, market.getPrice());
			assertTrue(market.getPrice() >= 0);
		}

		// Simulate Elan spawn
		market.setElanAlive(true);
		expectedPrice *= 10;

		// Run another 5 iterations
		for (int i = 0; i < 5; ++i) {
			market.tickPrice();
			int difference = 10 * randomGenerator.nextInt(10);
			boolean increasePrice = randomGenerator.nextDouble() < .5;

			if (increasePrice) {
				expectedPrice += difference;
			} else {
				expectedPrice = Math.max(0, expectedPrice - difference);
			}
			System.out.println(String.format("Expected price is %d", expectedPrice));
			assertEquals(expectedPrice, market.getPrice());
			assertTrue(market.getPrice() >= 0);
		}

		// Ensure setting Elan to alive while he's alive doesn't change anything
		market.setElanAlive(true);

		// Run another 5 iterations
		for (int i = 0; i < 5; ++i) {
			market.tickPrice();
			int difference = 10 * randomGenerator.nextInt(10);
			boolean increasePrice = randomGenerator.nextDouble() < .5;

			if (increasePrice) {
				expectedPrice += difference;
			} else {
				expectedPrice = Math.max(0, expectedPrice - difference);
			}
			System.out.println(String.format("Expected price is %d", expectedPrice));
			assertEquals(expectedPrice, market.getPrice());
			assertTrue(market.getPrice() >= 0);
		}

		// Simulate Elan dead
		market.setElanAlive(false);
		expectedPrice /= 10;

		// Run another 5 iterations
		for (int i = 0; i < 5; ++i) {
			market.tickPrice();
			int difference = randomGenerator.nextInt(10);
			boolean increasePrice = randomGenerator.nextDouble() < .5;

			if (increasePrice) {
				expectedPrice += difference;
			} else {
				expectedPrice = Math.max(0, expectedPrice - difference);
			}
			System.out.println(String.format("Expected price is %d", expectedPrice));
			assertEquals(expectedPrice, market.getPrice());
			assertTrue(market.getPrice() >= 0);
		}

		// Ensure setting Elan to dead while he's dead doesn't change anything
		market.setElanAlive(false);

		// Run another 5 iterations
		for (int i = 0; i < 5; ++i) {
			market.tickPrice();
			int difference = randomGenerator.nextInt(10);
			boolean increasePrice = randomGenerator.nextDouble() < .5;

			if (increasePrice) {
				expectedPrice += difference;
			} else {
				expectedPrice = Math.max(0, expectedPrice - difference);
			}
			System.out.println(String.format("Expected price is %d", expectedPrice));
			assertEquals(expectedPrice, market.getPrice());
			assertTrue(market.getPrice() >= 0);
		}
	}
}
