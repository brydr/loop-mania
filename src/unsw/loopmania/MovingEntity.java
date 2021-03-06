package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

/**
 * The moving entity
 */
public abstract class MovingEntity extends Entity {

    /**
     * object holding position in the path
     */
    private PathPosition position;
    public IntegerProperty hp;
    private int speed;

    /**
     * Create a moving entity which moves up and down the path in position
     * @param position represents the current position in the path
     */
    public MovingEntity(PathPosition position) {
        super();
        this.position = position;
        this.hp = new SimpleIntegerProperty();
    }

    /**
     * move clockwise through the path
     */
    public void moveDownPath() {
        position.moveDownPath();
    }

    /**
     * move anticlockwise through the path
     */
    public void moveUpPath() {
        position.moveUpPath();
    }

    public SimpleIntegerProperty x() {
        return position.getX();
    }

    public SimpleIntegerProperty y() {
        return position.getY();
    }

    public int getX() {
        return x().get();
    }

    public int getY() {
        return y().get();
    }

    public int getHp() {
        return hp.get();
    }

    public int getSpeed() {
        return speed;
    }

    public void setHp(int hp) {
        this.hp.setValue(hp);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public PathPosition getPosition() {
        return position;
    }

    public abstract void takeDamage(int damage);

}
