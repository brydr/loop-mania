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
    public void attackEnemy(BasicEnemy enemy) {
        if (Math.pow((enemy.getX()-this.getX()), 2) +  Math.pow((enemy.getY()-this.getY()), 2) == 0) {
            this.destroy();
            enemy.takeDamage(TRAP_DAMAGE);
        } 
    }
}
