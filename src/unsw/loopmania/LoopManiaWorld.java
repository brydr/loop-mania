package unsw.loopmania;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.javatuples.Pair;
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

    /**
     * width of the world in GridPane cells
     */
    private int width;

    /**
     * height of the world in GridPane cells
     */
    private int height;

    /**
     * generic entitites - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

    private Character character;

    // TODO = add more lists for other entities, for equipped inventory items, etc...
    private List<Entity> equippedInventoryItems;

    private List<BasicEnemy> enemies;

    private List<Card> cardEntities;

    private List<Item> unequippedInventoryItems;

    private List<Building> buildingEntities;

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    private PathPosition firstPath;

    private JSONObject worldGoals;

    private BooleanProperty goalComplete;

    private String goalsToComplete;

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
        cardEntities = new ArrayList<>();
        unequippedInventoryItems = new ArrayList<>();
        equippedInventoryItems = new ArrayList<>();
        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
        firstPath = null;
        goalComplete = new SimpleBooleanProperty(false);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // getEnemies and addEnemies is used for testing.
    public List<BasicEnemy> getEnemies() {
        return enemies;
    }

    public void addEnemies(BasicEnemy e) {
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
     * kill an enemy
     * @param enemy enemy to be killed
     */
    private void killEnemy(BasicEnemy enemy){
        enemy.destroy();
        enemies.remove(enemy);
    }

    /**
     * run the expected battles in the world, based on current world state
     * @return list of enemies which have been killed
     */
    public List<BasicEnemy> runBattles() {
        // TODO = modify this - currently the character automatically wins all battles without any damage!
        List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
        List<BasicEnemy> enemiesInRange = new ArrayList<BasicEnemy>();
        boolean alreadyABattle = false;

        BasicEnemy battledEnemy = null;
        for (BasicEnemy e: enemies){
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

        for (BasicEnemy e: enemies) {
            // Check if there is an enemy that it is battling since supports only help enemies that are battling.
            if (alreadyABattle == false) {
                break;
            // Check that the enemy is not the enemy the character is battling to avoid adding it into the enemiesInRange array again.
            // These enemies added will be the enemies supporting.
            } else if (battledEnemy != e && Math.pow((character.getX()-e.getX()), 2) +  Math.pow((character.getY()-e.getY()), 2) <= Math.pow(e.getSupportRadius(), 2)) {
                enemiesInRange.add(e);
            }
        }

        int i = 0;
        // An array that will store all enemies that have turned into allied soldiers.
        List<AlliedSoldier> transformedEnemies = new ArrayList<AlliedSoldier>();

        // End the while if there are no enemies left or the only enemies left are transformedEnemies.
        while (enemiesInRange.size() > 0) {
            BasicEnemy e = enemiesInRange.get(i);
            int enemyHealth = e.getHp();
            int charHealth = character.getHp();

            while (charHealth > 0 && enemyHealth > 0 && !(e.getInTrance())) {
                character.attack(e);    // character.attack(e) also makes all allies of it attack too.
                e.attack(character);    // Attacks allies first.
                // If a zombie critical bite occurs and an allied soldier got transformed then it should be added to the enemies.
                if (e.getConvertedToEnemy() != null) {
                    enemiesInRange.add(e.getConvertedToEnemy());
                    e.setConvertedToEnemy(null);
                }
                charHealth = character.getHp();
                enemyHealth = e.getHp();

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
            }

            if (enemyHealth <= 0) {
                defeatedEnemies.add(e);
                enemiesInRange.remove(e);   // Remove the enemy from enemies that are in range.
            // If an enemy did not die it means it was put in trance.
            } else {
                // Adds the new allied soldier into the characters array of allied soldiers.
                AlliedSoldier transformedSoldier = e.convertToFriendly(character);
                transformedEnemies.add(transformedSoldier); // Add the transformed enemy into the transformedEnemy array which holds the allied soldier.
                enemiesInRange.remove(e);   // Remove the enemy from enemies that are in range.
                i--;    // Subtract 1 from i so that the index remains the same when i gets added with 1. This is so that it doesnt skip an enemy since an enemy got removed.
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
            BasicEnemy oldEnemy = transedEnemy.getOldEnemy();
            defeatedEnemies.add(oldEnemy);
        }

        for (BasicEnemy e: defeatedEnemies){
            // IMPORTANT = we kill enemies here, because killEnemy removes the enemy from the enemies list
            // if we killEnemy in prior loop, we get java.util.ConcurrentModificationException
            // due to mutating list we're iterating over
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
            // TODO = give some cash/experience/item rewards for the discarding of the oldest card
            // TODO may have to edit payout based on what is being deleted
            payout();
            removeCard(0);
            int randomLoot = new Random().nextInt(3); // A random value between 0 and 2 inclusive.

            // Give the character gold, exp or a random weapon.
            if (randomLoot == 0) {
                character.addGold(new Random().nextInt(91)+10); // Add a random amount of gold ranging from 10 and 100 inclusive.
            } else if (randomLoot == 1) {
                int randomExp = new Random().nextInt(21) + 10; // A random value between 10 and 30
                character.addExperience(randomExp);
            } else {
                loadRandomItem();
            }
        }

        // TODO = Make RandomCardGenerator an instance variable to improve performance
        Card randCard = new RandomCardGenerator().nextCard(cardEntities.size(), 0);

        cardEntities.add(randCard);
        return randCard;
    }

    public Item loadRandomItem(){
        // if adding more cards than have, remove the first card...
        if (unequippedInventoryItems.size() >= unequippedInventoryHeight * unequippedInventoryWidth){
            // TODO = give some cash/experience/item rewards for the discarding of the oldest card
            removeItemByPositionInUnequippedInventoryItems(0);

            int randomLoot = new Random().nextInt(3); // A random value between 0 and 2 inclusive.
            // Give the character gold, exp or a random card.
            if (randomLoot == 0) {
                character.addGold(new Random().nextInt(91)+10); // Add a random amount of gold ranging from 10 and 100 inclusive.
            } else if (randomLoot == 1) {
                int randomExp = new Random().nextInt(21) + 10; // A random value between 10 and 30
                character.addExperience(randomExp);
            } else {
                loadRandomCard();
            }
        }

        // TODO = Make RandomCardGenerator an instance variable to improve performance
        Item item = new RandomItemGenerator().nextItem(unequippedInventoryItems.size(), 0);

        unequippedInventoryItems.add(item);
        return item;
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
    public Pair<Integer, Integer> getFirstSlotRemoveIfFull() {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
            removeItemByPositionInUnequippedInventoryItems(0);
            // give some cash/experience rewards for the discarding of the oldest sword
            payout();
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        return firstAvailableSlot;
    }

    /**
     * spawn an item in the world and return the item entity
     * @return an item to be spawned in the controller as a JavaFX node
     * @pre unequippedInventoryItems isn't full (getFirstSlotRemoveIfFull should be run before creating item for this method)
     */
    public void addUnequippedItem(Item item){
        unequippedInventoryItems.add(item);
    }

    /**
     * spawn a random BasicItem (no rare items)
     * All items have equal chance of spawn
     * used when enemies defeated or card destroyed
     * @return BasicItem to be spawned in the controller as a JavaFX node
     */
    public BasicItem addUnequippedRandomBasicItem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        Random randomGenerator = new Random();
        BasicItem newItem;
        int numBasicItems = 7;
        switch (randomGenerator.nextInt(numBasicItems)) {
            case 0:
                newItem = new HealthPotion(x, y);
                break;
            case 1:
                newItem = new Staff(x, y);
                break;
            case 2:
                newItem = new Stake(x, y);
                break;
            case 3:
                newItem = new Sword(x, y);
                break;
            case 4:
                newItem = new Armour(x, y);
                break;
            case 5:
                newItem = new Shield(x, y);
                break;
            case 6:
                newItem = new Helmet(x, y);
                break;
            default: throw new RuntimeException("Can't generate random item");
        }
        unequippedInventoryItems.add(newItem);
        return newItem;
    }


    /**
     * remove an item by x,y coordinates
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y){
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
    }

    /**
     * run moves which occur with every tick without needing to spawn anything immediately
     */
    public void runTickMoves(){
        if (firstPath == null) {
            firstPath = character.getPosition();
            firstPath = new PathPosition(firstPath.getCurrentPositionInPath(), firstPath.getOrderedPath());
        }

        GoalNode finalGoal = GoalEvaluator.evaluateGoals(worldGoals, character);

        if (GoalEvaluator.evaluate(finalGoal) == true) {
            // Character achieved all goals
            goalComplete.setValue(true);
        }
        character.moveDownPath();

        if (character.getX() == firstPath.getX().get() && character.getY() == firstPath.getY().get()) {
            character.addCycles();
        }
        moveBasicEnemies();
        possiblySpawnAlliedSoldiers();
        applyTrapAttacks();
    }

    /**
     * If The Character is at a barracks, spawn an AlliedSoldier.
     * @param item
     */
    public void possiblySpawnAlliedSoldiers() {
        // NOTE = Currently there is a double check on the building matching the Character's position.
        // We can decide which of these checks can be removed.
        buildingEntities.parallelStream()
            .filter(building -> building instanceof BarracksBuilding
                && character.getX() == building.getX()
                && character.getY() == building.getY())
            .map(building -> (BarracksBuilding) building)
            .forEach(barracks -> barracks.spawnAlliedSoldiers(character));
    }

    public void applyTrapAttacks() {
        for (Building building : buildingEntities) {
            if (building instanceof TrapBuilding) {
                ((TrapBuilding)building).damageAnyEnemies(enemies);
            }
        }
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    public void removeUnequippedInventoryItem(Entity item){
        item.destroy();
        unequippedInventoryItems.remove(item);
    }

    /**
     * return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    public Entity getUnequippedInventoryItemEntityByCoordinates(int x, int y){
        for (Entity e: unequippedInventoryItems){
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
        return null;
    }

    public Entity getEquippedInventoryItemEntityByCoordinates(int x, int y){
        for (Entity e: equippedInventoryItems){
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
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
    public Pair<Integer, Integer> getFirstAvailableSlotForItem(){
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available slot defined by looking row by row
        for (int y=0; y<unequippedInventoryHeight; y++){
            for (int x=0; x<unequippedInventoryWidth; x++){
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null){
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
    public void shiftCardsDownFromXCoordinate(int x){
        for (Card c: cardEntities){
            if (c.getX() >= x){
                c.x().set(c.getX()-1);
            }
        }
    }

    /**
     * move all enemies
     */
    public void moveBasicEnemies() {
        // TODO = expand to more types of enemy
        for (BasicEnemy e: enemies){
            e.move();
        }
    }

    // Only public for testing purposes
    // TODO = Revert to private visibility when cycle-complete triggers is implemented
    public void spawnEnemies() {
        for (Building building : buildingEntities) {
            if (building instanceof EnemySpawner) {
                EnemySpawner enemySpawner = (EnemySpawner) building;
                enemySpawner.spawn(this);
            }
        }
        /* // FP Alternative
        buildingEntities.stream()
            .filter(building -> building instanceof EnemySpawner)
            .map(building -> (EnemySpawner) building)
            .forEach(enemySpawner -> enemySpawner.spawn(this));
        */
    }

    /**
     * get a randomly generated position which could be used to spawn an enemy
     * @return null if random choice is that wont be spawning an enemy or it isn't possible, or random coordinate pair if should go ahead
     */
    public Pair<Integer, Integer> possiblyGetBasicEnemySpawnPosition(){
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
        }
        return null;
    }

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
     * remove a card by its x, y coordinates
     * @param cardNodeX x index from 0 to width-1 of card to be removed
     * @param cardNodeY y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     * @return {@code Building} the building if successfully created, OR {@code null} if otherwise
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        // Start by getting card
        Card card = null;
        for (Card c: cardEntities){
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)){
                card = c;
                break;
            }
        }

        // TODO = Replace above implementation with below
        // Other ideas: https://stackoverflow.com/questions/22694884/filter-java-stream-to-1-and-only-1-element
        Card cardMatches = cardEntities.stream()
            .filter(c -> (c.getX() == cardNodeX) && (c.getY() == cardNodeY))
            .collect(CustomCollectors.toSingleton());

        // Check that tile can be spawned here
        if (!card.canSpawnOnTile( getTileType(buildingNodeX, buildingNodeY) )) {
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
            if ((i.getX() == itemNodeX) && (i.getY() == itemNodeY)){
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
     * For a *path adjacent* tile, return the nearest path tile.
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

    public void payout() {
        if (new Random().nextInt(100) >= 85) {
            character.addGold(new Random().nextInt(90)+10);
        }
        if (new Random().nextInt(100) >= 50) {
            character.addExperience(new Random().nextInt(20)+10);
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

}
