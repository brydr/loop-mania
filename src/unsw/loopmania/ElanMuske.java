package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;
public class ElanMuske extends BossEnemy {

    private boolean runAway;
    
    public ElanMuske(PathPosition position) {
        super(position);
        this.setAttack(50);
        this.setHp(1000);
        this.setSpeed(1);
        this.setBattleRadius(1);
        this.setSupportRadius(1);
        this.setExperienceGain(1000);
        int runAwayChance = (new Random()).nextInt(10);
        // runs away at a 70% chance.
        if (runAwayChance < 7) {
            runAway = true;   
        } else {
            runAway = false;
        }
    }

    @Override
    public boolean getRunAway() {
        return runAway;
    }
    
    @Override
    public void setRunAway(boolean runAway) {
        this.runAway = runAway;
    }

    @Override
    public List<Item> dropLoot() {
        List<Item> loot = new ArrayList<Item>();
        int oneRingChance = new Random().nextInt(100); // A random value between 0 and 99 inclusive.
        if (oneRingChance < 3) {
            TheOneRing theOneRing = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
            loot.add(theOneRing);
        }
        return loot;
    }
    @Override
    public void move() {
        // First subtract 1 to the speed and check if the speed goes to 0 then it will move and set speed back to 0.
        // This is implemented this way in case the user may add items that may stun the enemy causing it not to move. In that case the speed to can be set to the amount of rounds the
        // enemy is stunned for.
        // This is also useful for implementing the speed for zombies since they are slow and require two ticks to move.
        int speed = this.getSpeed();
        this.setSpeed(speed - 1);

        if (this.getSpeed() == 0) {
            int directionChoice = (new Random()).nextInt(2);
            if (directionChoice == 0){
                for (int i = 0; i < 2; i++) {
                    moveUpPath();
                }
            }
            else if (directionChoice == 1){
                for (int i = 0; i < 2; i++) {
                    moveDownPath();
                }
            }

            this.setSpeed(1);
        } 
    }

    @Override
    public void attack(Character character) {
        int attackPower = this.getAttack();
        character.takeDamage(attackPower);
    }

    @Override
    public void healEnemies(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            int enemyHp = enemy.getHp();
            enemyHp += 20;
            enemy.setHp(enemyHp);
        }
    }

    @Override
    public String getImage() {
        String slugImage = "src/images/ElanMuske.png";
        return slugImage;
    }
}
