package unsw.loopmania;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;

public class TrapBuilding extends Building {
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
     * @param enemyList A list of all {@code BasicEnemy} in the world
     * @implNote A reference to {@code LoopManiaWorld} could alternatively be used, with world.killEnemy() used
     */
    public void damageAnyEnemies(List<BasicEnemy> enemyList) {
        for (BasicEnemy enemy : enemyList) {
            if (enemy.getX() == getX() && enemy.getY() == getY()) {
                enemy.takeDamage(TRAP_DAMAGE);
                // Enemy has been killed
                if (enemy.getHp() <= 0) {
                    enemy.destroy();
                    enemyList.remove(enemy);
                }
            }
        }
    }
}
