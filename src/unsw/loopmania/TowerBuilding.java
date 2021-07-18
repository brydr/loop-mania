package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class TowerBuilding extends Building implements BuildingAttackers {

    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public String getImage() {
        String towerBuildingImage = "src/images/tower.png";
        return towerBuildingImage;
    }
    
    public void attackEnemy(BasicEnemy enemy) {
        if (Math.pow((enemy.getX()-this.getX()), 2) +  Math.pow((enemy.getY()-this.getY()), 2) <= Math.pow(4, 2)) {
            enemy.takeDamage(5);
        } 
    }
}