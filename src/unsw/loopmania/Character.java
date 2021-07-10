package unsw.loopmania;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {
    public Gold gold;
    
    // TODO = potentially implement relationships between this class and other classes
    public Character(PathPosition position, Gold gold) {
        super(position);
        this.gold = gold;
    }
    
}
