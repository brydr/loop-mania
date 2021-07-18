package unsw.loopmania;

public interface EnemySpawner {
    /**
     * Calls the "spawn" action by using the LoopManiaWorld API. Note that this may not necessarily
     * cause a moving entity to spawn (e.g. {@code VampireCastleBuilding} should only spawn an entity 
     * for every 5th call of this function).
     * @param world A reference to our {@code LoopManiaWorld}
     */
    public void spawn(LoopManiaWorld world);

}
