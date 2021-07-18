package unsw.loopmania;

public class ShopController {
	private final Shop shop;

	public ShopController(World world, ShopStrategy strategy, long seed) {
		shop = new Shop(world, strategy, seed);
	}

	public ShopController(World world, ShopStrategy strategy) {
		this(world, strategy, System.nanoTime());
	}
}
