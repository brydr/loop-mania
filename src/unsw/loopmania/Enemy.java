package unsw.loopmania;

import java.util.List;
import java.util.Random;

import org.json.JSONArray;

/**
 * an of enemy in the world
 */
public abstract class Enemy extends MovingEntity {
    private int attackPower;
    private int battleRadius;
    private int supportRadius;
    private int experienceGain;
    private boolean runAway;
    private BasicEnemy convertedToEnemy;
    private final int startingHp;

    public Enemy(PathPosition position, int startingHp) {
        super(position);
        convertedToEnemy = null;
        runAway = false;
        this.startingHp = startingHp;
        setHp(startingHp);
    }

    public boolean getRunAway() {
        return runAway;
    }
    public void setRunAway(boolean runAway) {
        this.runAway = runAway;
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

    public BasicEnemy getConvertedToEnemy() {
        return convertedToEnemy;
    }

    public void setConvertedToEnemy(BasicEnemy e) {
        this.convertedToEnemy = e;
    }

    /**
     * move the enemy
     */
    public void move(){
        // this basic enemy moves in a random direction... 25% chance up or down, 50% chance not at all...
        int directionChoice = (new Random()).nextInt(4);
        if (directionChoice == 0){
            moveUpPath();
        }
        else if (directionChoice == 1){
            moveDownPath();
        }
    }

    public abstract boolean getInTrance();

    public void setInTrance(Boolean inTrance) {
    }

    public void takeDamage(int damage) {
        int hp = this.getHp();
        this.setHp(hp - damage);
    }

    public void heal(int healAmount) {
        setHp(Math.min(getHp() + healAmount, startingHp));
    }

    public AlliedSoldier convertToFriendly(Character character) {
        return null;
    }
    public abstract void attack(Character character);

    public abstract List<Item> dropLoot(JSONArray rareItems);
    public abstract String getImage();
}
