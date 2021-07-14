package unsw.loopmania;

import java.time.Duration;


public class AlliedSoldier extends MovingEntity {

    private BasicEnemy oldEnemy;
    private Duration tranceTime;
    private static final int attackDamage = 8;

    // constructor for basic AlliedSoldier
    public AlliedSoldier(PathPosition pos) {
        super(pos);
        int hp = 50;
        this.setHp(hp);
    }

    // constructor for when enemy is put in trance
    public AlliedSoldier(PathPosition pos, Duration tranceTime, BasicEnemy oldEnemy) {
        super(pos);
        this.setHp(oldEnemy.getHp());
        this.tranceTime = tranceTime;
        this.oldEnemy = oldEnemy;
    }

    public BasicEnemy getOldEnemy() {
        return oldEnemy;
    }

    /**
     * reduce hp of AlliedSoldier
     * @return true if dead, false if alive
     */
    public void takeDamage(int damage) {
        this.setHp(this.getHp() - damage);
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
    public void attack(BasicEnemy enemy) {
        enemy.takeDamage(attackDamage);
    }

    /**
     * reactivates oldEnemy with same health as this AlliedSoldier
     * TODO might need to cap health at a max in case returning back 
     * from trance gives the enemy extra health then before
     */
    public void reactivateOldEnemy() {
        oldEnemy.setInTrance(false);
        oldEnemy.setHp(this.getHp());
    }
}