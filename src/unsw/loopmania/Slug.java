package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class Slug extends BasicEnemy {
    public static final int STARTING_HP = 30;

    public Slug(PathPosition position) {
        super(position, STARTING_HP);
        this.setAttack(3);
        this.setSpeed(1);
        this.setBattleRadius(1);
        this.setSupportRadius(1);
        this.setExperienceGain(10);
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
            super.move();
            this.setSpeed(1);
        }
    }

    @Override
    public void attack(Character character) {
        int attackPower = this.getAttack();
        character.takeDamage(attackPower);
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
    public String getImage() {
        return "src/images/slug.png";
    }
}
