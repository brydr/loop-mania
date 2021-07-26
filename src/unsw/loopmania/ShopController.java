package unsw.loopmania;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ShopController {
	private Shop shop;
	private LoopManiaWorld world;
	@FXML
	private GridPane shopPane;
	@FXML
	private GridPane characterPane;
	@FXML
	private Label priceLabel;

	private final static String LABEL_DEFAULT_TEXT = "Click items to buy, close window when done";

	public ShopController() {
	}

	public void initialiseShop(LoopManiaWorld world, ShopStrategy strategy) {
		shop = new Shop(world, strategy);
		this.world = world;
		updateGridPanes();
		world.getCharacter().addGold(100000000);
	}

	private void updateGridPanes() {
		updateShopGridPanes();
		updateCharacterPanes();
	}

	private void updateShopGridPanes() {
		for (int i = 0; i < Shop.SHOP_HEIGHT; i++) {
			for (int j = 0; j < Shop.SHOP_WIDTH; j++) {
				Node node = getNodeFromGridPane(shopPane, i, j);
				shopPane.getChildren().remove(node);

				int listIndex = this.getIndexFrom2dCoordinates(i, j, Shop.SHOP_WIDTH);

				if (listIndex >= shop.getInventory().size()) {
					continue;
				}

				System.out.print(listIndex + " ");
				System.out.println(shop.getInventory().get(listIndex).getClass());
				Item itemAtNode = shop.getInventory().get(listIndex);
				File imageFile = new File(itemAtNode.getImage());
				ImageView image = new ImageView(imageFile.toURI().toString());
				image.hoverProperty().addListener(new HoverListener(itemAtNode, false));
				image.addEventHandler(MouseEvent.MOUSE_CLICKED, new ClickHandler(itemAtNode, false));
				image.setCursor(Cursor.HAND);
				image.setScaleX(2);
				image.setScaleY(2);
				shopPane.add(image, i, j);
			}
		}
	}

	public void updateCharacterPanes() {
		for (int i = 0; i < LoopManiaWorld.unequippedInventoryHeight; i++) {
			for (int j = 0; j < LoopManiaWorld.unequippedInventoryWidth; j++) {
				Node node = getNodeFromGridPane(characterPane, i, j);
				characterPane.getChildren().remove(node);

				int listIndex = this.getIndexFrom2dCoordinates(i, j, LoopManiaWorld.unequippedInventoryWidth);

				if (listIndex >= world.getInventory().size()) {
					continue;
				}

				System.out.print(listIndex + " ");
				System.out.println(world.getInventory().get(listIndex).getClass());
				Item itemAtNode = world.getInventory().get(listIndex);
				File imageFile = new File(itemAtNode.getImage());
				ImageView image = new ImageView(imageFile.toURI().toString());
				image.hoverProperty().addListener(new HoverListener(itemAtNode, true));
				image.addEventHandler(MouseEvent.MOUSE_CLICKED, new ClickHandler(itemAtNode, true));
				image.setCursor(Cursor.HAND);
				image.setScaleX(1.5);
				image.setScaleY(1.5);
				characterPane.add(image, i, j);
			}
		}
	}

	private int getIndexFrom2dCoordinates(int i, int j, int width) {
		return i * width + j;
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
		priceLabel.setText(LABEL_DEFAULT_TEXT);
	}

	private class ClickHandler implements EventHandler<MouseEvent> {
		private final Item item;
		private final boolean selling;

		public ClickHandler(Item item, boolean selling) {
			this.item = item;
			this.selling = selling;
		}

		@Override
		public void handle(MouseEvent arg0) {
			System.out.println("Clicked on " + item.toString());
			if (item instanceof BasicItem) {
				if (selling) {
					System.out.println("Selling " + item.toString());
					shop.sell((BasicItem) item);
				} else {
					System.out.println("Buying " + item.toString());
					shop.buy((BasicItem) item);
				}
				updateShopGridPanes();
			}
		}
	}

	private class HoverListener implements ChangeListener<Boolean> {
		private final Item item;
		private final boolean selling;

		public HoverListener(Item item, boolean selling) {
			this.item = item;
			this.selling = selling;
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue) {
				if (item instanceof BasicItem) {
					priceLabel.setText(String.format("Click to %s for %d gold", selling ? "sell" : "buy",
							selling ? ((BasicItem) item).getSellPrice() : ((BasicItem) item).getBuyPrice()));
				} else {
					priceLabel.setText(String.format("This item can't be %s", selling ? "sold" : "bought"));
				}
			} else {
				priceLabel.setText(ShopController.LABEL_DEFAULT_TEXT);
			}

		}
	}
}
