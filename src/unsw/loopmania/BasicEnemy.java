package unsw.loopmania;

import java.util.Random;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * a basic form of enemy in the world
 */
public abstract class BasicEnemy extends MovingEntity {
    // TODO = modify this, and add additional forms of enemy

    private int attackPower;
    private int battleRadius;
    private int supportRadius;

    public BasicEnemy(PathPosition position) {
        super(position);
    }

    public int getAttack() {
        return attackPower;
    }
    public int getBattleRadius() {
        return battleRadius;
    }

    public int getSupportRadius() {
        return supportRadius;
    }
    public void setAttack(int attack) {
        this.attackPower = attack;
    }
    public void setBattleRadius(int battleRadius) {
        this.battleRadius = battleRadius;
    }

    public void setSupportRadius(int supportRadius) {
        this.supportRadius = supportRadius;
    }

    /**
     * move the enemy
     */
    public void move(){
        // TODO = modify this, since this implementation doesn't provide the expected enemy behaviour
        // this basic enemy moves in a random direction... 25% chance up or down, 50% chance not at all...
        int directionChoice = (new Random()).nextInt(2);
        if (directionChoice == 0){
            moveUpPath();
        }
        else if (directionChoice == 1){
            moveDownPath();
        }
    }

    public void convertToFriendly() {
        this.destroy();
    }

    public abstract void attack(MovingEntity movingEntity);
    public abstract void takeDamage(MovingEntity movingEntity);
}
