package unsw.loopmania;

import java.util.List;

public class ConfusingMode implements GameMode {
	private final GameMode innerMode = new StandardMode();

	@Override
	public boolean checkItemLimits(BasicItem wantToBuy, List<BasicItem> purchaseHistory) {
		// Delegate to standard mode
		return innerMode.checkItemLimits(wantToBuy, purchaseHistory);
	}

	@Override
	public String toString() {
		return "Confusing Mode";
	}
}
