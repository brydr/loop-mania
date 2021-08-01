package unsw.loopmania;

import java.util.List;

public class StandardMode implements GameMode {
	/**
	 * Standard mode has no restrictions on item limits
	 */
	@Override
	public boolean checkItemLimits(BasicItem wanToBuy, List<BasicItem> purchaseHistory) {
		return true;
	}

	@Override
	public String toString() {
		return "Standard Mode";
	}
}
