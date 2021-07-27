package unsw.loopmania;

public class Slug extends BasicEnemy {
    public static final int STARTING_HP = 30;
    public Slug(PathPosition position) {
        super(position);
        this.setAttack(3);
        this.setHp(STARTING_HP);
        this.setSpeed(1);
        this.setBattleRadius(1);
        this.setSupportRadius(1);
        this.setExperienceGain(10);
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
    public void setHp(int hp) {
        if (hp > STARTING_HP) {
            this.hp.setValue(STARTING_HP);
        } else {
            this.hp.setValue(hp);
        }
    }

    @Override
    public void attack(Character character) {
        int attackPower = this.getAttack();
        character.takeDamage(attackPower);
    }

    @Override
    public String getImage() {
        String slugImage = "src/images/slug.png";
        return slugImage;
    }
}
