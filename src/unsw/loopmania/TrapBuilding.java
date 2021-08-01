package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class TrapBuilding extends Building implements BuildingAttackers {
    public static final int TRAP_DAMAGE = 30;

    public TrapBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public String getImage() {
        return "src/images/trap.png";
    }

    /**
     * Deal damage to any enemies on the Trap, potentially killing them.
    */
    @Override
    public void attackEnemy(Enemy enemy) {
        if (enemy.getX() == getX() && enemy.getY() == getY()) {
            this.destroy();
            enemy.takeDamage(TRAP_DAMAGE);
        }
    }
}
