package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;

import javafx.beans.property.SimpleIntegerProperty;

public class ElanMuske extends BossEnemy {
    private final static int HEAL_AMOUNT = 20;
    private final static int STARTING_HP = 1000;
    private boolean runAway;

    public ElanMuske(PathPosition position) {
        super(position, STARTING_HP);
        this.setAttack(50);
        this.setSpeed(1);
        this.setBattleRadius(1);
        this.setSupportRadius(1);
        this.setExperienceGain(1000);
        runAway = true;
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
    public List<Item> dropLoot(JSONArray rareItems) {
        List<Item> loot = new ArrayList<Item>();
        int rareItemChance = new Random().nextInt(100); // A random value between 0 and 99 inclusive.
        if (rareItemChance < 3) {
            String item = rareItems.getString(new Random().nextInt(rareItems.length()));  // Selects a random item from the rare items list
            if (item.equals("the_one_ring")) {
                TheOneRing theOneRing = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                loot.add(theOneRing);
            } else if (item.equals("anduril_flame_of_the_west")) {
                AndurilFlameOfTheWest andurilFlameOfTheWest = new AndurilFlameOfTheWest(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                loot.add(andurilFlameOfTheWest);
            } else if (item.equals("tree_stump")) {
                TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                loot.add(treeStump);
            }
        }
        return loot;
    }

    @Override
    public void move() {
        // First subtract 1 to the speed and check if the speed goes to 0 then it will
        // move and set speed back to 0.
        // This is implemented this way in case the user may add items that may stun the
        // enemy causing it not to move. In that case the speed to can be set to the
        // amount of rounds the
        // enemy is stunned for.
        // This is also useful for implementing the speed for zombies since they are
        // slow and require two ticks to move.
        int speed = this.getSpeed();
        this.setSpeed(speed - 1);

        if (this.getSpeed() == 0) {
            int directionChoice = (new Random()).nextInt(2);
            if (directionChoice == 0) {
                for (int i = 0; i < 2; i++) {
                    moveUpPath();
                }
            } else if (directionChoice == 1) {
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
        // Set attack back to 50 in case of tree stump which reduces its attack by a 1/3.
        this.setAttack(50);
    }

    public void healEnemies(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            enemy.heal(HEAL_AMOUNT);
        }
    }

    @Override
    public String getImage() {
        return "src/images/ElanMuske.png";
    }
}
