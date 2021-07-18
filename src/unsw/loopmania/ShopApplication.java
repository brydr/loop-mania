package unsw.loopmania;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShopApplication extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		primaryStage.setTitle("Shop");
		primaryStage.setResizable(false);

		FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("ShopView.fxml"));
		shopLoader.setController(new ShopController());
		Parent shopRoot = shopLoader.load();

		Scene shopScene = new Scene(shopRoot);


		primaryStage.setScene(shopScene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
