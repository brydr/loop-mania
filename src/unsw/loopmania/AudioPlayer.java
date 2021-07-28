package unsw.loopmania;

import javafx.scene.media.AudioClip;

/**
 * Class which abstracts audio playing in our JavaFX frontend.
 * @apiNote We can apply the "singleton pattern" to this if we really wanted.
 * @apiNote This can count as a "facade pattern".
 * @apiNote Future feature: We can introduce a volume slider to scale all volumes.
 */
public final class AudioPlayer {
    // ---- Constant Paths ----
    // Sound effects obtained from https://www.zapsplat.com
    private final String WIN_BATTLE_FILE = "/audio/WinBattleSound.mp3";
    private final String PICKUP_GOLD_FILE = "/audio/PickupGoldSound.mp3";
    private final String PICKUP_POTION_FILE = "/audio/PickupPotionSound.mp3";
    private final String USE_POTION_FILE = "/audio/UsePotionSound.mp3";
    private final String EQUIP_DEFAULT_FILE = "/audio/EquipDefaultSound.mp3";
    private final String EQUIP_ARMOUR_FILE = "/audio/EquipArmourSound.mp3";
    private final String EQUIP_SWORD_FILE = "/audio/EquipSwordSound.mp3";
    private final String DROP_BUILDING_FILE = "/audio/DropBuildingSound.mp3";
    

    // ---- AudioClips ----
    // Note: Conceptually constant, but I'm reserving CONSTANT_NAME_STYLE for primitives and java.lang objects
    private final AudioClip winBattleSound;
    private final AudioClip pickupGoldSound;
    private final AudioClip pickupPotionSound;
    private final AudioClip usePotionSound;
    private final AudioClip equipDefaultSound;
    private final AudioClip equipArmourSound;
    private final AudioClip equipSwordSound;
    private final AudioClip dropBuildingSound;

    // ---- Default Volumes ----
    // Note: We can use this value as a parameter for audioClip.play(), or alternatively call audioClip.setVolume()
    // to store it as the default for our AudioClip
    private final double WIN_BATTLE_VOL = 0.5d;
    private final double PICKUP_GOLD_VOL = 1.0d;
    private final double PICKUP_POTION_VOL = 1.0d;
    private final double USE_POTION_VOL = 1.0d;
    private final double EQUIP_DEFAULT_VOL = 1.0d;
    private final double EQUIP_ARMOUR_VOL = 1.0d;
    private final double EQUIP_SWORD_VOL = 1.0d;
    private final double DROP_BUILDING_VOL = 1.0d;



    /**
     * Simple helper/wrapper for creating an {@code AudioClip} from a file path, used to simplify constructor.
     * @param filePath The {@code String} containing the location of the sound file, to be consumed by 
     * {@code getClass().getResource(String)}
     * @return A new {@code AudioClip}
     */
    private AudioClip newAudioClipFromPath(final String filePath) {
        final String fileURL = getClass().getResource(filePath).toExternalForm();
        return new AudioClip(fileURL);

    }

    /**
     * Loads all AudioClip/Media into memory
     */
    public AudioPlayer() {
        // Initialise AudioClips
        this.winBattleSound = newAudioClipFromPath(WIN_BATTLE_FILE);
        this.pickupGoldSound = newAudioClipFromPath(PICKUP_GOLD_FILE);
        this.pickupPotionSound = newAudioClipFromPath(PICKUP_POTION_FILE);
        this.usePotionSound = newAudioClipFromPath(USE_POTION_FILE);
        this.equipDefaultSound = newAudioClipFromPath(EQUIP_DEFAULT_FILE);
        this.equipArmourSound = newAudioClipFromPath(EQUIP_ARMOUR_FILE);
        this.equipSwordSound = newAudioClipFromPath(EQUIP_SWORD_FILE);
        this.dropBuildingSound = newAudioClipFromPath(DROP_BUILDING_FILE);
    }

    public void playWinBattleSound() {
        winBattleSound.play(WIN_BATTLE_VOL);
    }

    public void playPickupGoldSound() {
        pickupGoldSound.play();
    }

    public void playPickupPotionSound() {
        pickupPotionSound.play();
    }

    public void playUsePotionSound() {
        usePotionSound.play();
    }

    /* // This logic is being handled by controller for now
    public void playEquipSound(Item item) {
        if (item instanceof Armour || item instanceof Helmet)
            playEquipArmourSound();
        else if (item instanceof Sword)
            playEquipSwordSound();
        else
            playEquipDefaultSound();
    }
    */ 

    public void playEquipDefaultSound() {
        equipDefaultSound.play();
    }

    public void playEquipArmourSound() {
        equipArmourSound.play();
    }

    public void playEquipSwordSound() {
        equipSwordSound.play();
    }

    public void playDropBuildingSound() {
        dropBuildingSound.play();
    }
    
}
