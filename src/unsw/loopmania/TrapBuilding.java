package unsw.loopmania;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;

public class TrapBuilding extends Building implements BuildingAttackers {
    public static final int TRAP_DAMAGE = 30;

    public TrapBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public String getImage() {
        String trapBuildingImage = "src/images/trap.png";
        return trapBuildingImage;
    }

    /**
     * Deal damage to any enemies on the Trap, potentially killing them.
    */
    @Override
    public void attackEnemy(BasicEnemy enemy) {
        if (enemy.getX() == getX() && enemy.getY() == getY()) {
            this.destroy();
            enemy.takeDamage(TRAP_DAMAGE);
        } 
    }
}
