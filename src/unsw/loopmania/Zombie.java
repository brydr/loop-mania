package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;

import javafx.beans.property.SimpleIntegerProperty;

public class Zombie extends BasicEnemy {
    private int criticalBiteChance;
    private final Random randomGenerator;

    public static final int STARTING_HP = 30;
    public Zombie(PathPosition position) {
        this(position, System.nanoTime());
    }

    // Used for testing.
    public Zombie(PathPosition position, long seed) {
        super(position, STARTING_HP);
        this.randomGenerator = new Random(seed);
        this.setAttack(6);
        // this.setHp(STARTING_HP);
        this.setSpeed(2);
        this.setBattleRadius(2);
        this.setSupportRadius(2);
        this.setExperienceGain(40);
    }

    @Override
    public void move() {
        // First subtract 1 to the speed and check if the speed goes to 0 then it will move and set speed back to 0.
        // This is implemented this way in case the user may add items that may stun the enemy causing it not to move.
        // In that case the speed to can be set to the amount of rounds the enemy is stunned for.
        // This is also useful for implementing the speed for zombies since they are slow and require two ticks to move.
        final int speed = this.getSpeed();
        this.setSpeed(speed - 1);

        if (this.getSpeed() == 0) {
            super.move();
            this.setSpeed(2);
        }
    }

    @Override
    public void attack(Character character) {
        criticalBiteChance = randomGenerator.nextInt(10);    // A random number between 0 and 9.
        // System.out.println("Chance is " + criticalBiteChance);
        int attackPower = this.getAttack();
        List<AlliedSoldier> alliedSoldiers = character.getListAlliedSoldiers();
        // If the character has allied soldiers it should attack them instead.
        if (alliedSoldiers.size() > 0) {
            AlliedSoldier firstSoldier = alliedSoldiers.get(0);
            firstSoldier.takeDamage(attackPower);
            if (firstSoldier.getHp() <= 0) {
                character.removeAlliedSoldier(firstSoldier);
            } else if (criticalBiteChance == 0) {
                Zombie newZombie = new Zombie(firstSoldier.getPosition());
                setConvertedToEnemy(character.convertToEnemy(firstSoldier, newZombie));
            }
        } else {
            character.takeDamage(attackPower);
        }
    }

    @Override
    public List<Item> dropLoot(JSONArray rareItems) {
        List<Item> loot = new ArrayList<Item>();
        int rareItemChance = new Random().nextInt(100); // A random value between 0 and 99 inclusive.
        if (rareItemChance < 3) {
            String item = rareItems.getString(new Random().nextInt(rareItems.length()));  // Selects a random item from the rare items list
            if (item.equals("the_one_ring")) {
                TheOneRing theOneRing = new TheOneRing(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                loot.add(theOneRing);
            } else if (item.equals("anduril_flame_of_the_west")) {
                AndurilFlameOfTheWest andurilFlameOfTheWest = new AndurilFlameOfTheWest(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                loot.add(andurilFlameOfTheWest);
            } else if (item.equals("tree_stump")) {
                TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
                loot.add(treeStump);
            }
        }
        return loot;
    }

    @Override
    public String getImage() {
        return "src/images/zombie.png";
    }

}
