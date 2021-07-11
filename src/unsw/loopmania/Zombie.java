package unsw.loopmania;

import java.util.Random;
public class Zombie extends BasicEnemy implements Undead {

    private int attackPower = 6;
    private int hp = 30;
    private int speed = 2;
    private int battleRadius = 2;
    private int supportRadius = 2;
    public Zombie(PathPosition position) {
        super(position);
    }

    public int getHp() {
        return hp;
    }

    public int getBattleRadius() {
        return battleRadius;
    }

    public int getSupportRadius() {
        return supportRadius;
    }

    public void move() {
        // First subtract 1 to the speed and check if the speed goes to 0 then it will move and set speed back to 0.
        // This is implemented this way in case the user may add items that may stun the enemy causing it not to move. In that case the speed to can be set to the amount of rounds the
        // enemy is stunned for.
        // This is also useful for implementing the speed for zombies since they are slow and require two ticks to move.
        speed -= 1;

        if (speed == 0) {
            super.move();
            speed = 2;
        } 
    }

    public void attack(MovingEntity movingEntity) {
        int criticalBiteChance = (new Random()).nextInt(10);    // A random number between 0 and 9.

        if (criticalBiteChance == 0) {
            criticalBite(movingEntity);
        }

        movingEntity.takeDamage(attackPower);
    }

    public void takeDamage(int damage) {
        hp -= damage;
    }

    public void criticalBite(MovingEntity movingEntity) {   
        // If the criticalBite is inflicted on an allied Soldier then change its class into a zombie.
        if (movingEntity.getClass() == AlliedSoldier.class) {
            movingEntity = new Zombie(movingEntity.getPosition());
        }
    }
}
