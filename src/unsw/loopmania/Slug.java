package unsw.loopmania;
public class Slug extends BasicEnemy {
    
    public Slug(PathPosition position) {
        super(position);
        this.setAttack(3);
        this.setHp(30);
        this.setSpeed(1);
        this.setBattleRadius(1);
        this.setSupportRadius(1);
    }



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

    public void attack(MovingEntity movingEntity) {
        int attackPower = this.getAttack();
        movingEntity.takeDamage(attackPower);
    }

}
