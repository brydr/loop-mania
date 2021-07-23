package unsw.loopmania;

import java.util.Random;
import java.time.Duration;
public class Vampire extends BasicEnemy implements Undead {

    // criticalChance is only used for testing, primarily for the shield.
    public double criticalChance;
    private int criticalDuration;
    private boolean alreadyInCritical;

    public static final int STARTING_HP = 60;

    public Vampire(PathPosition position) {
        super(position);
        this.setAttack(12);
        this.setHp(STARTING_HP);
        this.setSpeed(1);
        this.setBattleRadius(2);
        this.setSupportRadius(3);
        this.setExperienceGain(200);
        this.criticalDuration = 0;
        criticalChance = 0.2;
        this.alreadyInCritical = false;
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
            super.move();
            this.setSpeed(1);
        }
    }

    @Override
    public void attack(Character character) {

        int criticalBiteChance;

        if (character.getEquippedShield() == null) {
            criticalBiteChance = (new Random()).nextInt(55); // Random number between 0 and 54 inclusive
            criticalChance = 0.2;
        } else {
            criticalBiteChance = (new Random()).nextInt(100); // Random number between 0 and 99 inclusive
            criticalChance = 0.12;
        }


        if (criticalDuration == 0) {
            alreadyInCritical = false;
        }

        if (criticalBiteChance <= 11 && alreadyInCritical == false) {    // If a criticalBite chance occurs and the vampire is not already in a criticalBite state.
            alreadyInCritical = true;
            criticalBite();
        } else if (alreadyInCritical && criticalDuration != 0) {     // If the vampire is already in a criticalBite state.
            criticalBite();
            criticalDuration -= 1;  // Subtract 1 from the duration.
        } else {
            alreadyInCritical = false;
        }

        int attackPower = this.getAttack();
        character.takeDamage(attackPower);
        // Set the attack back to 12 in case the attack got increased due to critical bite.
        this.setAttack(12);
    }

    public void criticalBite() {
        int criticalBiteDamage = (new Random()).nextInt(17) + 4; // A random number between 4 and 20 inclusive
        // This prevents the vampire from entering a criticalBite state whilst it is already in a criticalBite state.
        if (!(alreadyInCritical)) {
            criticalDuration = (new Random()).nextInt(3); // A random number between 0 and 2 inclusive.
        }

        int attackPower = this.getAttack();
        this.setAttack(attackPower + criticalBiteDamage);
    }

    @Override
    public String getImage() {
        String vampireImage = "src/images/vampire.png";
        return vampireImage;
    }
}
