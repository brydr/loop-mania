package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class TowerBuilding extends Building implements BuildingAttackers {

    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public String getImage() {
        return "src/images/tower.png";
    }

    @Override
    public void attackEnemy(Enemy enemy) {
        final double sqrDistFromEnemy = Math.pow(enemy.getX() - getX(), 2) + 
                                        Math.pow(enemy.getY() - getY(), 2);
        if (sqrDistFromEnemy <= 4 * 4) {
            enemy.takeDamage(5);
        } 
    }
}