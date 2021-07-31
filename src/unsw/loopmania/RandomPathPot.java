package unsw.loopmania;
public class RandomPathPot extends RandomPathLoot {

    public RandomPathPot(PathPosition position) {
        super(position);
    }
    public String getImage() {
        return "src/images/brilliant_blue_new.png";
    }

    public int onPickUp() {
        return 1;
    }
}
