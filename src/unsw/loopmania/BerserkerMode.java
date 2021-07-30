package unsw.loopmania;

import java.util.List;

public class BerserkerMode implements ShopStrategy {
	private final static int MAX_PROTECTIVE_GEAR = 1;

	@Override
	public boolean checkItemLimits(BasicItem wantToBuy, List<BasicItem> purchaseHistory) {
		return wantToBuy instanceof ProtectiveGear
				? countProtectiveGear(purchaseHistory) < BerserkerMode.MAX_PROTECTIVE_GEAR
				: true;
	}

	private int countProtectiveGear(List<BasicItem> purchaseHistory) {
		int protectiveGears = 0;

		for (BasicItem item : purchaseHistory) {
			if (item instanceof ProtectiveGear)
				protectiveGears++;
		}

		return protectiveGears;
	}

	@Override
	public String toString() {
		return "Berserker Mode";
	}
}
