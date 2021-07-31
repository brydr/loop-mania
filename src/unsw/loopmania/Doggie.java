package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;

import javafx.beans.property.SimpleIntegerProperty;

public class Doggie extends BossEnemy {
    private final DoggieCoinMarket market;

    public Doggie(PathPosition position, DoggieCoinMarket market) {
        super(position);
        this.setAttack(20);
        this.setHp(400);
        this.setSpeed(1);
        this.setBattleRadius(1);
        this.setSupportRadius(1);
        this.setExperienceGain(400);
        this.market = market;
    }

    @Override
    public void move() {
        // First subtract 1 to the speed and check if the speed goes to 0 then it will
        // move and set speed back to 0.
        // This is implemented this way in case the user may add items that may stun the
        // enemy causing it not to move. In that case the speed to can be set to the
        // amount of rounds the
        // enemy is stunned for.
        // This is also useful for implementing the speed for zombies since they are
        // slow and require two ticks to move.
        int speed = this.getSpeed();
        this.setSpeed(speed - 1);

        if (this.getSpeed() == 0) {
            super.move();
            this.setSpeed(1);
        }
    }

    @Override
    public void attack(Character character) {
        int stunChance = (new Random()).nextInt(10); // A random number between 0 and 9.
        int attackPower = this.getAttack();
        character.takeDamage(attackPower);
        // Stun the character at a 10% chance.
        if (stunChance == 0) {
            character.setStunned(3);
        }
        // Set attack back to 20 since the tree stump reduces it by 1/3.
        this.setAttack(20);
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

    // Used for testing.
    public void stunAttack(Character character) {
        int attackPower = this.getAttack();
        character.takeDamage(attackPower);
        character.setStunned(3);
    }

    @Override
    public String getImage() {
        String slugImage = "src/images/doggie.png";
        return slugImage;
    }
}
