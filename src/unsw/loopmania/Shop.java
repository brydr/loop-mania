package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

public class Shop {
	public final static int SHOP_WIDTH = 3;
	public final static int SHOP_HEIGHT = 3;
	private final List<BasicItem> inventory = new ArrayList<BasicItem>();
	private final GameMode gameMode;
	private final LoopManiaWorld world;
	private final RandomObjectGenerator itemGenerator;
	public final static int MAX_SHOP_INVENTORY = Shop.SHOP_WIDTH * Shop.SHOP_HEIGHT;
	private final List<BasicItem> purchaseHistory = new ArrayList<BasicItem>();
	private final Character character;

	/**
	 * Shop class for purchasing and selling items
	 *
	 * @param world      Current world
	 * @param strategy   Difficulty setting to determine whether the player can buy
	 *                   an item
	 * @param randomSeed Seed for the random generator
	 */
	public Shop(LoopManiaWorld world, long randomSeed) {
		this.itemGenerator = new RandomObjectGenerator(randomSeed);
		this.world = world;
		this.generateShopInventory();
		this.character = world.getCharacter();
		this.gameMode = world.getGameMode();
		// world.getCharacter().addGold(90000000);
	}

	/**
	 * Shop class for purchasing and selling items, seed defaults to nanoTime
	 *
	 * @param world    Current world
	 * @param strategy Difficulty setting to determine whether the player can buy an
	 *                 item
	 */
	public Shop(LoopManiaWorld world) {
		this(world, System.nanoTime());
	}

	/**
	 * Generates the shop inventory using the random item generator
	 */
	private void generateShopInventory() {
		for (int i = 0; i < Shop.MAX_SHOP_INVENTORY; i++) {
			this.inventory.add(this.itemGenerator.nextBasicItem(0, 0));
		}
	}

	/**
	 * Gets the shop's inventory
	 *
	 * @return An immutable list containing the shop's inventory
	 */
	public List<BasicItem> getInventory() {
		return List.copyOf(this.inventory);
	}

	/**
	 * Buys an item, checks whether the shop actually has that item in stock and
	 * whether the character has enough money. Also subtracts the price of the item
	 * from the character if they can afford it
	 *
	 * @param item The item the character wants to buy
	 * @return Whether the purchase succeeded
	 */
	public boolean buy(BasicItem item) {
		if (!this.canBuy(item)) {
			return false;
		}
		// When execution reaches here, the player can buy the item

		character.subtractGold(item.getBuyPrice());
		this.inventory.remove(item);
		world.addUnequippedItem(item);
		purchaseHistory.add(item);

		return true;
	}

	/**
	 * Sells an item, checks whether the character actually has the item. Also adds
	 * money for the character
	 *
	 * @param item The item the character wants to sell
	 * @return Whether the transaction succeeded
	 */
	public boolean sell(BasicItem item) {
		if (!world.getInventory().contains(item)) {
			return false;
		}

		world.removeUnequippedInventoryItem(item);
		character.addGold(item.getSellPrice());

		return true;
	}

	/**
	 * Checks whether the character can buy the item by checking whether the item is
	 * in stock and whether they can afford it
	 *
	 * @param item The item the character wants to buy
	 * @return Whether they can buy it
	 */
	private boolean canBuy(BasicItem item) {
		boolean inStock = inventory.contains(item);
		boolean hasEnoughGold = character.getGold() - item.getBuyPrice() >= 0;
		boolean passesItemLimits = gameMode.checkItemLimits(item, this.purchaseHistory);

		return inStock && hasEnoughGold && passesItemLimits;
	}
}
