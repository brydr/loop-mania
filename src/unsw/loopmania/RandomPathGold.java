package unsw.loopmania;

public class RandomPathGold extends RandomPathLoot {

    public RandomPathGold(PathPosition position) {
        super(position);
    }

    public String getImage() {
        return "src/images/gold_pile.png";
    }

    public int onPickUp() {
        return 0;
    }
    
}
