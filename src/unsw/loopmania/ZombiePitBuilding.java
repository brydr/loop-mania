package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class ZombiePitBuilding extends Building implements EnemySpawner {

    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public String getImage() {
        return "src/images/zombie_pit.png";
    }

    @Override
    public boolean spawn(boolean isCycle) {
        if (isCycle) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BasicEnemy spawnEnemy(PathPosition pos) {
        Zombie zombie = new Zombie(pos);
        return zombie;
    }
}
