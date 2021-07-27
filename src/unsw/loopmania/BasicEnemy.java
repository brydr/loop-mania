package unsw.loopmania;

import java.time.Duration;
import java.util.Random;

/**
 * a basic form of enemy in the world
 */
public abstract class BasicEnemy extends Enemy {
    // TODO = modify this, and add additional forms of enemy

    private BasicEnemy convertedToEnemy;

    public BasicEnemy(PathPosition position) {
        super(position);
        convertedToEnemy = null;
    }

    public BasicEnemy getConvertedToEnemy() {
        return convertedToEnemy;
    }

    public void setConvertedToEnemy(BasicEnemy e) {
        this.convertedToEnemy = e;
    }
    
    public abstract void attack(Character character);
    public abstract String getImage();
}
