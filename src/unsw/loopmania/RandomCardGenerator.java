package unsw.loopmania;

import java.util.Random;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Generates a Card instance from a random subclass by composing a {@code java.util.Random} instance.
 */
public class RandomCardGenerator {
    // Number of subclasses of Card 
    static final int N_CARD_VARIANTS = 7;
    
    private Random randomGenerator;

    /**
     * Construct a new RandomCardGenerator object
     */
    public RandomCardGenerator() {
        randomGenerator = new Random();
    }
    
    /**
     * Gets a card of a random type. This is uniformly distributed, so each type/subclass is equally likely.
     * @param xVal x-coordinate of our cards "position"
     * @param yVal y-coordinate of our cards "position"
     * @return An instance of a (random) subclass of Card
     */
    public Card nextCard(int xVal, int yVal) {
        var x = new SimpleIntegerProperty(xVal);
        var y = new SimpleIntegerProperty(yVal);

        switch (randomGenerator.nextInt(N_CARD_VARIANTS)) {
            case 0: return new BarracksCard(x, y);
            case 1: return new CampfireCard(x, y);
            case 2: return new TowerCard(x, y);
            case 3: return new TrapCard(x, y);
            case 4: return new VillageCard(x, y);
            case 5: return new ZombiePitCard(x, y);
            case 6: return new VampireCastleCard(x, y);
            default: // This should *NEVER* happen
                assert 2 + 2 == 5;
                return null;
        }
    }
}