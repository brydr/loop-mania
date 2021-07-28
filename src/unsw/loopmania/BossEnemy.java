package unsw.loopmania;

import java.util.List;

/**
 * a boss form of enemy in the world
 */
public abstract class BossEnemy extends Enemy {
    // TODO = modify this, and add additional forms of enemy

    public BossEnemy(PathPosition position) {
        super(position);
    }

    // A hook that ElanMuske implements.
    public void healEnemies(List<Enemy> enemies) {
    }

    public abstract String getImage();
}
