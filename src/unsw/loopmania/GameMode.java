package unsw.loopmania;

import java.util.List;

public interface GameMode {
	public boolean checkItemLimits(BasicItem wantToBuy, List<BasicItem> purchaseHistory);
}
