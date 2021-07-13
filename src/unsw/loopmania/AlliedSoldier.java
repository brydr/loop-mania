package unsw.loopmania;

import java.time.Duration;
import java.util.Optional;


public class AlliedSoldier extends MovingEntity {

    private Optional<BasicEnemy> oldEnemy;
    private Duration tranceTime;
    private int attackDamage = 8;

    // constructor for basic AlliedSoldier
    public AlliedSoldier(PathPosition pos, int hp) {
        super(pos);
        this.setHp(hp);
    }

    // constructor for when enemy is put in trance
    public AlliedSoldier(PathPosition pos, int hp, Duration tranceTime, Optional<BasicEnemy> oldEnemy) {
        super(pos);
        this.setHp(hp);
        this.tranceTime = tranceTime;
        this.oldEnemy = oldEnemy;
    }

    public Optional<BasicEnemy> getOldEnemy() {
        return oldEnemy;
    }

    /**
     * reduce hp of AlliedSoldier
     * @return true if dead, false if alive
     */
    public void takeDamage(int damage) {
        this.setHp(this.getHp() - Math.round(damage));
    }

    /**
     * update tranceTime
     * @return true if time is over (<=0), return false if still in trance
     */
    public Boolean isTranceOver(Duration timePassed) {
        tranceTime = this.tranceTime.minus(timePassed);
        if (tranceTime.isNegative() || tranceTime.isZero()) return true;
        else return false;
    }

    /**
     * @param enemy, enemy to be attacked
     * outputs damage to given enemy
     */
    public void attack(MovingEntity enemy) {
        enemy.takeDamage(this.attackDamage);
    }
}