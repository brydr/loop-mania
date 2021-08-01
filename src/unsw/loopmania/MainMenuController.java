package unsw.loopmania;

import java.io.IOException;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

/**
 * controller for the main menu. TODO = you could extend this, for example with
 * a settings menu, or a menu to load particular maps.
 */
public class MainMenuController {
    private GameMode selectedGameMode;
    @FXML
    private ChoiceBox<GameMode> gameModeDropDown;

    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;

    public void setGameSwitcher(MenuSwitcher gameSwitcher) {
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     *
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu(selectedGameMode);
    }

    @FXML
    private void initialize() {
        gameModeDropDown.getItems().removeAll(gameModeDropDown.getItems());

        gameModeDropDown.getItems().add(new StandardMode());
        gameModeDropDown.getItems().add(new SurvivalMode());
        gameModeDropDown.getItems().add(new BerserkerMode());
        gameModeDropDown.getItems().add(new ConfusingMode());

        gameModeDropDown.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends GameMode> observable, GameMode oldValue, GameMode newValue) -> {
            selectedGameMode = newValue;
            System.out.println(String.format("Selected %s", selectedGameMode));
        });
        gameModeDropDown.getSelectionModel().select(0);
    }

    public GameMode getGameMode() {
        // TODO
        return selectedGameMode;
    }
}
