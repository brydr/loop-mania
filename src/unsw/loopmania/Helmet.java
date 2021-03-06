package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Helmet extends ProtectiveGear {
	private final static double DAMAGE_MULTIPLIER = .7;
	private final static int BUY_PRICE = 120;
	private final static int SELL_PRICE = 60;

	public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, Helmet.BUY_PRICE, Helmet.SELL_PRICE);
	}

	@Override
	public int calculateDamage(int attackPower) {
		decDurability();
		return (int) Math.round(Helmet.DAMAGE_MULTIPLIER * attackPower);
	}

	@Override
	public String getImage() {
        return "src/images/helmet.png";
    }

	@Override
	public String getEmptySlotId() {
		return "helmetCell";
	}

	@Override
	public String getEmptySlotImage() {
		return "src/images/helmet_slot.png";
	}
}
