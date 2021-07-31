package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class HealthPotion extends Consumable {
    private final static int BUY_PRICE = 50;
    private final static int SELL_PRICE = 20;

    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, HealthPotion.BUY_PRICE, HealthPotion.SELL_PRICE);
    }
    
    @Override
    public String getImage() {
        return "src/images/brilliant_blue_new.png";
    }
}
