package unsw.loopmania;

/**
 * a boss form of enemy in the world
 */
public abstract class BossEnemy extends Enemy {
    public BossEnemy(PathPosition position, int startingHp) {
        super(position, startingHp);
    }

    @Override
    public boolean getInTrance() {
        // Bosses can't be tranced
        return false;
    }

    public abstract String getImage();
}
