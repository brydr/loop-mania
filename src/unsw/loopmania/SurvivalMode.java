package unsw.loopmania;

import java.util.List;

public class SurvivalMode implements ShopStrategy {
	private final static int MAX_HEALTH_POTIONS = 1;

	@Override
	public boolean checkItemLimits(BasicItem wantToBuy, List<BasicItem> purchaseHistory) {
		return wantToBuy instanceof HealthPotion ? countHealthPotions(purchaseHistory) < SurvivalMode.MAX_HEALTH_POTIONS
				: true;
	}

	private int countHealthPotions(List<BasicItem> purchaseHistory) {
		int healthPotions = 0;

		for (BasicItem item : purchaseHistory) {
			if (item instanceof HealthPotion) {
				healthPotions++;
			}
		}

		return healthPotions;
	}

	@Override
	public String toString() {
		return "Survival Mode";
	}
}
