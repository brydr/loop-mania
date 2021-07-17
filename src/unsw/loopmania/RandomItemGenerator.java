package unsw.loopmania;

import java.util.Random;
import javafx.beans.property.SimpleIntegerProperty;
public class RandomItemGenerator {
    static final int N_ITEM_VARIANTS = 7;

    private Random randomGenerator;

    /**
     * Construct a new RandomCardGenerator object
     */
    public RandomItemGenerator() {
        randomGenerator = new Random();
    }

    public BasicItem nextItem(int xVal, int yVal) {

        var x = new SimpleIntegerProperty(xVal);
        var y = new SimpleIntegerProperty(yVal);

        switch (randomGenerator.nextInt(7)) {
            case 0: return new HealthPotion(x, y);
            case 1: return new Staff(x, y);
            case 2: return new Stake(x, y);
            case 3: return new Sword(x, y);
            case 4: return new Armour(x, y);
            case 5: return new Shield(x, y);
            case 6: return new Helmet(x, y);
            default: throw new RuntimeException("Can't generate random item");
        }
    }
}
