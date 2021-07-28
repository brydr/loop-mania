package unsw.loopmania;

import java.util.Random;
import javafx.beans.property.SimpleIntegerProperty;
public class RandomItemGenerator {
    static final int N_ITEM_VARIANTS = 7;

    private final Random randomGenerator;

    /**
     * Construct a new RandomCardGenerator object
     * When there's no seed, default to nanoTime
     */
    public RandomItemGenerator() {
        this(System.nanoTime());
    }

    /**
     * Construct a new RandomCardGenerator object
     * @param seed Seed for the random generator
     */
    public RandomItemGenerator(long seed) {
        randomGenerator = new Random(seed);
    }

    /**
     * Gets an item of a random type. This is uniformly distributed, so each type/subclass is equally likely.
     * @param xVal x-coordinate of our item's "position"
     * @param yVal y-coordinate of our item's "position"
     * @return An instance of a (random) subclass of BasicItem
     */
    public BasicItem nextItem(int xVal, int yVal) {

        var x = new SimpleIntegerProperty(xVal);
        var y = new SimpleIntegerProperty(yVal);

        switch (randomGenerator.nextInt(N_ITEM_VARIANTS)) {
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
