package unsw.loopmania;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;


/**
 * the draggable types.
 * If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE{
    CARD,
    ITEM
}

/**
 * A JavaFX controller for the world.
 *
 * All event handlers and the timeline in JavaFX run on the JavaFX application thread:
 *     https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 *     Note in https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html under heading "Threading", it specifies animation timelines are run in the application thread.
 * This means that the starter code does not need locks (mutexes) for resources shared between the timeline KeyFrame, and all of the  event handlers (including between different event handlers).
 * This will make the game easier for you to implement. However, if you add time-consuming processes to this, the game may lag or become choppy.
 *
 * If you need to implement time-consuming processes, we recommend:
 *     using Task https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by itself or within a Service https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 *
 *     Tasks ensure that any changes to public properties, change notifications for errors or cancellation, event handlers, and states occur on the JavaFX Application thread,
 *         so is a better alternative to using a basic Java Thread: https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
 *     The Service class is used for executing/reusing tasks. You can run tasks without Service, however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need to implement locks on resources shared with the application thread (i.e. Timeline KeyFrame and drag Event handlers).
 * You can check whether code is running on the JavaFX application thread by running the helper method printThreadingNotes in this class.
 *
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 *
 * If you need to delay some code but it is not long-running, consider using Platform.runLater https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 *     This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController {

    /**
     * squares gridpane includes path images, enemies, character, empty grass, buildings
     */
    @FXML
    private GridPane squares;

    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot stretches over the entire game world,
     * so we can detect dragging of cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;

    @FXML
    private GridPane unequippedInventory;

    @FXML
    private GridPane characterStats;
    @FXML
    private Label hp;
    @FXML
    private Label gold;
    @FXML
    private Label exp;
    @FXML
    private Label cycles;

    @FXML
    private Label alliedSoldierNum;

    @FXML
    private CheckBox goalComplete;
    @FXML
    private Label allGoals;

    // all image views including tiles, character, enemies, cards... even though cards in separate gridpane...
    private List<ImageView> entityImages;

    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here and we actually drag this node
     */
    private DragIcon draggedEntity;

    private boolean isPaused;
    private LoopManiaWorld world;

    /**
     * runs the periodic game logic - second-by-second moving of character through maze, as well as enemies, and running of battles
     */
    private Timeline timeline;

    private Image vampireCastleCardImage;
    private Image swordImage;
    private ImageView potionSlot;

    /**
     * the image currently being dragged, if there is one, otherwise null.
     * Holding the ImageView being dragged allows us to spawn it again in the drop location if appropriate.
     */
    // TODO = it would be a good idea for you to instead replace this with the building/item which should be dropped
    private ImageView currentlyDraggedImage;

    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged into the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged outside of the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    /**
     * object handling switching to the main menu
     */
    private MenuSwitcher mainMenuSwitcher;

    private static final String grassAndDirt = "src/images/32x32GrassAndDirtPath.png";

    // /**
    //  * Object handling switching to the shop
    //  */
    // private MenuSwitcher shopSwitcher;

    // Object handling playing audio
    private AudioPlayer audioPlayer = new AudioPlayer();


    /**
     * @param world world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        entityImages = new ArrayList<>(initialEntities);
        vampireCastleCardImage = new Image((new File("src/images/vampire_castle_card.png")).toURI().toString());
        swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        potionSlot = new ImageView(new Image((new File("src/images/potion_slot.png")).toURI().toString()));
        currentlyDraggedImage = null;
        currentlyDraggedType = null;

        // initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
    }

    @FXML
    public void initialize() {
        Image pathTilesImage = new Image((new File(grassAndDirt)).toURI().toString());
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);

        // Add the ground first so it is below all other entities (inculding all the twists and turns)
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setId(grassAndDirt);
                groundView.setViewport(imagePart);
                squares.add(groundView, x, y);
            }
        }

        // load entities loaded from the file in the loader into the squares gridpane
        for (ImageView entity : entityImages){
            squares.getChildren().add(entity);
        }

        // add the ground underneath the cards
        for (int x=0; x<world.getWidth(); x++){
            ImageView groundView = new ImageView(pathTilesImage);
            groundView.setViewport(imagePart);
            cards.add(groundView, x, 0);
        }

        // add the empty slot images for the unequipped inventory
        for (int x=0; x<LoopManiaWorld.unequippedInventoryWidth; x++){
            for (int y=0; y<LoopManiaWorld.unequippedInventoryHeight; y++){
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

        Character character = world.getCharacter();

        hp.textProperty().bind(character.hpProperty().asString());
        gold.textProperty().bind(character.goldProperty().asString());
        exp.textProperty().bind(character.expProperty().asString());
        cycles.textProperty().bind(character.cycleProperty().asString());
        alliedSoldierNum.textProperty().bind(character.alliedSoldierProperty().asString());

        goalComplete.selectedProperty().bind(world.goalProperty());

        GoalNode finalGoal = GoalEvaluator.evaluateGoals(world.getWorldGoals(), character);
        String goalsToComplete = GoalEvaluator.prettyPrint(finalGoal);
        allGoals.setText(goalsToComplete);


        // create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);
    }

    /**
     * create and run the timer
     */
    public void startTimer(){
        // TODO = handle more aspects of the behaviour required by the specification
        System.out.println("starting timer");
        isPaused = false;
        // trigger adding code to process main game logic to queue. JavaFX will target framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
            world.runTickMoves();
            List<BasicEnemy> defeatedEnemies = world.runBattles();
            if (defeatedEnemies.size() > 0) {
                for (BasicEnemy e: defeatedEnemies){
                    reactToEnemyDefeat(e);
                }
                audioPlayer.playWinBattleSound();
                runBattleResults(defeatedEnemies);
            }
            removeBrokenItems();
            world.possiblySpawnEnemies();
            List<BasicEnemy> newEnemies = world.getEnemies();
            for (BasicEnemy newEnemy : newEnemies){
                onLoad(newEnemy);
            }

            List<RandomPathLoot> pickedUpLoot = world.pickUpLoot();
            for (RandomPathLoot pathLoot : pickedUpLoot){
                reactToLootPicked(pathLoot);
            }

            List<RandomPathLoot> newPathLoot = world.possiblyDropPathLoot();
            for (RandomPathLoot pathLoot : newPathLoot){
                onLoad(pathLoot);
            }

            // if (isAtCastle) {
            //     // TODO open shop after loop 1, 3, 5......
            //     openShop();
            // }


            printThreadingNotes("HANDLED TIMER");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }




    /**
     * pause the execution of the game loop
     * the human player can still drag and drop items during the game pause
     */
    public void pause() {
        isPaused = true;
        System.out.println("pausing");
        timeline.stop();
    }

    public void terminate() {
        pause();
    }

    /**
     * pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * @param entity backend entity to be paired with view
     * @param view frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }

    /**
     * load a random card from the world, and pair it with an image in the GUI
     */
    private void loadRandomCard() {
        Card card = world.loadRandomCard();
        onLoad(card);
    }

    // // TODO do we need these load functions?
    // /**
    //  * load a sword from the world, and pair it with an image in the GUI
    //  */
    // private void loadSword(){
    //     // start by getting first available coordinates
    //     Pair<Integer, Integer> firstAvailableSlot = world.getFirstSlotRemoveIfFull();
    //     Sword sword = new Sword(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
    //     // add sword to list of unequipped items in backend
    //     world.addUnequippedItem(sword);
    //     onLoad(sword);
    // }

    // /**
    //  * load a staff from the world, and pair it with an image in the GUI
    //  */
    // private void loadStaff(){
    //     // start by getting first available coordinates
    //     Pair<Integer, Integer> firstAvailableSlot = world.getFirstSlotRemoveIfFull();
    //     Staff staff = new Staff(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
    //     // add staff to list of unequipped items in backend
    //     world.addUnequippedItem(staff);
    //     onLoad(staff);
    // }

    // /**
    //  * load a stake from the world, and pair it with an image in the GUI
    //  */
    // private void loadStake(){
    //     // start by getting first available coordinates
    //     Pair<Integer, Integer> firstAvailableSlot = world.getFirstSlotRemoveIfFull();
    //     Stake stake = new Stake(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
    //     // add stake to list of unequipped items in backend
    //     world.addUnequippedItem(stake);
    //     onLoad(stake);
    // }

    // /**
    //  * load a shield from the world, and pair it with an image in the GUI
    //  */
    // private void loadShield(){
    //     // start by getting first available coordinates
    //     Pair<Integer, Integer> firstAvailableSlot = world.getFirstSlotRemoveIfFull();
    //     Shield shield = new Shield(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
    //     // add shield to list of unequipped items in backend
    //     world.addUnequippedItem(shield);
    //     onLoad(shield);
    // }
    // /**
    //  * load a Armour from the world, and pair it with an image in the GUI
    //  */
    // private void loadArmour(){
    //     // start by getting first available coordinates
    //     Pair<Integer, Integer> firstAvailableSlot = world.getFirstSlotRemoveIfFull();
    //     Armour armour = new Armour(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
    //     // add Armour to list of unequipped items in backend
    //     world.addUnequippedItem(armour);
    //     onLoad(armour);
    // }

    // /**
    //  * load a Helmet from the world, and pair it with an image in the GUI
    //  */
    // private void loadHelmet(){
    //     // start by getting first available coordinates
    //     Pair<Integer, Integer> firstAvailableSlot = world.getFirstSlotRemoveIfFull();
    //     Helmet helmet = new Helmet(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
    //     // add Helmet to list of unequipped items in backend
    //     world.addUnequippedItem(helmet);
    //     onLoad(helmet);
    // }

    /**
     * load a HealthPotion from the world, and pair it with an image in the GUI
     */
    private void loadHealthPotion(){
        // start by getting first available coordinates
        Pair<Integer, Integer> firstAvailableSlot = world.getFirstSlotRemoveIfFull();
        HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        // add HealthPotion to list of unequipped items in backend
        world.addUnequippedItem(healthPotion);
        onLoad(healthPotion);
    }

    /**
     * load TheOneRing from the world, and pair it with an image in the GUI
     */
    private void loadTheOneRing(){
        // start by getting first available coordinates
        Pair<Integer, Integer> firstAvailableSlot = world.getFirstSlotRemoveIfFull();
        TheOneRing theOneRing = new TheOneRing(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        // add TheOneRing to list of unequipped items in backend
        world.addUnequippedItem(theOneRing);
        onLoad(theOneRing);
    }

    /**
     * load random BasicItem from the world, and pair it with an image in the GUI
     */
    private void loadRandomBasicItem(){
        // start by getting first available coordinates
        Pair<Integer, Integer> firstAvailableSlot = world.getFirstSlotRemoveIfFull();
        BasicItem item = world.addUnequippedRandomBasicItem(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        onLoad(item);
    }

    /**
     * Loads an item that already exists but has invalid X and Y values
     *
     * @param item The item being loaded
     */
    public void loadItem(Item item) {
        Pair<Integer, Integer> firstAvailableSlot = world.getFirstSlotRemoveIfFull();
        item.setX(new SimpleIntegerProperty(firstAvailableSlot.getValue0()));
        item.setY(new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
        onLoad(item);
    }

    /**
     * run GUI events after an enemy is defeated, such as spawning items/experience/gold
     * @param enemy defeated enemy for which we should react to the death of
     */
    private void reactToEnemyDefeat(BasicEnemy enemy){
        // react to character defeating an enemy
        // in starter code, spawning extra card/weapon...
        // TODO = provide different benefits to defeating the enemy based on the type of enemy
        Character character = world.getCharacter();
        int experienceGain = enemy.getExperienceGain(); // Get the experience obtained from defeating an enemy.
        character.addExperience(experienceGain);
        int randomLoot = new Random().nextInt(3); // A random value between 0 and 2 inclusive.
        int oneRingChance = new Random().nextInt(100); // A random value between 0 and 99 inclusive.
        if (randomLoot == 0) {
            character.addGold(new Random().nextInt(91)+10); // Add a random amount of gold ranging from 10 and 100 inclusive.
        } else if (randomLoot == 1) {
            loadRandomCard();
        } else {
            loadRandomBasicItem();
        }

        if (oneRingChance < 3) {
            Pair<Integer, Integer> firstAvailableSlot = world.getFirstAvailableSlotForItem();
            if (firstAvailableSlot == null){
                // eject the oldest unequipped item and replace it... oldest item is that at beginning of items
                // TODO = give some cash/experience rewards for the discarding of the oldest sword
                world.removeItemByPositionInUnequippedInventoryItems(0);
                firstAvailableSlot = world.getFirstAvailableSlotForItem();
            }
            loadTheOneRing();
        }
    }

    public void runBattleResults(List<BasicEnemy> defeatedEnemies) {
        BattleResultsController results = new BattleResultsController(defeatedEnemies, world.getCharacter().getHp());
        pause();
        Stage test = results.BattleResults();
        test.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                test.close();
                startTimer();
            }
        });
    }

    @FXML
    private void openShop() throws IOException {
        pause();
        // FIXME get difficulty from main menu
        showShop(world, new StandardMode());
    }

    private void showShop(LoopManiaWorld world, ShopStrategy strategy) throws IOException {
        pause();

        FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("ShopView.fxml"));
        Parent shopRoot = shopLoader.load();
        Scene shopScene = new Scene(shopRoot);
        shopRoot.requestFocus();

        Stage shopStage = new Stage();
        shopStage.setScene(shopScene);
        shopStage.setResizable(false);
        shopStage.setTitle("Shop");
        ShopController shopController = shopLoader.getController();
        shopController.initialiseShop(world, strategy);
        shopStage.show();

        shopStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                shopStage.close();
                startTimer();
            }
        });
    }

    /**
     * run GUI events after an path loot is picked up.
     * @param randPathLoot the random path loot that needs to be reacted to.
     */
    private void reactToLootPicked(RandomPathLoot randPathLoot) {
        Character character = world.getCharacter();
        int pickUpVal = randPathLoot.onPickUp();

        if (pickUpVal == 0) {
            // If they picked up gold.
            // Give the character a gold payout between 10 and 100 inclusive.
            final int goldPayout = 10 + new Random().nextInt(91);
            character.addGold(goldPayout);
            audioPlayer.playPickupGoldSound();
        } else if (pickUpVal == 1) {
             // If they picked up a potion.
            loadHealthPotion();
            audioPlayer.playPickupPotionSound();
        } else {
            // Something is broken
            assert false;
        }
    }

    /**
     * load a vampire castle card into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the cards GridPane.
     * @param card
     */
    private void onLoad(Card card) {
        ImageView view = new ImageView(new Image((new File(card.getImage())).toURI().toString()));
        view.setId(card.getImage());
        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares);

        addEntity(card, view);
        cards.getChildren().add(view);
    }

    /**
     * load an item into the GUI
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the unequippedInventory GridPane.
     * @param item
     */
    private void onLoad(Item item) {
        ImageView view = new ImageView(new Image((new File(item.getImage())).toURI().toString()));
        view.setId(item.getImage());
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedItems);
        addEntity(item, view);
        unequippedInventory.getChildren().add(view);
    }

    /**
     * TODO do we need this method as we don't spawn items into equipped grid just from unequipped into equipped
     * load an item into the GUI
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the equipped GridPane.
     * @param item
     */
    private void onLoadEquipped(Item item) {
        ImageView view = new ImageView(new Image((new File(item.getImage())).toURI().toString()));

        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedItems);//TODO may need to remove drageEventHandler here as we don't drag equipped item anywhere?
        addEntity(item, view);
        equippedItems.getChildren().add(view);
    }

    /**
     * load an enemy into the GUI
     * @param enemy
     */
    private void onLoad(BasicEnemy enemy) {
        ImageView view = new ImageView(new Image((new File(enemy.getImage())).toURI().toString()));
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    /**
     * load a building into the GUI
     * @param building
     */
    private void onLoad(Building building){
        ImageView view = new ImageView(new Image((new File(building.getImage())).toURI().toString()));
        view.setId(building.getImage());
        addEntity(building, view);
        squares.getChildren().add(view);
    }

    /**
     * load a random path drop into the GUI
     * @param randomPathLoot
     */
    private void onLoad(RandomPathLoot randomPathLoot) {
        ImageView view = new ImageView(new Image((new File(randomPathLoot.getImage())).toURI().toString()));
        addEntity(randomPathLoot, view);
        squares.getChildren().add(view);
    }

    /**
     * add drag event handlers for dropping into gridpanes, dragging over the background, dropping over the background.
     * These are not attached to invidual items such as swords/cards.
     * @param draggableType the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        // TODO = be more selective about where something can be dropped
        // for example, in the specification, villages can only be dropped on path, whilst vampire castles cannot go on the path

        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                // TODO = for being more selective about where something can be dropped, consider applying additional if-statement logic
                /*
                 *you might want to design the application so dropping at an invalid location drops at the most recent valid location hovered over,
                 * or simply allow the card/item to return to its slot (the latter is easier, as you won't have to store the last valid drop location!)
                 */
                if (currentlyDraggedType == draggableType) {
                    // problem = event is drop completed is false when should be true...
                    // https://bugs.openjdk.java.net/browse/JDK-8117019
                    // putting drop completed at start not making complete on VLAB...

                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    final Dragboard db = event.getDragboard();
                    final Node node = event.getPickResult().getIntersectedNode();
                    if (node != targetGridPane && db.hasImage()) {

                        final Integer colIndex = GridPane.getColumnIndex(node);
                        final Integer rowIndex = GridPane.getRowIndex(node);
                        final int x = (colIndex != null) ? colIndex : 0;
                        final int y = (rowIndex != null) ? rowIndex : 0;

                        // Places at 0,0 - will need to take coordinates once that is implemented
                        ImageView image = new ImageView(db.getImage());

                        boolean placeBack = false;

                        final int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        final int nodeY = GridPane.getRowIndex(currentlyDraggedImage);
                        switch (draggableType) {
                            case CARD:
                                // TODO = spawn a building here of different types
                                Building newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);

                                if (newBuilding != null) {
                                    // Building placed successfully
                                    audioPlayer.playDropBuildingSound();
                                    removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                    onLoad(newBuilding);
                                } else {
                                    placeBack = true;
                                }
                                break;
                            case ITEM:
                                Node targetNode = getNodeFromGridPane(targetGridPane, x, y);
                                Item item = world.getUnequippedItemTypeByCoordinates(nodeX, nodeY);
                                // Set this to true if none of the conditions occur
                                placeBack = false;
                                if (targetNode.getId().equals("swordCell") && item instanceof Weapon) {
                                    Weapon weapon = (Weapon)item;
                                    world.getCharacter().setEquippedWeapon(weapon);
                                } else if (targetNode.getId().equals("helmetCell") && item instanceof Helmet) {
                                    Helmet helmet = (Helmet)item;
                                    world.getCharacter().setEquippedHelmet(helmet);
                                } else if (targetNode.getId().equals("armourCell") && item instanceof Armour) {
                                    Armour armour = (Armour)item;
                                    world.getCharacter().setEquippedArmour(armour);
                                    audioPlayer.playEquipArmourSound();
                                } else if (targetNode.getId().equals("shieldCell") && item instanceof Shield) {
                                    Shield shield = (Shield)item;
                                    world.getCharacter().setEquippedShield(shield);
                                } else if (targetNode.getId().equals("rareItemCell") && item instanceof RareItem) {
                                    RareItem rareItem = (RareItem)item;
                                    world.getCharacter().setEquippedRareItem(rareItem);
                                } else if (targetNode.getId().equals("potionCell") && item instanceof HealthPotion) {
                                    HealthPotion healthPotion = (HealthPotion)item;
                                    world.getCharacter().setEquippedHealthPotion(healthPotion);
                                } else {
                                    placeBack = true;
                                }
                                if (!placeBack) {
                                    removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                    removeItemByCoordinates(nodeX, nodeY);
                                    targetGridPane.add(image, x, y, 1, 1);
                                    // Play the correct sound
                                    if (item instanceof Armour || item instanceof Helmet)
                                        audioPlayer.playEquipArmourSound();
                                    else if (item instanceof Sword)
                                        audioPlayer.playEquipSwordSound();
                                    else
                                        audioPlayer.playEquipDefaultSound();
                                }
                                break;
                            default:
                             // Something went very wrong
                                assert false;
                        }

                        // Set the dragged item back to true if it got placed in a non valid tile.
                        if (placeBack) {
                            currentlyDraggedImage.setVisible(true);
                        }

                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");

                    }
                }
                event.setDropCompleted(true);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
            }
        });

        // this doesn't fire when we drag over GridPane because in the event handler for dragging over GridPanes, we consume the event
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>(){
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    if (event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null){
                    draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                event.consume();
            }
        });

        // this doesn't fire when we drop over GridPane because in the event handler for dropping over GridPanes, we consume the event
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != anchorPaneRoot && db.hasImage()){
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);

                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                    }
                }
                //let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /**
     * remove the card from the world, and spawn and return a building instead where the card was dropped
     * @param cardNodeX the x coordinate of the card which was dragged, from 0 to width-1
     * @param cardNodeY the y coordinate of the card which was dragged (in starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card, where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card, where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    private Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        return world.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
    }

    /**
     * remove an item from the unequipped inventory by its x and y coordinates in the unequipped inventory gridpane
     * @param nodeX x coordinate from 0 to unequippedInventoryWidth-1
     * @param nodeY y coordinate from 0 to unequippedInventoryHeight-1
     */
    private void removeItemByCoordinates(int nodeX, int nodeY) {
        world.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    }

    /**
     * add drag event handlers to an ImageView
     * @param view the view to attach drag event handlers to
     * @param draggableType the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be dragged
     * @param targetGridPane the relevant gridpane to which the entity would be dragged to
     */
    private void addDragEventHandlers(ImageView view, DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                currentlyDraggedImage = view; // set image currently being dragged, so squares setOnDragEntered can detect it...
                currentlyDraggedType = draggableType;
                //Drag was detected, start drap-and-drop gesture
                //Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);

                //Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane);

                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                switch (draggableType){
                    case CARD:
                        Image cardImage = currentlyDraggedImage.getImage();
                        draggedEntity.setImage(cardImage);
                        break;
                    case ITEM:
                        Image itemImage = currentlyDraggedImage.getImage();
                        draggedEntity.setImage(itemImage);
                        break;
                    default:
                        assert false;
                        break;
                }

                draggedEntity.setVisible(true);
                draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // to be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

                // when entering and exiting possible squares change opacity
                for (Node n: targetGridPane.getChildren()){
                    // events for entering and exiting are attached to squares children because that impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = be more selective about whether highlighting changes - if it cannot be dropped in the location, the location shouldn't be highlighted!
                        @Override
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                            //The drag-and-drop gesture entered the target
                            //show the user that it is an actual gesture target
                                if(event.getGestureSource() != n && event.getDragboard().hasImage() && checkSameCell(currentlyDraggedImage.getId(), n.getId())){
                                    n.setOpacity(0.4);
                                }
                            }

                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = since being more selective about whether highlighting changes, you could program the game so if the new highlight location is invalid the highlighting doesn't change, or leave this as-is
                        @Override
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                                n.setOpacity(1);
                            }
                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
                }
                event.consume();
            }

        });
    }

    // checks if dragged item can be placed in target node
    public boolean checkSameCell(String draggedId, String targetId) {
        // check null for when slot is already filled with another image, no need to set opacity
        if (targetId == null) return false;
        if (draggedId.equals("src/images/shield.png") && targetId.equals("shieldCell")) return true;
        else if (draggedId.equals("src/images/helmet.png") && targetId.equals("helmetCell")) return true;
        else if (draggedId.equals("src/images/the_one_ring.png") && targetId.equals("rareItemCell")) return true;
        else if (draggedId.equals("src/images/brilliant_blue_new.png") && targetId.equals("potionCell")) return true;
        else if (draggedId.equals("src/images/armour.png") && targetId.equals("armourCell")) return true;
        else if (draggedId.equals("src/images/basic_sword.png") && targetId.equals("swordCell")) return true;
        else if (draggedId.equals("src/images/stake.png") && targetId.equals("swordCell")) return true;
        else if (draggedId.equals("src/images/staff.png") && targetId.equals("swordCell")) return true;
        else if ((draggedId.equals("src/images/trap_card.png")
                || (draggedId.equals("src/images/village_card.png"))
                || (draggedId.equals("src/images/barracks_card.png")))
                && targetId.equals(LoopManiaWorldControllerLoader.pathTile))
            return true;
        else if ((draggedId.equals("src/images/campfire_card.png")
                || (draggedId.equals("src/images/tower_card.png"))
                || (draggedId.equals("src/images/vampire_castle_card.png"))
                || (draggedId.equals("src/images/zombie_pit_card.png")))
                && targetId.equals(grassAndDirt))
            return true;
        // otherwise
        return false;
    }

    /**
     * remove drag event handlers so that we don't process redundant events
     * this is particularly important for slower machines such as over VLAB.
     * @param draggableType either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane){
        // remove event handlers from nodes in children squares, from anchorPaneRoot, and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n : targetGridPane.getChildren()){
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
    }

    /**
     * handle the pressing of keyboard keys.
     * Specifically, we should pause when pressing SPACE
     * @param event some keyboard key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case SPACE:
            if (isPaused){
                startTimer();
            }
            else{
                pause();
            }
            break;
        case Q:
            useHealthPotion(equippedItems);
            break;
        default:
            break;
        }

    }

    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher){
        // TODO = possibly set other menu switchers
        this.mainMenuSwitcher = mainMenuSwitcher;
    }

    // public void setShopSwitcher(MenuSwitcher shopSwitcher) {
    //     this.shopSwitcher = shopSwitcher;
    // }

    /**
     * this method is triggered when click button to go to main menu in FXML
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        // TODO = possibly set other menu switchers
        pause();
        mainMenuSwitcher.switchMenu();
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the world.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     *
     * note that this is put in the controller rather than the loader because we need to track positions of spawned entities such as enemy
     * or items which might need to be removed should be tracked here
     *
     * NOTE teardown functions setup here also remove nodes from their GridPane. So it is vital this is handled in this Controller class
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        // TODO = tweak this slightly to remove items from the equipped inventory?
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // if need to remove items from the equipped inventory, add code to remove from equipped inventory gridpane in the .onDetach part
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
                                               .onAttach((o, l) -> o.addListener(xListener))
                                               .onDetach((o, l) -> {
                                                    o.removeListener(xListener);
                                                    entityImages.remove(node);
                                                    squares.getChildren().remove(node);
                                                    cards.getChildren().remove(node);
                                                    equippedItems.getChildren().remove(node);
                                                    unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
                                               .onAttach((o, l) -> o.addListener(yListener))
                                               .onDetach((o, l) -> {
                                                   o.removeListener(yListener);
                                                   entityImages.remove(node);
                                                   squares.getChildren().remove(node);
                                                   cards.getChildren().remove(node);
                                                   equippedItems.getChildren().remove(node);
                                                   unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here, position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handleX.detach();
                handleY.detach();
            }
        });
    }

    /**
     * we added this method to help with debugging so you could check your code is running on the application thread.
     * By running everything on the application thread, you will not need to worry about implementing locks, which is outside the scope of the course.
     * Always writing code running on the application thread will make the project easier, as long as you are not running time-consuming tasks.
     * We recommend only running code on the application thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */
    private void printThreadingNotes(String currentMethodLabel){
        System.out.println("\n###########################################");
        System.out.println("current method = "+currentMethodLabel);
        System.out.println("In application thread? = "+Platform.isFxApplicationThread());
        System.out.println("Current system time = "+java.time.LocalDateTime.now().toString().replace('T', ' '));
    }

    /**
     * Helper function sourced from https://stackoverflow.com/questions/20655024/javafx-gridpane-retrieve-specific-cell-content
     * used to get specific node from a gridpane (used for equippedItems)
     */
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    /**
     * Consume a health potion
     * @param gridPane The equipped items pane.
     * @precondition Potion slot is at (col, row) = (1, 1)
     */
    private void useHealthPotion(GridPane gridPane) {
        final boolean wasConsumed = world.getCharacter().consumePotion();
        // If potion was consumed, play sound
        if (wasConsumed) {
            audioPlayer.playUsePotionSound();
            HealthPotion item = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
            unequipItem(new Triplet<>(1, 1, item));
        }
    }

    /**
     * remove damaged equipped broken items after battle
     */
    private void removeBrokenItems() {
        Character character = world.getCharacter();
        System.out.println("armour" + character.getEquippedArmour());
        System.out.println("helmet" + character.getEquippedHelmet());
        System.out.println("shield" + character.getEquippedShield());
        System.out.println("weapon" + character.getEquippedWeapon());
        List<Triplet<Integer, Integer, BasicItem>> damagedItems = character.removeDamagedItems();
        for (Triplet<Integer, Integer, BasicItem> triplet : damagedItems) {
            unequipItem(triplet);
        }
    }

    private void unequipItem(Triplet<Integer, Integer, BasicItem> triplet) {
        final Node node = getNodeFromGridPane(equippedItems, triplet.getValue0(), triplet.getValue1());
        assert node != null;
        equippedItems.getChildren().remove(node);

        final ImageView emptyslot = new ImageView(
            new Image(
            new File(triplet.getValue2().getEmptySlotImage()).toURI().toString()
        ));
        emptyslot.setId(triplet.getValue2().getEmptySlotId());
        equippedItems.add(emptyslot, triplet.getValue0(), triplet.getValue1());
        Node newNode = getNodeFromGridPane(equippedItems, triplet.getValue0(), triplet.getValue1());
        newNode.setId(triplet.getValue2().getEmptySlotId());
    }
}
