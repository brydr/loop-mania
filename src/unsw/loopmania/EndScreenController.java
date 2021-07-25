package unsw.loopmania;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EndScreenController {

    String msg;

    public EndScreenController(boolean result) {
        if (result) {
            msg = "Congratulations!! You won!";
        } else {
            msg = "You lose :(";
        }
    }

    public Stage runEndScreen() {
        Stage endScreen = new Stage();
        endScreen.setResizable(false);
        endScreen.initModality(Modality.APPLICATION_MODAL);
        Insets inset = new Insets(20.0f);
        VBox Vbox = new VBox();
        Vbox.setPadding(inset);
        Vbox.setAlignment(Pos.CENTER);
        Vbox.setSpacing(10);
        Vbox.getChildren().add(new Text(msg));

        Scene endScene = new Scene(Vbox);
        endScreen.setScene(endScene);
        endScreen.show();
        return endScreen;
    }

}
