package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    private WeaponStrategy equippedWeapon;
    private Armour equippedArmour;
    private Shield equippedShield;
    private Helmet equippedHelmet;
    private RareItem equippedRareItem;
    private List<AlliedSoldier> listAlliedSoldiers;
    private IntegerProperty experience;
    private IntegerProperty cycles;
    private IntegerProperty alliedSoldierNum;
    private Gold gold;
    private HealthPotion equippedHealthPotion;
    private final static int maxHp = 200;

    public Character(PathPosition position) {
        super(position);
        this.experience = new SimpleIntegerProperty();
        this.cycles = new SimpleIntegerProperty();
        this.alliedSoldierNum = new SimpleIntegerProperty();
        experience.setValue(0);
        cycles.setValue(0);
        alliedSoldierNum.setValue(0);
        this.setHp(maxHp);
        listAlliedSoldiers = new ArrayList<AlliedSoldier>();
        gold = new Gold();
        equippedWeapon = new Unarmed();
    }

    public WeaponStrategy getEquippedWeapon() {
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

    public HealthPotion getEquippedHealthPotion() {
        return equippedHealthPotion;
    }

    public void setEquippedHealthPotion(HealthPotion healthPotion) {
        this.equippedHealthPotion = healthPotion;
    }

    public int getCycles() {
        return cycles.get();
    }

    public int addCycles() {
        cycles.setValue(cycles.getValue() + 1);
        return cycles.get() + 1;
    }

    public int getExperience() {
        return experience.get();
    }

    public int addExperience(int exp) {
        experience.setValue(experience.getValue() + exp);
        return experience.get() + exp;
    }

    public int getGold() {
        return gold.getGold();
    }

    public IntegerProperty getGoldProperty() {
        return gold.getGoldProperty();
    }

    public void addGold(int gold) {
        this.gold.addGold(gold);
    }

    public void subtractGold(int gold) {
        this.gold.subtractGold(gold);
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
     * if health potion is equipped, remove potion and reset hp to max
     */
    public void consumePotion() {
        if (this.equippedHealthPotion != null) {
            this.equippedHealthPotion = null;
            this.setHp(maxHp);
        }
    }

    /**
     * attack allied soldier first otherwise
     * reduce hp of character based on armour equipped
     * @param damage, raw damage coming from enemy
     */
    public void takeDamage(int damage) {
        if (listAlliedSoldiers.size() > 0) {
            AlliedSoldier ally = listAlliedSoldiers.get(0);
            ally.takeDamage(damage);
            if (ally.getHp() <= 0) {
                removeAlliedSoldier(ally);
            }
            return;
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
        int outputDamage = this.equippedWeapon.getDamage(enemy);
        // reduce player damage by 15% if helmet equipped
        if (this.equippedHelmet != null)
            outputDamage = equippedHelmet.calculateDamage(outputDamage);
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
    public void moveUp() {
        moveUpPath();
        Boolean isDownPath = false;
        notifyObserversPosition(isDownPath);
    }

    /**
     * Move character and allied soldiers down path
     */
    public void moveDown() {
        System.out.println("test");
        moveDownPath();
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

    public IntegerProperty hpProperty() {
        IntegerProperty charHp = this.hp;
        return charHp;
    }
    public IntegerProperty goldProperty() {
        IntegerProperty charGold = this.getGoldProperty();
        return charGold;
    }
    public IntegerProperty expProperty() {
        IntegerProperty charExp = this.experience;
        return charExp;
    }
    public IntegerProperty cycleProperty() {
        IntegerProperty charCycle = this.cycles;
        return charCycle;
    }
    public IntegerProperty alliedSoldierProperty() {
        alliedSoldierNum.setValue(listAlliedSoldiers.size());
        IntegerProperty charalliedSoldierNum = this.alliedSoldierNum;
        return charalliedSoldierNum;
    }
    public String getImage() {
        String characterImage = "src/images/human_new.png";
        return characterImage;
    }
}
