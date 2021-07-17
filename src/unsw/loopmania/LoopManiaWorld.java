package unsw.loopmania;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */
public class LoopManiaWorld {
    // TODO = add additional backend functionality

    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;

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

    // TODO = expand the range of enemies
    private List<BasicEnemy> enemies;

    // TODO = expand the range of cards
    private List<Card> cardEntities;

    // TODO = expand the range of items
    private List<Entity> unequippedInventoryItems;

    // TODO = expand the range of buildings
    private List<Building> buildingEntities;

    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

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
        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
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
            removeCard(0);
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
            removeItemByPositionInUnequippedInventoryItems(0);;
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
    public Item addUnequippedItem(Item item){        
        // now we insert the new sword, as we know we have at least made a slot available...
        unequippedInventoryItems.add(item);
        return item;
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
        character.moveDownPath();
        moveBasicEnemies();
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

    /**
     * remove a card by its x, y coordinates
     * @param cardNodeX x index from 0 to width-1 of card to be removed
     * @param cardNodeY y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        // start by getting card
        Card card = null;
        for (Card c: cardEntities){
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)){
                card = c;
                break;
            }
        }
        /* FP Alternative
        // Other ideas: https://stackoverflow.com/questions/22694884/filter-java-stream-to-1-and-only-1-element
        var cardMatches = cardEntities.stream()
            .filter(c -> (c.getX() == cardNodeX) && (c.getY() == cardNodeY));
        assert cardMatches.count() == 1;
        Card myCard = cardMatches.findAny().get();
        */

        // now spawn building
        // VampireCastleBuilding newBuilding = new VampireCastleBuilding(new SimpleIntegerProperty(buildingNodeX), new SimpleIntegerProperty(buildingNodeY));
        Building newBuilding = card.createBuilding(new SimpleIntegerProperty(buildingNodeX),
                                                   new SimpleIntegerProperty(buildingNodeY));
        buildingEntities.add(newBuilding);

        // Destroy the card
        card.destroy();
        cardEntities.remove(card);
        shiftCardsDownFromXCoordinate(cardNodeX);

        return newBuilding;
    }

    public List<Card> getCards() {
        return cardEntities;
    }
}
