package unsw.loopmania;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;
import unsw.loopmania.util.CustomCollectors;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */
public class LoopManiaWorld {
    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;
    public static final int numBasicItems = 7;

    // This is bad but it's necessary for added items to appear in the front end
    private LoopManiaWorldController controller;
    /**
     * width of the world in GridPane cells
     */
    private int width;

    /**
     * height of the world in GridPane cells
     */
    private int height;

    /**
     * Boolean whether or not a boss is on the world.
     */
    private boolean boss;
    private boolean bossAlreadySpawned;

    /**
     * generic entitites - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

    private Character character;


    // TODO = add more lists for other entities, for equipped inventory items, etc...
    private List<Entity> equippedInventoryItems;

    private List<Enemy> enemies;


    private List<Card> cardEntities;

    // The unequipped inventory of the character
    // This shouldn't be added to directly, call addUnequippedItem() instead
    private List<Item> unequippedInventoryItems;

    private List<Building> buildingEntities;

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    private PathPosition firstPath;

    private JSONObject worldGoals;

    public BooleanProperty goalComplete;

    private String goalsToComplete;

    private boolean nextCycle;

    private List<RandomPathLoot> worldPathLoot;

    private JSONArray worldRareItems;

    // Market for doggie coin
    private DoggieCoinMarket doggieCoinMarket = new DoggieCoinMarket();

    /**
     * create the world (constructor)
     *
     * @param width width of world in number of cells
     * @param height height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        this.width = width;
        this.height = height;
        nonSpecifiedEntities = new ArrayList<>();
        character = null;
        enemies = new ArrayList<>();
        boss = false;
        bossAlreadySpawned = false;

        cardEntities = new ArrayList<>();
        unequippedInventoryItems = new ArrayList<>();
        equippedInventoryItems = new ArrayList<>();
        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
        firstPath = null;
        nextCycle = false;
        worldPathLoot = new ArrayList<>();
        goalComplete = new SimpleBooleanProperty(false);
    }

    public void setController(LoopManiaWorldController controller) {
        this.controller = controller;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public DoggieCoinMarket getDoggieCoinMarket() {
        return doggieCoinMarket;
    }

    // getEnemies and addEnemies is used for testing.
    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void addEnemies(Enemy e) {
        enemies.add(e);
    }

    // for testing
    public List<Building> getBuildings() {
        return buildingEntities;
    }
    public void addBuilding(Building newBuilding) {
        buildingEntities.add(newBuilding);
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public void setGoals(JSONObject goals) {
        worldGoals = goals;
    }

    public void setRareItems(JSONArray rareItems) {
        worldRareItems = rareItems;
    }

    public JSONArray getRareItems() {
        return worldRareItems;
    }

    /**
     * add a generic entity (without it's own dedicated method for adding to the world)
     * @param entity
     */
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        // TODO = if more specialised types being added from main menu, add more methods like this with specific input types...
        nonSpecifiedEntities.add(entity);
    }

    /**
     * spawns enemies if the conditions warrant it, adds to world
     * @return list of the enemies to be displayed on screen
     */
    public List<BasicEnemy> possiblySpawnEnemies(){
        // TODO = expand this very basic version
        Pair<Integer, Integer> pos = possiblyGetBasicEnemySpawnPosition();
        List<BasicEnemy> spawningEnemies = new ArrayList<>();
        if (pos != null){
            int indexInPath = orderedPath.indexOf(pos);
            Slug slug = new Slug(new PathPosition(indexInPath, orderedPath));
            enemies.add(slug);
            spawningEnemies.add(slug);
        }
        return spawningEnemies;
    }

    /**
     * spawns boss enemies if the conditions warrant it, adds to world
     * @return list of the boss enemies to be displayed on screen
     */
    public List<BossEnemy> possiblySpawnBossEnemies(){
        // TODO = expand this very basic version
        Pair<Integer, Integer> pos = possiblyGetBossEnemySpawnPosition();
        List<BossEnemy> spawningEnemies = new ArrayList<>();
        if (pos != null && bossAlreadySpawned == false && boss == false){
            int indexInPath = orderedPath.indexOf(pos);
            if (character.getCycles() == 20) {
                Doggie doggie = new Doggie(new PathPosition(indexInPath, orderedPath), doggieCoinMarket);
                enemies.add(doggie);
                spawningEnemies.add(doggie);
                bossAlreadySpawned = true;
                boss = true;
            } else if (character.getCycles() == 40 && character.getExperience() >= 10000) {
                ElanMuske elan = new ElanMuske(new PathPosition(indexInPath, orderedPath));
                enemies.add(elan);
                spawningEnemies.add(elan);
                bossAlreadySpawned = true;
                boss = true;
                doggieCoinMarket.setElanAlive(true);
            }
        }
        return spawningEnemies;
    }

    /**
     * Drops random path loot at a 1% chance for each tile.
     * @return list of loot that will be dropped.
     */
    public List<RandomPathLoot> possiblyDropPathLoot(){
        List<RandomPathLoot> droppingPathLoot = new ArrayList<>();

        int orderedPathSize = orderedPath.size();

        if (!(nextCycle)) {
            return droppingPathLoot;
        }

        int i = 0;
        // For random gold drops.
        while (i < orderedPathSize) {
            if (new Random().nextInt(100) == 99) {   // If a random number between 0 and 99 inclusive is equal to 99. 1% chance.
                RandomPathGold randPathGold = new RandomPathGold(new PathPosition(i, orderedPath));
                droppingPathLoot.add(randPathGold);
                worldPathLoot.add(randPathGold);
            }
            i++;
        }

        // For random pot drops.
        i = 0;
        while (i < orderedPathSize) {
            if (new Random().nextInt(100) == 99) {   // If a random number between 0 and 99 inclusive is equal to 99. 1% chance.
                RandomPathPot randPathPot = new RandomPathPot(new PathPosition(i, orderedPath));
                droppingPathLoot.add(randPathPot);
                worldPathLoot.add(randPathPot);
            }
            i++;
        }

        return droppingPathLoot;
    }

    /**
     * kill an enemy
     * @param enemy enemy to be killed
     */
    private void killEnemy(Enemy enemy){
        enemy.destroy();
        enemies.remove(enemy);
    }

    /**
     * Check if the character is on top of any randomly spawned path loot.
     * @return list of path loot that got picked up.
     */
    public List<RandomPathLoot> pickUpLoot() {
        List<RandomPathLoot> pickedUp = new ArrayList<RandomPathLoot>();

        for (RandomPathLoot randPathLoot : worldPathLoot) {
            // If the character's position is directly on top of a path loot.
            if (Math.pow((character.getX()-randPathLoot.getX()), 2) +  Math.pow((character.getY()-randPathLoot.getY()), 2) == 0) {
                pickedUp.add(randPathLoot);
            }
        }

        for (RandomPathLoot lootPickedUp : pickedUp){
            lootPickedUp.destroy();
            worldPathLoot.remove(lootPickedUp);
        }

        return pickedUp;
    }

    /**
     * run the expected battles in the world, based on current world state
     * @return list of enemies which have been killed
     */
    public List<Enemy> runBattles() {
        // TODO = Refactor - this is a nightmare to read - break into smaller private functions and use shorter lines of code
        // TODO = modify this - currently the character automatically wins all battles without any damage!
        List<Enemy> defeatedEnemies = new ArrayList<Enemy>();

        List<Enemy> enemiesInRange = getEnemyInRange();

        int i = 0;
        // An array that will store all enemies that have turned into allied soldiers.
        List<AlliedSoldier> transformedEnemies = new ArrayList<AlliedSoldier>();

        // End the while if there are no enemies left or the only enemies left are transformedEnemies.
        while (enemiesInRange.size() > 0) {
            Enemy e = enemiesInRange.get(i);
            int enemyHealth = e.getHp();
            int charHealth = character.getHp();

            while (charHealth > 0 && enemyHealth > 0 && !(e.getInTrance())) {
                // If the enemy can run away then break out of the while loop.
                if (e.getRunAway()) {
                    int runAwayChance = (new Random()).nextInt(10);
                    if (runAwayChance < 3) {
                        e.setRunAway(false);
                    }
                    break;
                }
                // For every rare item equipped, do its effect.
                for (RareItem rareItems : character.getListRareItems()) {
                    rareItems.effect(character, e);
                }

                character.attack(e);    // character.attack(e) also makes all allies of it attack too.

                // Add logic so the tower attacks too if in range.
                for (Building building : buildingEntities) {
                    if (building instanceof TowerBuilding) {
                        BuildingAttackers buildingAttacker = (BuildingAttackers) building;
                        buildingAttacker.attackEnemy(e);
                    }
                }
                e.attack(character);    // Attacks allies first.

                // If the enemy is a boss, then apply its healEnemies method which elan muske uses which heals the list of enemies passed in.
                if (e instanceof BossEnemy) {
                    BossEnemy boss = (BossEnemy) e;
                    boss.healEnemies(enemiesInRange);
                }
                // If a zombie critical bite occurs and an allied soldier got transformed then it should be added to the enemies.
                if (e.getConvertedToEnemy() != null) {
                    enemiesInRange.add(e.getConvertedToEnemy());
                    e.setConvertedToEnemy(null);
                }
                charHealth = character.getHp();
                enemyHealth = e.getHp();

                // Only do the effect for the one ring.
                RareItem theOneRing = character.getEquippedRareItem();
                if (theOneRing != null) {
                    theOneRing.effect(character, e);
                }

                // An array that will store all allies that need to be removed from the transformedEnemies array due to converting back into an enemy.
                List<AlliedSoldier> convertBackAlliedSoldier = new ArrayList<AlliedSoldier>();

                for (AlliedSoldier transedEnemy : transformedEnemies) {
                    // Check if the transed time is now equal to 0.
                    // If it is then add the allied soldier into the convertBackAlliedSoldier array.
                    if (transedEnemy.isTranceOver(Duration.ofSeconds(1))) {
                        convertBackAlliedSoldier.add(transedEnemy);
                    }
                }

                // Loop through the convertBackAlliedSoldier to check for any allied soldiers that are converting back into an enemy.
                for (AlliedSoldier convertBackAllied : convertBackAlliedSoldier) {
                    BasicEnemy oldEnemy = character.convertBackToEnemy(convertBackAllied);
                    enemiesInRange.add(oldEnemy);   // Add the transformed ally into the enemiesInRange array.
                    transformedEnemies.remove(convertBackAllied);   // Remove the ally from transformedEnemies since it has now transformed back into an enemy.
                }
            }

            if (charHealth <= 0) {
                // Character is dead so game over.
                return defeatedEnemies;
            }

            if (enemyHealth <= 0) {
                defeatedEnemies.add(e);
                enemiesInRange.remove(e);   // Remove the enemy from enemies that are in range.
            // If an enemy did not die it means it was put in trance or Elan ran away.
            } else {
                // Adds the new allied soldier into the characters array of allied soldiers.
                AlliedSoldier transformedSoldier = e.convertToFriendly(character);
                if (transformedSoldier != null) {
                    transformedEnemies.add(transformedSoldier); // Add the transformed enemy into the transformedEnemy array which holds the allied soldier.
                    enemiesInRange.remove(e);   // Remove the enemy from enemies that are in range.
                    i--;    // Subtract 1 from i so that the index remains the same when i gets added with 1. This is so that it doesnt skip an enemy since an enemy got removed.
                // If it is null, it means it was Elan who ran away and so break out of the while loop since the character never killed it.
                } else {
                    if (enemiesInRange.size() == 1) {
                        break;
                    }
                }
            }

            i++;
            // If it goes past the maximum index then set i back to 0.
            if (i >= enemiesInRange.size()) {
                i = 0;
            }
        }

        // If the fight ends whilst the enemy is in a trance, the enemy dies.
        for (AlliedSoldier transedEnemy : transformedEnemies) {
            transedEnemy.reactivateOldEnemy();
            character.removeAlliedSoldier(transedEnemy);    // Remove the ally from the character ally list.
            BasicEnemy oldEnemy = transedEnemy.getOldEnemy();
            defeatedEnemies.add(oldEnemy);
        }

        for (Enemy e: defeatedEnemies){
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from the enemies list
            // if we killEnemy in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
            if (e instanceof BossEnemy) {
                // Set boss = false meaning that there is no boss in the world.
                boss = false;
                character.addBossKilled();
                if (e instanceof ElanMuske) {
                    doggieCoinMarket.setElanAlive(false);
                }
            }
            killEnemy(e);
        }
        return defeatedEnemies;
    }

    /**
     * spawn a card in the world and return the card entity
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public Card loadRandomCard(){
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()){
            // Cards full case
            // TODO may have to edit payout based on what is being deleted
            if (new Random().nextDouble() < 1. / 3) {
                loadRandomBasicItem();
            } else {
                payoutGoldOrXp();
            }
            removeCard(0);
        }

        // TODO = Make RandomCardGenerator an instance variable to improve performance
        Card randCard = new RandomCardGenerator().nextCard(cardEntities.size(), 0);

        cardEntities.add(randCard);
        return randCard;
    }

    public void loadRandomBasicItem() {
        // TODO = Make RandomCardGenerator an instance variable to improve performance
        // 0, 0 is fine as addUnequippedItem() sets them correctly later
        addUnequippedItem(new RandomItemGenerator().nextBasicItem(0, 0));
    }


    /**
     * remove card at a particular index of cards (position in gridpane of unplayed cards)
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index){
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

    /**
     * spawn a sword in the world and return the sword entity
     * @return a sword to be spawned in the controller as a JavaFX node
     */
    private Pair<Integer, Integer> getFirstSlotRemoveIfFull() {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            // give some cash/experience rewards for the discarding of the oldest sword
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        return firstAvailableSlot;
    }

    /**
     * Adds an item to the character's unequipped inventory in both the frontend and backend.
     * Handles removing an item and adding gold and XP when the inventory is full.
     * Overrides the item's X and Y to the next free inventory slot.
     * This function calls onLoad() in the controller.
     *
     * @param item The item to be added
     * @return an item to be spawned in the controller as a JavaFX node
     */
    public void addUnequippedItem(Item item) {
        Pair<Integer, Integer> firstAvailableSlot = getFirstSlotRemoveIfFull();
        item.setX(new SimpleIntegerProperty(firstAvailableSlot.getValue0()));
        item.setY(new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        unequippedInventoryItems.add(item);
        controller.onLoad(item);
    }


    /**
     * remove an item by x,y coordinates
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y){
        Item item = getUnequippedItemTypeByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
    }

/**
     * run moves which occur with every tick without needing to spawn anything immediately
     * @return Whether the character is back at the castle
     */
    public boolean runTickMoves(){
        nextCycle = false;

        // Initialise firstPath
        if (firstPath == null) {
            final PathPosition charPos = character.getPosition();
            firstPath = new PathPosition(charPos.getCurrentPositionInPath(), charPos.getOrderedPath());
        }

        GoalNode finalGoal = GoalEvaluator.evaluateGoals(worldGoals, character);

        if (GoalEvaluator.evaluate(finalGoal) == true) {
            // Character achieved all goals
            goalComplete.setValue(true);
        }
        character.moveDownPath();
        character.setAlliedSoldierNum();

        if (character.getX() == firstPath.getX().get() && character.getY() == firstPath.getY().get()) {
            character.addCycles();
            nextCycle = true;
            // Set bossAlreadySpawned to false meaning since it is a new cycle, a boss has not spawned.
            bossAlreadySpawned = false;
        }

        List<Building> buildingToRemove = new ArrayList<>();
        // Do all functionality of helper buildings.
        for (Building building : buildingEntities) {
            // If the building is a helperBuilding then call its helpChar function.
            if (building instanceof BuildingHelpers) {
                BuildingHelpers buildingHelper = (BuildingHelpers) building;
                buildingHelper.helpChar(character);
            // If the building is a spawner then call its spawn function.
            } else if (building instanceof EnemySpawner) {
                EnemySpawner enemySpawner = (EnemySpawner) building;
                if (enemySpawner.spawn(nextCycle)) {
                    var orderedPath = getOrderedPath();
                    Pair<Integer, Integer> spawnLoc = getNearestPathTile(building.getX(), building.getY());
                    int buildingPosIndex = orderedPath.indexOf(spawnLoc);

                    PathPosition pos = new PathPosition(buildingPosIndex, orderedPath);

                    BasicEnemy enemy = enemySpawner.spawnEnemy(pos);

                    enemies.add(enemy);
                }
            } else if (building instanceof TrapBuilding) {
                BuildingAttackers buildingAttacker = (BuildingAttackers) building;
                // Loop through all enemies and make the trap deal damage to them.
                List<Enemy> killedEnemies = new ArrayList<>();
                for (Enemy e: enemies) {
                    buildingAttacker.attackEnemy(e);
                    if (e.getHp() <= 0) {
                        killedEnemies.add(e);
                    }
                    if (!building.shouldExist().get()) {
                        buildingToRemove.add(building);
                    }
                }

                for (Enemy e: killedEnemies) {
                    killEnemy(e);
                }
            }
        }
        for (Building buildingRemove : buildingToRemove) {
            buildingEntities.remove(buildingRemove);
        }
        moveBasicEnemies();
        doggieCoinMarket.tickPrice();

        return nextCycle;
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    public void removeUnequippedInventoryItem(Entity item){
        item.destroy();
        unequippedInventoryItems.remove(item);
    }

    public Entity getEquippedInventoryItemEntityByCoordinates(int x, int y){
        for (Entity e: equippedInventoryItems){
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
        // else - no Entity found
        return null;
    }

    private void removeEquippedInventoryItem(Entity item){
        item.destroy();
        equippedInventoryItems.remove(item);
    }

    /**
     * remove item at a particular index in the unequipped inventory items list (this is ordered based on age in the starter code)
     * @param index index from 0 to length-1
     */
    public void removeItemByPositionInUnequippedInventoryItems(int index){
        Entity item = unequippedInventoryItems.get(index);
        item.destroy();
        unequippedInventoryItems.remove(index);
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the unequipped inventory
     * @return x,y coordinate pair
     */
    private Pair<Integer, Integer> getFirstAvailableSlotForItem(){
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available slot defined by looking row by row
        for (int y = 0; y < unequippedInventoryHeight; y++){
            for (int x = 0; x < unequippedInventoryWidth; x++){
                if (getUnequippedItemTypeByCoordinates(x, y) == null){
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

    /**
     * shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x){
        for (Card c : cardEntities){
            if (c.getX() >= x){
                c.x().set(c.getX()-1);
            }
        }
    }

    /**
     * move all enemies
     */
    private void moveBasicEnemies() {
        // TODO = expand to more types of enemy
        for (Enemy e : enemies){
            e.move();
        }
    }

    /**
     * get a randomly generated position which could be used to spawn an enemy
     * @return null if random choice is that wont be spawning an enemy or it isn't possible, or random coordinate pair if should go ahead
     */
    private Pair<Integer, Integer> possiblyGetBasicEnemySpawnPosition(){
        // TODO = modify this

        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();
        int choice = rand.nextInt(2); // TODO = change based on spec... currently low value for dev purposes...
        // TODO = change based on spec
        if ((choice == 0) && (enemies.size() < 2)){
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
            int endNotAllowed = (indexPosition + 3)%orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        } else {
            return null;
        }
    }

    /**
     * get a randomly generated position which could be used to spawn an enemy
     * @return null if random choice is that wont be spawning an enemy or it isn't possible, or random coordinate pair if should go ahead
     */
    private Pair<Integer, Integer> possiblyGetBossEnemySpawnPosition(){
        // TODO = modify this

        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)...
        Random rand = new Random();

        if (boss == false) {
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
            int endNotAllowed = (indexPosition + 3)%orderedPath.size();
            // note terminating condition has to be != rather than < since wrap around...
            for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }
            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));
            return spawnPosition;
        } else {
            return null;
        }


    }

    /**
     * Gets the TileType (either a PathTile, PathAdjacentTile or NonPathTile) of the tile with co-ordinates (x, y).
     * @param x x-coordinate of our subject tile
     * @param y y-coordinate of our subject tile
     * @return The correct {@code TileType}
     */
    private TileType getTileType(final int x, final int y) {
        // TODO = See if this needs to become a checked/recoverable error
        assert (x < width) && (y < height);

        final var subjectTile = Pair.with(x, y);

        // See if path matches
        boolean isPathTile = orderedPath.parallelStream()
            .anyMatch(tile -> tile.equals(subjectTile));
        if (isPathTile)
            return TileType.PathTile;

        // Precondition is that tile != subjectTile
        Predicate<Pair<Integer,Integer>> isAdjacentTo
        = tile -> {
            int tileX = tile.getValue0().intValue();
            int tileY = tile.getValue1().intValue();
            return (x - 1 <= tileX) && (tileX <= x + 1)
                && (y - 1 <= tileY) && (tileY <= y + 1);
        };
        boolean isAdjacentTile = orderedPath.parallelStream()
            .anyMatch(isAdjacentTo);
        if (isAdjacentTile)
            return TileType.PathAdjacentTile;
        else
            return TileType.NonPathTile;
    }


    /**
     * Remove a card by its x, y coordinates
     * @param cardNodeX x index from 0 to width-1 of card to be removed
     * @param cardNodeY y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     * @return {@code Building} the building if successfully created, OR {@code null} if otherwise
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        // Other ideas: https://stackoverflow.com/questions/22694884/filter-java-stream-to-1-and-only-1-element
        Card card = cardEntities.stream()
            .filter(c -> (c.getX() == cardNodeX) && (c.getY() == cardNodeY))
            .collect(CustomCollectors.toSingleton());

        // Building CANNOT be spawned here IF (tile is incorrect type) OR (tile is the firstPath)
        if (!card.canSpawnOnTile( getTileType(buildingNodeX, buildingNodeY) )
            || (   buildingNodeX == firstPath.getX().getValue()
                && buildingNodeY == firstPath.getY().getValue() )) {
            // TODO = Change interface to use an `Exception` or `Optional<T>` instead
            return null;
        };

        // Now spawn building
        Building newBuilding = card.createBuilding(new SimpleIntegerProperty(buildingNodeX),
                                                   new SimpleIntegerProperty(buildingNodeY));
        buildingEntities.add(newBuilding);

        // Destroy the card
        card.destroy();
        cardEntities.remove(card);
        shiftCardsDownFromXCoordinate(cardNodeX);
        return newBuilding;
    }

    /**
     * remove a card by its x, y coordinates
     * @param itemNodeX x index from 0 to width-1 of card to be removed
     * @param itemNodeY y index from 0 to height-1 of card to be removed
     * @return item in location in unequipped inventory
     */
    public Item getUnequippedItemTypeByCoordinates(int itemNodeX, int itemNodeY) {
        // Start by getting card
        Item item = null;
        for (Item i: unequippedInventoryItems){
            if (i.getX() == itemNodeX && i.getY() == itemNodeY){
                item = i;
                break;
            }
        }
        return item;
    }

    public List<Card> getCards() {
        return cardEntities;
    }

    List<Pair<Integer,Integer>> getOrderedPath() {
        return orderedPath;
    }

    /**
     * For a *path adjacent* tile, return PathTile that is adjacent.
     * @param x x-coordinate of our path-adjacent tile
     * @param y y-coordinate of our path-adjacent tile
     * @throws NoSuchElementException if no adjacent Path tile.
     * @return A {@code Pair} containing the nearest tile on the path.
     */
    public Pair<Integer, Integer> getNearestPathTile(int x, int y) {
        // Precondition is that tile is adjacent
        return orderedPath.parallelStream()
            .filter(tile -> {
                int xTile = tile.getValue0();
                int yTile = tile.getValue1();
                return x - 1 <= xTile && xTile <= x + 1
                    && y - 1 <= yTile && yTile <= y + 1;
            })
            .findAny()
            .orElseThrow();
    }

    public List<Item> getInventory() {
        return this.unequippedInventoryItems;
    }

    public void payoutGoldOrXp() {
        if (new Random().nextDouble() < .5) {
            // Character gets gold
            character.addGold(new Random().nextInt(91) + 10);

        } else {
            // Character gets XP
            character.addExperience(new Random().nextInt(21) + 10);
        }
    }

    /**
     * Adds a new card to the world. Currently used for tests.
     * @param newCard
     */
    public void addCard(Card newCard) {
        cardEntities.add(newCard);
    }

    public BooleanProperty goalProperty() {
        BooleanProperty charGoalComplete = this.goalComplete;
        return charGoalComplete;
    }

    public String getGoalString() {
        return goalsToComplete;
    }

    public JSONObject getWorldGoals() {
        return worldGoals;
    }

    public List<Enemy> getEnemyInRange() {
        List<Enemy> enemiesInRange = new ArrayList<Enemy>();
        boolean alreadyABattle = false;

        Enemy battledEnemy = null;

        for (Enemy e: enemies){
            // Pythagoras: a^2+b^2 < radius^2 to see if within radius
            // TODO = you should implement different RHS on this inequality, based on influence radii and battle radii
            // If an enemy is in range add it into the enemiesInRange array. This enemy will be the enemy the character is battling.
            if (Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) <= Math.pow(e.getBattleRadius(), 2)){
                enemiesInRange.add(e);
                battledEnemy = e;
                alreadyABattle = true;
                break;
            }
        }

        for (Enemy e: enemies) {
            // Check if there is an enemy that it is battling since supports only help enemies that are battling.
            if (alreadyABattle == false) {
                break;
            // Check that the enemy is not the enemy the character is battling to avoid adding it into the enemiesInRange array again.
            // These enemies added will be the enemies supporting.
            } else if (battledEnemy != e && Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) <= Math.pow(e.getSupportRadius(), 2)) {
                enemiesInRange.add(e);
            }
        }

        return enemiesInRange;
    }

}
