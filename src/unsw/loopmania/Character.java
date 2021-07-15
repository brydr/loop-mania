package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    //TODO update unarmed xy coord arguments
    private WeaponStrategy equippedWeapon = new Unarmed(0,0);
    private Armour equippedArmour;
    private Shield equippedShield;
    private Helmet equippedHelmet;
    private RareItem equippedRareItem;
    private List<AlliedSoldier> listAlliedSoldiers = new ArrayList<AlliedSoldier>();

    public Character(PathPosition position) {
        super(position);
        this.setHp(100);
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public Armour getEquippedArmour() {
        return equippedArmour;
    }

    public void setEquippedArmour(Armour equippedArmour) {
        this.equippedArmour = equippedArmour;
    }

    public Shield getEquippedShield() {
        return equippedShield;
    }

    public void setEquippedShield(Shield equippedShield) {
        this.equippedShield = equippedShield;
    }

    public Helmet getEquippedHelmet() {
        return equippedHelmet;
    }

    public void setEquippedHelmet(Helmet equippedHelmet) {
        this.equippedHelmet = equippedHelmet;
    }

    public RareItem getEquippedRareItem() {
        return equippedRareItem;
    }

    public void setEquippedRareItem(RareItem equippedRareItem) {
        this.equippedRareItem = equippedRareItem;
    }
    
    public void removeEquippedArmour() {
        this.equippedArmour = null;
    }

    public void removeEquippedShield() {
        this.equippedShield = null;
    }

    public void removeEquippedHelmet() {
        this.equippedHelmet = null;
    }

    public void removeEquippedRareItem() {
        this.equippedRareItem = null;
    }

    public List<AlliedSoldier> getListAlliedSoldiers() {
        return listAlliedSoldiers;
    }

    public void addAlliedSoldier(AlliedSoldier soldier) {
        this.listAlliedSoldiers.add(soldier);
    }

    public void removeAlliedSoldier(AlliedSoldier soldier) {
        this.listAlliedSoldiers.remove(soldier);
    }

    /**
     * attack allied soldier first otherwise
     * reduce hp of character based on armour equipped
     * @param damage, raw damage coming from enemy
     */
    public void takeDamage(int damage) {
        if (!listAlliedSoldiers.isEmpty()) {
            AlliedSoldier ally = listAlliedSoldiers.get(0);
            ally.takeDamage(damage);
            if (ally.getHp() <= 0) {
                removeAlliedSoldier(ally);
                return;
            }
        }
        if (this.equippedArmour != null) 
            damage = equippedArmour.calculateDamage(damage);
        if (this.equippedHelmet != null) 
            damage = equippedHelmet.calculateDamage(damage);
        if (this.equippedShield != null) 
            damage = equippedShield.calculateDamage(damage);

        int newHp = this.getHp() - damage;
        this.setHp(newHp);
    }

    /**
     * @param enemy, enemy to be attacked
     * Observer pattern to make all allied soldiers attack the enemy
     * Calls damage from equippedWeapon
     * outputs damage to given enemy
     */
    public void attack(BasicEnemy enemy) {
        int outputDamage = this.equippedWeapon.attack(enemy);
        // reduce player damage by 15% if helmet equipped
        if (this.equippedHelmet != null) 
            outputDamage = equippedHelmet.calculateAttack();
        enemy.takeDamage(outputDamage);

        // check enemy isn't in trance before allies attack
        if (!enemy.getInTrance()){
            // all allies attack enemy
            for (AlliedSoldier ally : listAlliedSoldiers) {
                ally.attack(enemy);
            }
        }
    }

    /**
     * Observer pattern
     * update AlliedSoldier positions
     */
    public void notifyObserversPosition(Boolean isDownPath) {
        for (AlliedSoldier ally : listAlliedSoldiers) {
            if (isDownPath) ally.moveDownPath();
            else ally.moveUpPath();
        }
    }

    /**
     * Move character and allied soldiers up path
     */
    @Override
    public void moveUpPath() {
        super.moveUpPath();
        Boolean isDownPath = false;
        notifyObserversPosition(isDownPath);
    }

    /**
     * Move character and allied soldiers down path
     */
    @Override
    public void moveDownPath() {
        super.moveDownPath();
        Boolean isDownPath = true;
        notifyObserversPosition(isDownPath);
    }

    /**
     * @param ally, ally to be converted back into an enemy
     */
    public BasicEnemy convertBackToEnemy(AlliedSoldier ally) {
        ally.reactivateOldEnemy();
        this.removeAlliedSoldier(ally);
        return ally.getOldEnemy();
    }

    /**
     * If zombie crit bite, it parses the ally it bit and a new zombie instance
     * @param ally, ally to be converted into an enemy
     * @param enemy, new enemy
     * @return enemy returned so it can be added to the list of enemies to attack in runBattles
     */
    public BasicEnemy convertToEnemy(AlliedSoldier ally, BasicEnemy enemy) {
        enemy.setHp(ally.getHp());
        this.removeAlliedSoldier(ally);
        return enemy;
    }
}
