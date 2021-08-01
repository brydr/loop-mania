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
    private ProtectiveGear equippedShield;
    private Helmet equippedHelmet;
    private RareItem equippedRareItem;
    private List<RareItem> equippedRareItems;
    private List<AlliedSoldier> listAlliedSoldiers;
    private IntegerProperty experience;
    private IntegerProperty cycles;
    private IntegerProperty alliedSoldierNum;
    private Gold gold;
    private HealthPotion equippedHealthPotion;
    private final static int MAX_HP = 200;
    private int numAttack;
    private int stunned;
    private int bossesKilled;
    private boolean oneRingUsed;

    public Character(PathPosition position) {
        super(position);
        this.experience = new SimpleIntegerProperty();
        this.cycles = new SimpleIntegerProperty();
        this.alliedSoldierNum = new SimpleIntegerProperty();
        experience.setValue(0);
        cycles.setValue(0);
        alliedSoldierNum.setValue(0);
        this.setHp(MAX_HP);
        listAlliedSoldiers = new ArrayList<AlliedSoldier>();
        equippedRareItems = new ArrayList<RareItem>();
        gold = new Gold();
        equippedWeapon = new Unarmed();
        this.numAttack = 0;
        this.stunned = 0;
        this.oneRingUsed = false;
    }

    @Override
    public void setHp(int hp) {
        final int newHp = Integer.min(MAX_HP, hp);
        super.setHp(newHp);
    }

    /**
     * numAttack is the amount of times the character will attack extra for. 
     * For instance the campfire allows the character to attack twice since the campfire makes the character deal double damage.
     * @return
     */
    public int getNumAttack() {
        return numAttack;
    }

    public void setNumAttack(int numAttack) {
        this.numAttack = numAttack;
    }

    public boolean getOneRingUsed() {
        return oneRingUsed;
    }

    public void setOneRingUsed(boolean used) {
        this.oneRingUsed = used;
    }

    public int getStunned() {
        return stunned;
    }

    public void setStunned(int stunned) {
        this.stunned = stunned;
    }

    public int getBossesKilled() {
        return bossesKilled;
    }

    public void addBossKilled() {
        this.bossesKilled++;
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

    public ProtectiveGear getEquippedShield() {
        return equippedShield;
    }

    public void setEquippedShield(ProtectiveGear equippedShield) {
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

    public List<RareItem> getListRareItems() {
        return equippedRareItems;
    }

    public void addRareItem(RareItem rareItem) {
        this.equippedRareItems.add(rareItem);
    }

    public void removeRareItem(RareItem rareItem) {
        this.equippedRareItems.remove(rareItem);
    }

    public int getMaxHp() {
        return MAX_HP;
    }

    /**
     * If health potion is equipped, remove potion and reset HP to maximum
     * @return {@code true} If potion equipped & consumed successfully,
     * {@code false} otherwise.
     */
    public boolean consumePotion() {
        if (equippedHealthPotion != null) {
            this.setHp(MAX_HP);
            equippedHealthPotion = null;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Apply attack to any allied soldiers first, otherwise reduce HP of 
     * The Character by an amount depending on armour equipped
     * @param baseDamage Raw/base damage dealt by enemy
     */
    public void takeDamage(final int baseDamage) {
        if (listAlliedSoldiers.size() > 0) {
            // Use the first AlliedSoldier as cannon fodder
            AlliedSoldier ally = listAlliedSoldiers.get(0);
            ally.takeDamage(baseDamage);
            if (ally.getHp() <= 0)
                removeAlliedSoldier(ally);
            
        } else { /* no allied soldiers */
            int damage = baseDamage;
            // Transform the damage if "The Character" has protective gear
            if (this.equippedArmour != null)
                damage = equippedArmour.calculateDamage(damage);
            if (this.equippedHelmet != null)
                damage = equippedHelmet.calculateDamage(damage);
            if (this.equippedShield != null)
                damage = equippedShield.calculateDamage(damage);

            final int newHp = this.getHp() - damage;
            // TODO Check if The Character has been killed
            this.setHp(newHp);
        }
    }

    /** Attack a given enemy. Observer pattern applied to make all allied soldiers attack the enemy.  
     * Calls damage from equippedWeapon, outputs damage to given enemy.
     * @param enemy The enemy to be attacked
     */
    public void attack(Enemy enemy) {
        // If the character is currently stunned -1 to the stun duration and return.
        if (stunned > 0) {
            stunned -= 1;
            return;
        }

         // Get base damage according to equipped weapon
         final int baseDamage = equippedWeapon.getDamage(enemy);
         // Attack power is diminished by helmet
         final int outputDamage = (equippedHelmet != null)
             ? equippedHelmet.calculateDamage(baseDamage)
             : baseDamage;


         enemy.takeDamage(outputDamage);
         // Check if the character has extra attacks.
         while (numAttack > 0) {
            enemy.takeDamage(outputDamage);
            numAttack--;
        }
 
         // Check that enemy isn't in trance before allies attack
         if (!enemy.getInTrance()) {
             // All allies attack enemy
             for (AlliedSoldier ally : listAlliedSoldiers) {
                 ally.attack(enemy);
             }
         }
 
    }

    /**
     * Observer pattern.
     * Update AlliedSoldier positions
     * @param isDownPath Whether movement is down the path (vs. up the path)
     */
    public void notifyObserversPosition(boolean isDownPath) {
        for (AlliedSoldier ally : listAlliedSoldiers) {
            if (isDownPath)
                ally.moveDownPath();
            else
                ally.moveUpPath();
        }
    }

    /**
     * Move character and allied soldiers up path
     */
    public void moveUp() {
        moveUpPath();
        notifyObserversPosition(false);
    }

    /**
     * Move character and allied soldiers down path
     */
    public void moveDown() {
        moveDownPath();
        notifyObserversPosition(true);
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

    public void setAlliedSoldierNum() {
        alliedSoldierNum.setValue(listAlliedSoldiers.size());
    }
    public IntegerProperty alliedSoldierProperty() {
        IntegerProperty charalliedSoldierNum = this.alliedSoldierNum;
        return charalliedSoldierNum;
    }
    public String getImage() {
        return "src/images/human_new.png";
    }
}
