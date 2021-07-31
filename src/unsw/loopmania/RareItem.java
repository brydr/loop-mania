package unsw.loopmania;

public interface RareItem {
    public boolean effect(Character character, Enemy enemy);

    public void addEffect(Character character, Enemy enemy);
}
