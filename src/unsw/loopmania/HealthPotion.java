package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class HealthPotion extends Consumable {

    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    

    public String getImage() {
        String healthPotionImage = "src/images/brilliant_blue_new.png";
        return healthPotionImage;
    }
}
