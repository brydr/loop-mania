package unsw.loopmania;

public interface EnemySpawner {
    public boolean spawn(boolean isCycle);

    public BasicEnemy spawnEnemy(PathPosition pos);

}
