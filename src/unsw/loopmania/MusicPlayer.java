package unsw.loopmania;

import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class MusicPlayer {
    private final MediaPlayer mainPlayer;
    private final MediaPlayer megaPlayer;
    private final MediaPlayer shopPlayer;

    private final String MAIN_THEME_FILE  = "/audio/music/KahootTrap.wav";
    private final String MEGALOVANIA_FILE = "/audio/music/Megalovania.wav";
    private final String SHOP_FILE        = "/audio/music/Shop.wav";

    private final double MAIN_THEME_VOL  = 0.7d;
    private final double MEGALOVANIA_VOL = 0.3d;
    private final double SHOP_VOL        = 0.2d;

    // A stack keeping track of what song overlaid what
    private final Deque<MediaPlayer> musicStack = new ArrayDeque<>();


    // This constructor is called during testing so it won't initialise the media players
    public MusicPlayer(boolean testMode) {
        mainPlayer = null;
        megaPlayer = null;
        shopPlayer = null;
    }

    public MusicPlayer() {
        final URL musicURL = getClass().getResource(MAIN_THEME_FILE);
        final URL megaURL  = getClass().getResource(MEGALOVANIA_FILE);
        final URL shopURL  = getClass().getResource(SHOP_FILE);

        mainPlayer = new MediaPlayer( new Media(musicURL.toString()) );
        megaPlayer = new MediaPlayer( new Media(megaURL.toString()) );
        shopPlayer = new MediaPlayer( new Media(shopURL.toString()) );

        mainPlayer.setVolume(MAIN_THEME_VOL);
        megaPlayer.setVolume(MEGALOVANIA_VOL);
        shopPlayer.setVolume(SHOP_VOL);

        // Style note: functional interfaces like `Runnable` can also have an explicit instance, or
        // use lambda functions
        // https://www.codejava.net/java-core/the-java-language/java-8-lambda-runnable-example

        // Main theme - replays indefinitely
        mainPlayer.setOnEndOfMedia(new Runnable(){
            @Override
            public void run() {
                mainPlayer.seek(Duration.ZERO);
            }
        });

        // Megalovania - replays indefinitely while bosses are still around
        megaPlayer.setOnEndOfMedia(new Runnable(){
            @Override
            public void run() {
                megaPlayer.seek(Duration.ZERO);
            }
        });

        // Shop - replays indefinitely while the shop's open
        shopPlayer.setOnEndOfMedia(new Runnable(){
            @Override
            public void run() {
                shopPlayer.seek(Duration.ZERO);
            }
        });

        musicStack.push(mainPlayer);
    }

    public void playMainTheme() {
        playTrack(mainPlayer);
    }

    public void playMegalovania() {
        playTrack(megaPlayer);
    }

    public void stopMegalovania() {
        stopTrack(megaPlayer);
    }

    public void playShop() {
        playTrack(shopPlayer);
    }

    public void stopShop() {
        stopTrack(shopPlayer);
    }

    public void play() {
        playTrack();
    }
    /**
     * If the song isn't already playing, add it to the stack and play
     *
     * @param player The song to play
     */
    private void playTrack(MediaPlayer player) {
        if (!musicStack.peek().equals(player)) {
            musicStack.peek().pause();
            musicStack.push(player);
            musicStack.peek().play();
        } else if (musicStack.peek().getStatus() != Status.PLAYING) {
            musicStack.peek().play();
        }
    }

    /**
     * Play the current song
     */
    private void playTrack() {
        if (musicStack.peek().getStatus() != Status.PLAYING) {
            musicStack.peek().play();
        }
    }
    /**
     * If the song is playing, remove it from the stack and play the previous
     *
     * @param player The song to stop
     */
    private void stopTrack(MediaPlayer player) {
        if (musicStack.peek().equals(player)) {
            musicStack.pop().stop();
            musicStack.peek().play();
        }
    }
}
