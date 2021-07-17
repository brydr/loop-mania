package unsw.loopmania;

import java.util.Random;

/**
 * a basic form of enemy in the world
 */
public abstract class BasicEnemy extends MovingEntity {
    // TODO = modify this, and add additional forms of enemy

    private int attackPower;
    private int battleRadius;
    private int supportRadius;
    private int experienceGain;
    Boolean inTrance;

    public BasicEnemy(PathPosition position) {
        super(position);
        inTrance = false;
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

    public int getExperienceGain() {
        return experienceGain;
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

    public void setExperienceGain(int experience) {
        this.experienceGain = experience;
    }

    /**
     * move the enemy
     */
    public void move(){
        // TODO = modify this, since this implementation doesn't provide the expected enemy behaviour
        // this basic enemy moves in a random direction... 25% chance up or down, 50% chance not at all...
        int directionChoice = (new Random()).nextInt(4);
        if (directionChoice == 0){
            moveUpPath();
        }
        else if (directionChoice == 1){
            moveDownPath();
        }
    }

    public Boolean getInTrance() {
        return inTrance;
    }

    public void setInTrance(Boolean inTrance) {
        this.inTrance = inTrance;
    }


    public void takeDamage(int damage) {
        int hp = this.getHp();
        this.setHp(hp - damage);
    }

    public abstract AlliedSoldier convertToFriendly(Character character);
    public abstract void attack(Character character);
}
