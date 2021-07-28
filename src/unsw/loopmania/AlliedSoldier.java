package unsw.loopmania;

import java.time.Duration;


public class AlliedSoldier extends MovingEntity {

    private BasicEnemy oldEnemy = null;
    private Duration tranceTime = null;
    private static final int ATTACK_DAMAGE = 8;
    private static final int NEW_SOLDIER_HP = 50;

    // constructor for basic AlliedSoldier
    public AlliedSoldier(PathPosition position) {
        super(position);
        super.setHp(NEW_SOLDIER_HP);
    }

    // constructor for when enemy is put in trance
    public AlliedSoldier(PathPosition pos, Duration tranceTime, Enemy oldEnemy) {
        super(pos);
        this.setHp(oldEnemy.getHp());
        this.tranceTime = tranceTime;
        this.oldEnemy = (BasicEnemy) oldEnemy;
    }

    public BasicEnemy getOldEnemy() {
        return oldEnemy;
    }

    public boolean isTrancedEnemy() {
        return (oldEnemy != null) && (tranceTime != null);
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
    public boolean isTranceOver(Duration timePassed) {
        if (tranceTime == null)
            return false;
        tranceTime = this.tranceTime.minus(timePassed);
        if (tranceTime.isNegative() || tranceTime.isZero()) 
            return true;
        else
            return false;
    }

    /**
     * @param enemy, enemy to be attacked
     * outputs damage to given enemy
     */
    public void attack(Enemy enemy) {
        enemy.takeDamage(ATTACK_DAMAGE);
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

    public String getImage() {
        String alliedSoldierImage = "src/images/deep_elf_master_archer.png";
        return alliedSoldierImage;
    }
}
