package unsw.loopmania;

import org.javatuples.Pair;

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
        String vampireCastleBuildingImage = "src/images/vampire_castle_building_transparent.png";
        //String vampireCastleBuildingImage = "src/images/vampire_castle_building_purple_background.png";
        return vampireCastleBuildingImage;
    }
    
    /**
     * Actually performs a spawn of a vampire, trigged by spawn(). 
     * @param world A reference to the {@code LoopManiaWorld} instance.
     */
    private void doSpawn(LoopManiaWorld world) {
        var orderedPath = world.getOrderedPath();
        Pair<Integer, Integer> spawnLoc = world.getNearestPathTile(getX(), getY());
        int buildingPosIndex = orderedPath.indexOf(spawnLoc);

        Vampire newVampire = new Vampire(
            new PathPosition(buildingPosIndex, orderedPath)
        );
        
        world.addEnemies(newVampire);
    }

    @Override
    public void spawn(LoopManiaWorld world) {
        // Increment spawn counter
        spawnCounter++;
        if (spawnCounter == 5) {
            // Spawn a Vampire
            doSpawn(world);
            // Reset the counter
            spawnCounter = 0;
        }
    }
}
