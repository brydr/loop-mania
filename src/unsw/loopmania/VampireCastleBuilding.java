package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * a basic form of building in the world
 */
public class VampireCastleBuilding extends Building implements EnemySpawner {
    // Incremented for each cycle of the Path. When it reaches 5, it is reset to 0 and a Vampire is spawned.
    private int spawnCounter;

    public VampireCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public String getImage() {
        return "src/images/vampire_castle_building_purple_background.png";
    }

    @Override
    public boolean spawn(boolean isCycle) {
        // Increment spawn counter
        if (isCycle)
            spawnCounter++;

        if (spawnCounter == 5) {
            // Reset the counter
            spawnCounter = 0;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BasicEnemy spawnEnemy(PathPosition pos) {
        Vampire vampire = new Vampire(pos);
        return (BasicEnemy) vampire;
    }
}
