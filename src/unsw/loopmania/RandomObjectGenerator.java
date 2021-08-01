package unsw.loopmania;

import java.util.Random;
import javafx.beans.property.SimpleIntegerProperty;


/**
 * Generates a Card/Item instance from a random subclass by composing a {@code java.util.Random} instance.
 */
public class RandomObjectGenerator {
    // Number of subclasses of Item
    private static final int N_ITEM_VARIANTS = 7;
    // Number of subclasses of Card 
    private static final int N_CARD_VARIANTS = 7;

    private final Random randomGenerator;

    /**
     * Construct a new RandomObjectGenerator object
     */
    public RandomObjectGenerator() {
        randomGenerator = new Random();
    }

    /**
     * Construct a new RandomObjectGenerator object
     * @param seed Seed for the random generator
     */
    public RandomObjectGenerator(long seed) {
        randomGenerator = new Random(seed);
    }

    /**
     * Gets an item of a random type. This is uniformly distributed, so each type/subclass is equally likely.
     * @param xVal x-coordinate of our item's "position"
     * @param yVal y-coordinate of our item's "position"
     * @return An instance of a (random) subclass of BasicItem
     */
    public BasicItem nextBasicItem(int xVal, int yVal) {
        final var x = new SimpleIntegerProperty(xVal);
        final var y = new SimpleIntegerProperty(yVal);

        switch (randomGenerator.nextInt(N_ITEM_VARIANTS)) {
            case 0: return new HealthPotion(x, y);
            case 1: return new Staff(x, y);
            case 2: return new Stake(x, y);
            case 3: return new Sword(x, y);
            case 4: return new Armour(x, y);
            case 5: return new Shield(x, y);
            case 6: return new Helmet(x, y);
            default: throw new RuntimeException("Can't generate random BasicItem");
        }
    }

    /**
     * Gets a card of a random type. This is uniformly distributed, so each type/subclass is equally likely.
     * @param xVal x-coordinate of our cards "position"
     * @param yVal y-coordinate of our cards "position"
     * @return An instance of a (random) subclass of Card
     */
    public Card nextCard(int xVal, int yVal) {
        final var x = new SimpleIntegerProperty(xVal);
        final var y = new SimpleIntegerProperty(yVal);

        switch (randomGenerator.nextInt(N_CARD_VARIANTS)) {
            case 0: return new BarracksCard(x, y);
            case 1: return new CampfireCard(x, y);
            case 2: return new TowerCard(x, y);
            case 3: return new TrapCard(x, y);
            case 4: return new VillageCard(x, y);
            case 5: return new ZombiePitCard(x, y);
            case 6: return new VampireCastleCard(x, y);
            default: throw new RuntimeException("Can't generate random Card");
        }
    }
}
