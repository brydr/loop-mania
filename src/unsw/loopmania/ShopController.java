package unsw.loopmania;

import java.io.File;

import org.graalvm.compiler.loop.BasicInductionVariable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ShopController {
	private Shop shop;

	@FXML
	private GridPane shopPane;
	@FXML
	private GridPane characterPane;
	@FXML
	private Label priceLabel;

	public ShopController() {

	}

	public void initialiseShop(LoopManiaWorld world, ShopStrategy strategy) {
		shop = new Shop(world, strategy);
		// shop.getInventory().addListener(updateList);

		updateList();
	}

	private void updateList() {
		for (int i = 0; i < Shop.SHOP_HEIGHT; i++) {
			for (int j = 0; j < Shop.SHOP_WIDTH; j++) {
				Node node = getNodeFromGridPane(shopPane, i, j);
				shopPane.getChildren().remove(node);

				int listIndex = this.getIndexFrom2dCoords(i, j);
				System.out.print(listIndex + " ");
				System.out.println(shop.getInventory().get(listIndex).getClass());
				BasicItem itemAtNode = shop.getInventory().get(listIndex);
				File imageFile = new File(itemAtNode.getImage());
				ImageView image = new ImageView(imageFile.toURI().toString());
				image.hoverProperty().addListener(new HoverListener(itemAtNode));
				image.setCursor(Cursor.HAND);
				image.setScaleX(2);
				image.setScaleY(2);
				shopPane.add(image, i, j);
			}
		}
	}

	private int getIndexFrom2dCoords(int i, int j) {
		return i * Shop.SHOP_WIDTH + j;
	}

	/**
	 * Helper function sourced from
	 * https://stackoverflow.com/questions/20655024/javafx-gridpane-retrieve-specific-cell-content
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

	@FXML
	private void initialize() {
	}

	private class HoverListener implements ChangeListener<Boolean> {
		private final BasicItem item;

		public HoverListener(BasicItem item) {
			this.item = item;
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue) {
				priceLabel.setText(String.format("Click to buy for %d gold", item.getBuyPrice()));
			}

			// private final Shop shop;

			// private MenuSwitcher gameSwitcher;

			// public void setShopSwitcher(MenuSwitcher gameSwitcher){
			// this.gameSwitcher = gameSwitcher;
			// }

			// public void switchToShop() throws IOException {
			// gameSwitcher.switchMenu();
			// }

			// @FXML
			// public void closeShop() {

			// }
		}
	}
}
