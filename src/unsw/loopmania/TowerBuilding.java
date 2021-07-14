package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class TowerBuilding extends Building {

    public TowerBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) throws IncorrectTileException {
        super(x, y);
    }
    
}