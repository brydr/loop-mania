package unsw.loopmania;


import javafx.beans.property.SimpleIntegerProperty;

/**
 * a basic form of building in the world
 */
public class VampireCastleBuilding extends Building implements EnemySpawner {
    // Incremented for each cycle of the Path. When it reaches 5, it is reset to 0 and a Vampire is spawned.
    private int spawnCounter;

    // TODO = add more types of building, and make sure buildings have effects on entities as required by the spec
    public VampireCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getImage() {
        String vampireCastleBuildingImage = "src/images/vampire_castle_building_purple_background.png";
        //String vampireCastleBuildingImage = "src/images/vampire_castle_building_purple_background.png";
        return vampireCastleBuildingImage;
    }

    public boolean spawn(boolean isCycle) {
        // Increment spawn counter
        if (isCycle) {
            spawnCounter++;
        }
        if (spawnCounter == 5) {
            // Reset the counter
            spawnCounter = 0;
            return true;
        }
        return false;
    }

    public BasicEnemy spawnEnemy(PathPosition pos) {
        Vampire vampire = new Vampire(pos);
        return vampire;
    }
}
