package unsw.loopmania;

import java.time.Duration;
import java.util.Optional;


public class AlliedSoldier{

    private int hp;
    private Optional<BasicEnemy> oldEnemy;
    private Duration tranceTime;

    // constructor for basic AlliedSoldier
    public AlliedSoldier(int hp) {
        this.hp = hp;
    }

    // constructor for when enemy is put in trance
    public AlliedSoldier(int hp, Duration tranceTime, Optional<BasicEnemy> oldEnemy) {
        this.hp = hp;
        this.tranceTime = tranceTime;
        this.oldEnemy = oldEnemy;
    }

    public Optional<BasicEnemy> getOldEnemy() {
        return oldEnemy;
    }

    public int getHp() {
        return this.hp;
    }

    /**
     * reduce hp of AlliedSoldier
     * @return true if dead, false if alive
     */
    public Boolean takeDamage(int damage) {
        hp -= Math.round(damage);
        if (hp < 0) {
            hp = 0;
            return true;
        }
        return false;
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
}