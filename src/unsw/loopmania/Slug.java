package unsw.loopmania;
public class Slug extends BasicEnemy {
    
    private int attackPower = 3;
    private int hp = 30;
    private int speed = 1;
    private int battleRadius = 1;
    private int supportRadius = 1;
    public Slug(PathPosition position) {
        super(position);
    }

    public int getHp() {
        return hp;
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

    public void move() {
        // First subtract 1 to the speed and check if the speed goes to 0 then it will move and set speed back to 0.
        // This is implemented this way in case the user may add items that may stun the enemy causing it not to move. In that case the speed to can be set to the amount of rounds the
        // enemy is stunned for.
        // This is also useful for implementing the speed for zombies since they are slow and require two ticks to move.
        speed -= 1;

        if (speed == 0) {
            super.move();
            speed = 1;
        } 
    }

    public void attack(MovingEntity movingEntity) {
        movingEntity.takeDamage(attackPower);
    }

    public void takeDamage(int damage) {
        hp -= damage;
    }

}
