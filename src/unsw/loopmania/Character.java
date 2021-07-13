package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    private Optional<Weapon> equippedWeapon;
    private Optional<Armour> equippedArmour;
    private Optional<Shield> equippedShield;
    private Optional<Helmet> equippedHelmet;
    private Optional<RareItem> equippedRareItem;
    private List<AlliedSoldier> listAlliedSoldiers = new ArrayList<AlliedSoldier>();
    //private weaponStrategy strategy????

    public Character(PathPosition position) {
        super(position);
        this.setHp(100);
    }

    public Optional<Weapon> getEquippedWeapon() {
        return equippedWeapon;
    }

    public void setEquippedWeapon(Optional<Weapon> equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public Optional<Armour> getEquippedArmour() {
        return equippedArmour;
    }

    public void setEquippedArmour(Optional<Armour> equippedArmour) {
        this.equippedArmour = equippedArmour;
    }

    public Optional<Shield> getEquippedShield() {
        return equippedShield;
    }

    public void setEquippedShield(Optional<Shield> equippedShield) {
        this.equippedShield = equippedShield;
    }

    public Optional<Helmet> getEquippedHelmet() {
        return equippedHelmet;
    }

    public void setEquippedHelmet(Optional<Helmet> equippedHelmet) {
        this.equippedHelmet = equippedHelmet;
    }

    public Optional<RareItem> getEquippedRareItem() {
        return equippedRareItem;
    }

    public void setEquippedRareItem(Optional<RareItem> equippedRareItem) {
        this.equippedRareItem = equippedRareItem;
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
     * reduce hp of character based on armour being warn
     * @return true if dead, false if alive
     * TODO call armour methods when they've been implemented
     */
    public void takeDamage(int damage) {
        Boolean hasArmour = false;
        Boolean hasHelmet = false;
        Boolean hasShield = false;
        Boolean thirtyPercentChance = new Random().nextInt(10) < 3;

        if (this.equippedArmour != null) hasArmour = true;
        if (this.equippedHelmet != null) hasHelmet = true;
        if (this.equippedShield != null) hasShield = true;

        if (hasArmour && hasHelmet) damage *= 0.35;
        else if (hasArmour) damage *= 0.5;
        else if (hasHelmet) damage *= 0.3;
        if (hasShield && thirtyPercentChance) damage = 0;
        int newHp = this.getHp() - damage;
        this.setHp(Math.round(newHp));
    }

    /**
     * @param enemy, enemy to be attacked
     * Observer pattern to make all allied soldiers attack the enemy
     * Calls damage from equippedWeapon
     * outputs damage to given enemy
     */
    public void attack(MovingEntity enemy) {
        // all allies attack enemy
        for (AlliedSoldier ally : listAlliedSoldiers) {
            ally.attack(enemy);
        }
        int outputDamage = this.equippedWeapon.attack(enemy);
        // reduce player damage by 15% if helmet equipped
        if (this.equippedHelmet != null) outputDamage *= 0.85;
        enemy.takeDamage(Math.floor(outputDamage));
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
}
