package unsw.loopmania;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EndScreenController {

    private boolean result;
    private ImageView img = new ImageView();
    private Image loseImg = new Image((new File("src/images/game_over.gif")).toURI().toString());
    private Image winImg = new Image((new File("src/images/level_complete.png")).toURI().toString());;

    public EndScreenController(boolean result) {
        this.result = result;
    }

    public Stage runEndScreen() {
        Stage endScreen = new Stage();
        endScreen.setResizable(false);
        endScreen.initModality(Modality.APPLICATION_MODAL);
        VBox Vbox = new VBox();
        setEndScreen(endScreen);
        Vbox.getChildren().add(img);
        StackPane stack = new StackPane();
        stack.setStyle("-fx-background-color: black");
        stack.getChildren().add(Vbox);
        Scene endScene = new Scene(stack);
        endScreen.setScene(endScene);
        endScreen.show();
        return endScreen;
    }

    private void setEndScreen(Stage stage) {
        if (result) {
            stage.setTitle("Congratulations!");
            img.setImage(winImg);
        } else {
            stage.setTitle("You Lose!");
            img.setImage(loseImg);
        }
    }

}
