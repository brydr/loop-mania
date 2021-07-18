package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

public class Gold extends Currency {
    public IntegerProperty amount;

    public Gold() {
        amount = new SimpleIntegerProperty();
        amount.setValue(0);
    }
    
    public int getGold() {
        return amount.get();
    }

    public IntegerProperty getGoldProperty() {
        return amount;
    }

    public void addGold(int add) {
        amount.setValue(amount.get() + add);
    }

    public void subtractGold(int sub) {
        amount.setValue(amount.get() - sub);
    }
}
