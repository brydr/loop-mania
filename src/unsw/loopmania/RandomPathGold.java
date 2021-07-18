package unsw.loopmania;

public class RandomPathGold extends RandomPathLoot {

    public RandomPathGold(PathPosition position) {
        super(position);
    }

    public String getImage() {
        String goldImage = "src/images/gold_pile.png";
        return goldImage;
    }

    public int onPickUp() {
        return 0;
    }
    
}
