package unsw.loopmania;

public class Gold extends Currency {
    public int amount;

    public Gold() {
        this.amount = 0;
    }
    
    public int getGold() {
        return amount;
    }

    public void addGold(int add) {
        amount = amount + add;
    }

    public void subtractGold(int sub) {
        amount = amount - sub;
    }

    public String getImage() {
        String goldImage = "src/images/gold_pile.png";
        return goldImage;
    }
}
