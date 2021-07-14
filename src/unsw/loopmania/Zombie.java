package unsw.loopmania;

import java.util.Arrays;
import java.util.List;

import java.util.Random;
public class Zombie extends BasicEnemy implements Undead {
    public Zombie(PathPosition position) {
        super(position);
        this.setAttack(6);
        this.setHp(30);
        this.setSpeed(2);
        this.setBattleRadius(2);
        this.setSupportRadius(2);
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
            this.setSpeed(2);
        } 
    }

    public void attack(Character character) {
        int criticalBiteChance = (new Random()).nextInt(10);    // A random number between 0 and 9.

        int attackPower = this.getAttack();
        List<AlliedSoldier> alliedSoldiers = character.getListAlliedSoldiers();
        // If the character has allied soldiers it should attack them instead.
        if (alliedSoldiers.size() > 0) {
            AlliedSoldier firstSoldier = alliedSoldiers.get(0);
            firstSoldier.takeDamage(attackPower);
            if (firstSoldier.getHp() <= 0) {
                character.removeAlliedSoldier(firstSoldier);
            } else if (criticalBiteChance == 0) {
                Zombie newZombie = new Zombie(firstSoldier.getPosition());
                setConvertedToEnemy(character.convertToEnemy(firstSoldier, newZombie));
            }
        } else {
            character.takeDamage(attackPower);
        }
    }
    
    public void criticalBite() {   

    }
}
