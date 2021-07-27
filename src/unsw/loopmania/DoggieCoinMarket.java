package unsw.loopmania;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DoggieCoinMarket {
	private final static int ELAN_MULTIPLIER = 10;
	private final static int INITIAL_PRICE = 10;
	private final static int MAX_FLUCTUATION = 10;
	private boolean elanAlive = false;
	private boolean elanAlivePreviousTick = false;

	private int currentPrice = INITIAL_PRICE;
	private int lastTickPrice = currentPrice;

	private final Random randomGenerator;
	private final List<Integer> priceHistory = new LinkedList<Integer>();

	public DoggieCoinMarket(long seed) {
		randomGenerator = new Random(seed);
		priceHistory.add(currentPrice);
	}

	public DoggieCoinMarket() {
		this(System.nanoTime());
	}

	public void setElanAlive(boolean alive) {
		elanAlive = alive;
	}

	public int getPrice() {
		return currentPrice;
	}

	public int getLastTickPrice() {
		return lastTickPrice;
	}

	public List<Integer> getPriceHistory() {
		return List.copyOf(priceHistory);
	}

	public void tickPrice() {
		lastTickPrice = currentPrice;
		// Elan spawning or dying events
		if (!elanAlivePreviousTick && elanAlive) {
			// Elan spawned
			currentPrice *= ELAN_MULTIPLIER;
		} else if (elanAlivePreviousTick && !elanAlive) {
			// Elan died
			currentPrice /= ELAN_MULTIPLIER;
		}
		int difference = randomGenerator.nextInt(MAX_FLUCTUATION);
		difference *= elanAlive ? ELAN_MULTIPLIER : 1;
		int newPrice = currentPrice + ((randomGenerator.nextDouble() < .5) ? difference : -difference);

		currentPrice = Math.max(0, newPrice);

		priceHistory.add(currentPrice);
		elanAlivePreviousTick = elanAlive;
	}
}
