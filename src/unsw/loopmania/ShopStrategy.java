package unsw.loopmania;

import java.util.List;

public interface ShopStrategy {
	// TODO
	public boolean checkItemLimits(BasicItem wantToBuy, List<BasicItem> purchaseHistory);
}
