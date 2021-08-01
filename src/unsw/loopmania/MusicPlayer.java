package unsw.loopmania;

import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class MusicPlayer {
    private final MediaPlayer mainPlayer;
    private final MediaPlayer megaPlayer;

    private final String MAIN_THEME_FILE  = "/audio/music/KahootTrap.wav";
    private final String MEGALOVANIA_FILE = "/audio/music/Megalovania.wav";
    private final double MAIN_THEME_VOL  = 0.7d;
    private final double MEGALOVANIA_VOL = 0.3d;

    // This constructor is called during testing so it won't initialise the media players
    public MusicPlayer(boolean testMode) {
        mainPlayer = null;
        megaPlayer = null;
    }

    public MusicPlayer() {
        final URL musicURL = getClass().getResource(MAIN_THEME_FILE);
        final URL megaURL  = getClass().getResource(MEGALOVANIA_FILE);

        mainPlayer = new MediaPlayer( new Media(musicURL.toString()) );
        megaPlayer = new MediaPlayer( new Media(megaURL.toString()) );

        mainPlayer.setVolume(MAIN_THEME_VOL);
        megaPlayer.setVolume(MEGALOVANIA_VOL);


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

        // Megavolania - returns to main theme when finished
        megaPlayer.setOnEndOfMedia(new Runnable(){
            @Override
            public void run() {
                mainPlayer.play();
            }
        });
    }
    public void playMainTheme() {
        mainPlayer.play();
    }

    /**
     * Change to the "Megalovania" song.
     */
    public void playMegalovania() {
        if (megaPlayer.getStatus() != Status.PLAYING) {
            // Mute OR stop main song
            mainPlayer.stop();
            megaPlayer.play();
        }
    }

    /**
     * Revert to the main theme.
     */
    public void stopMegalovania() {
        if (megaPlayer.getStatus() == Status.PLAYING ) {
            megaPlayer.stop();
            mainPlayer.play();
        }
    }
}
