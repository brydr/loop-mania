package unsw.loopmania;

import java.util.Random;

/**
 * a basic form of enemy in the world
 */
public abstract class BasicEnemy extends MovingEntity {
    // TODO = modify this, and add additional forms of enemy
    public BasicEnemy(PathPosition position) {
        super(position);
    }

    // TODO
    public abstract void takeDamage(int damage);

    public int getHp() {
        // TODO
        return -1;
    }

    public abstract void convertToFriendly(int time);

    /**
     * move the enemy
     */
    public void move() {
        // TODO = modify this, since this implementation doesn't provide the expected
        // enemy behaviour
        // this basic enemy moves in a random direction... 25% chance up or down, 50%
        // chance not at all...
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0) {
            moveUpPath();
        } else if (directionChoice == 1) {
            moveDownPath();
        }
    }
}
