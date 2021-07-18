package unsw.loopmania;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

public class ZombiePitBuilding extends Building implements EnemySpawner {

    public ZombiePitBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public String getImage() {
        String zombiePitImage = "src/images/zombie_pit.png";
        return zombiePitImage;
    }

    public boolean spawn(boolean isCycle) {
        if (isCycle) {
            return true;
        } 
        return false;
    }
}
