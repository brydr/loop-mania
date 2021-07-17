package unsw.loopmania;

import java.util.List;

public class StandardMode implements ShopStrategy {
	/**
	 * Standard mode has no restrictions on item limits
	 */
	@Override
	public boolean checkItemLimits(Item wanToBuy, List<Item> purchaseHistory) {
		return true;
	}
}
