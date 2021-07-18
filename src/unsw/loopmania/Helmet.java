package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Helmet extends ProtectiveGear {
	// TODO reduce wearer's attack power
	private final static double DAMAGE_MULTIPLIER = .7;
	private final static int BUY_PRICE = 120;
	private final static int SELL_PRICE = 60;

	public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y, Helmet.BUY_PRICE, Helmet.SELL_PRICE);
	}

	@Override
	public int calculateDamage(int attackPower) {
		return (int) Math.round(Helmet.DAMAGE_MULTIPLIER * attackPower);
	}

	public String getImage() {
        String helmetImage = "src/images/helmet.png";
        return helmetImage;
    }
}
