package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class DoggieCoin extends BasicItem {
	private final DoggieCoinMarket market;

	public DoggieCoin(SimpleIntegerProperty x, SimpleIntegerProperty y, DoggieCoinMarket coinMarket) {
		super(x, y, -1, -1);
		market = coinMarket;
	}

	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSellPrice() {
		return (int) Math.round(market.getPrice());
	}

	@Override
	public int getBuyPrice() {
		return (int) Math.round(market.getPrice());
	}
}
