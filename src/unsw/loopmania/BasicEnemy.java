package unsw.loopmania;

import java.time.Duration;
import java.util.Random;

/**
 * a basic form of enemy in the world
 */
public abstract class BasicEnemy extends Enemy {
    // TODO = modify this, and add additional forms of enemy

    private BasicEnemy convertedToEnemy;
    private Boolean inTrance;
    public BasicEnemy(PathPosition position) {
        super(position);
        convertedToEnemy = null;
        inTrance = false;
    }

    public BasicEnemy getConvertedToEnemy() {
        return convertedToEnemy;
    }

    public void setConvertedToEnemy(BasicEnemy e) {
        this.convertedToEnemy = e;
    }

    public Boolean getInTrance() {
        return inTrance;
    }

    public void setInTrance(Boolean inTrance) {
        this.inTrance = inTrance;
    }

    public AlliedSoldier convertToFriendly(Character character) {
        Random rand = new Random();
        int tranceTime = rand.nextInt(18) + 3;  // Random number between 3 and 20 inclusive.
        Duration tranceTimeDuration = Duration.ofSeconds(tranceTime);
        AlliedSoldier transformedSoldier = new AlliedSoldier(this.getPosition(), tranceTimeDuration, this);
        character.addAlliedSoldier(transformedSoldier);
        this.setInTrance(true); // Dont need this line since the weapon already sets the enemies trance to true but useful for one of the character tests.
        return transformedSoldier;
    }
    
    public abstract void attack(Character character);
    public abstract String getImage();
}
