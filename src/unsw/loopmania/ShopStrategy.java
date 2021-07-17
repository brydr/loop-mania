package unsw.loopmania;

import java.util.List;

public interface ShopStrategy {
	// TODO
	public boolean checkItemLimit(Item wantToBuy, List<Item> purchased);
}
