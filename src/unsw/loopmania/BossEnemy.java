package unsw.loopmania;

import java.time.Duration;
import java.util.List;
import java.util.Random;

/**
 * a boss form of enemy in the world
 */
public abstract class BossEnemy extends Enemy {
    // TODO = modify this, and add additional forms of enemy

    public BossEnemy(PathPosition position) {
        super(position);
    }

    // A hook that ElanMuske implements.
    public void healEnemies(List<BasicEnemy> enemies) {
    }

    public abstract String getImage();
}
