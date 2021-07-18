package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
public abstract class RandomPathLoot extends Entity {

    private PathPosition position;
    public RandomPathLoot(PathPosition position) {
        super();
        this.position = position;
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

    public abstract String getImage();
    public abstract int onPickUp();
}
