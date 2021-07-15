package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
public class TheOneRing extends RareItem {
    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
		super(x, y);
	}

	public String getImage() {
        String oneRingImage = "src/images/the_one_ring.png";
        return oneRingImage;
    }
}
