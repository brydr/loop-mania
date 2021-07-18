package unsw.loopmania;
public class RandomPathPot extends RandomPathLoot {

    public RandomPathPot(PathPosition position) {
        super(position);
    }
    public String getImage() {
        String potImage = "src/images/brilliant_blue_new.png";
        return potImage;
    }

    public int onPickUp() {
        return 1;
    }
}
