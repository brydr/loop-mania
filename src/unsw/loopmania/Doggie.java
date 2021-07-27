package unsw.loopmania;

import java.util.Random;
public class Doggie extends BossEnemy {
    int stunChance;
    public Doggie(PathPosition position) {
        super(position);
        this.setAttack(20);
        this.setHp(400);
        this.setSpeed(1);
        this.setBattleRadius(1);
        this.setSupportRadius(1);
        this.setExperienceGain(400);
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
        int stunChance = (new Random()).nextInt(10);    // A random number between 0 and 9.
        int attackPower = this.getAttack();
        character.takeDamage(attackPower);
        // Stun the character at a 10% chance.
        if (stunChance == 0) {
            character.setStunned(3);
        }
    }

    // Used for testing.
    public void stunAttack(Character character) {
        int attackPower = this.getAttack();
        character.takeDamage(attackPower);
        character.setStunned(3);
    }

    @Override
    public String getImage() {
        String slugImage = "src/images/doggie.png";
        return slugImage;
    }
}
