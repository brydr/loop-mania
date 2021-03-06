package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Armour extends ProtectiveGear {
	private final static int BUY_PRICE = 200;
	private final static int SELL_PRICE = 100;

	public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, Armour.BUY_PRICE, Armour.SELL_PRICE);
	}

	@Override
	public int calculateDamage(int attackPower) {
		decDurability();
		return attackPower / 2;
	}

	@Override
	public String getImage() {
        return "src/images/armour.png";
    }

	@Override
	public String getEmptySlotId() {
		return "armourCell";
	}

	@Override
	public String getEmptySlotImage() {
		return "src/images/armour_slot.png";
	}
}
