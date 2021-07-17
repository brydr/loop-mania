package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

public class Shop {
	private final List<BasicItem> shopInventory = new ArrayList<BasicItem>();
	private final ShopStrategy shopStrategy;
	private final LoopManiaWorld world;
	private final RandomItemGenerator itemGenerator;
	private final static int MAX_SHOP_INVENTORY = 20;
	private final static List<BasicItem> purchaseHistory = new ArrayList<BasicItem>();
	private final Character character;

	/**
	 * Shop class for purchasing and selling items
	 *
	 * @param world Current world
	 * @param strategy Difficulty setting to determine whether the player can buy an item
	 * @param randomSeed Seed for the random generator
	 */
	public Shop(LoopManiaWorld world, ShopStrategy strategy, long randomSeed) {
		this.itemGenerator = new RandomItemGenerator(randomSeed);
		this.shopStrategy = strategy;
		this.world = world;
		this.generateShopInventory();
		this.character = world.getCharacter();
	}

	/**
	 * Shop class for purchasing and selling items, seed defaults to nanoTime
	 *
	 * @param world Current world
	 * @param stratgy Difficulty setting to determine whether the player can buy an item
	 */
	public Shop(LoopManiaWorld world, ShopStrategy strategy) {
		this(world, strategy, System.nanoTime());
	}

	/**
	 * Generates the shop inventory using the random item generator
	 */
	private void generateShopInventory() {
		for (int i = 0; i < Shop.MAX_SHOP_INVENTORY; i++) {
			this.shopInventory.add(this.itemGenerator.nextItem(0, 0));
		}
	}

	/**
	 * Gets a list of the shop's inventory
	 * Caller shouldn't try to mutate the list
	 * @return Shop's inventory
	 */
	public List<BasicItem> getShopInventory() {
		return this.shopInventory;
	}

	/**
	 * Buys an item, checks whether the shop actually has that item in stock and whether the character has enough money
	 *
	 * @param item The item the character wants to buy
	 * @return Whether the purchase was successful
	 */
	public boolean buy(BasicItem item) {
		if (!this.shopInventory.contains(item)) {
			// Not in stock case
			return false;
		}

		if (character.getGold() - item.getBuyPrice() < 0) {
			// Not enough money case
			return false;
		}

		character.subtractGold(item.getBuyPrice());
		this.shopInventory.remove(item);
		world.addUnequippedItem(item);

		return true;
	}


	public void sell(BasicItem item) {
		world.removeUnequippedInventoryItem(item);
		character.addGold(item.getSellPrice());
	}
}
