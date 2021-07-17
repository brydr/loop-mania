package unsw.loopmania;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

public class ZombiePitBuilding extends Building implements EnemySpawner {

    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getImage() {
        String zombiePitImage = "src/images/zombie_pit.png";
        return zombiePitImage;
    }

    @Override
    public void spawn(LoopManiaWorld world) {
        var orderedPath = world.getOrderedPath();
        Pair<Integer, Integer> spawnLoc = world.getNearestPathTile(getX(), getY());
        int spawnLocIndex = orderedPath.indexOf(spawnLoc);
        Zombie newZombie = new Zombie(
            new PathPosition(spawnLocIndex, orderedPath)
        );
        world.addEnemies(newZombie);
    }
}
