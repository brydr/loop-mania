package unsw.loopmania;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BattleResultsController {
    private String enemyList = "";
    private int totalExp;
    private Integer characterHp;
    private int numEnemies[] = {0, 0, 0};
    
    public BattleResultsController(List<Enemy> defeatedEnemies, int characterHp) {
        this.characterHp = characterHp;
        totalExp = 0;
        getEnemyList(defeatedEnemies);
    }
    
    public Stage BattleResults() {
        Stage battleResults = new Stage();
        battleResults.setResizable(false);
        battleResults.initModality(Modality.APPLICATION_MODAL);
        Insets inset = new Insets(20.0f);
        VBox Vbox = new VBox();
        Vbox.setPadding(inset);
        Vbox.setAlignment(Pos.CENTER);
        Vbox.setSpacing(10);
        Vbox.getChildren().add(new Text("You won the battle!"));
                
        GridPane Grid = new GridPane();
        Grid.setHgap(10);
        Grid.setVgap(5);
        Label defLLabel = new Label("You defeated: ");
        defLLabel.setAlignment(Pos.CENTER_LEFT);
        Grid.add(defLLabel, 0, 0);
        Label defRLabel = new Label(listToString());
        defRLabel.setAlignment(Pos.CENTER_RIGHT);
        Grid.add(defRLabel, 1, 0);
        Label expLLabel = new Label("You recieved: ");
        expLLabel.setAlignment(Pos.CENTER_LEFT);
        Grid.add(expLLabel, 0, 1);
        Label expRLabel = new Label(totalExp + " EXP");
        expRLabel.setAlignment(Pos.CENTER_RIGHT);
        Grid.add(expRLabel, 1, 1);
        Label healLLabel = new Label("Health remaining: ");
        healLLabel.setAlignment(Pos.CENTER_LEFT);
        Grid.add(healLLabel, 0, 2);
        Label healRLabel = new Label(characterHp.toString());
        healRLabel.setAlignment(Pos.CENTER_RIGHT);
        Grid.add(healRLabel, 1, 2);
        Vbox.getChildren().add(Grid);
        Scene battleScene = new Scene(Vbox);
        battleResults.setScene(battleScene);
        battleResults.show();
        return battleResults;
    }

    public void getEnemyList(List<Enemy> defeatedEnemies) {
        for (Enemy e : defeatedEnemies) {
            if (e instanceof Slug) {
                numEnemies[0]++;
            }
            if (e instanceof Vampire) {
                numEnemies[1]++;
            }
            if (e instanceof Zombie) {
                numEnemies[2]++;
            }
            addExp(e.getExperienceGain());
        }
    }

    public void addExp(int exp) {
        totalExp += exp;
    }

    public String listToString() {
        if (numEnemies[0] > 0) {
            enemyList = enemyList + numEnemies[0] + " Slug";
            if (numEnemies[0] > 1) {
                enemyList = enemyList + "s\n";
            } else {
                enemyList = enemyList + "\n";
            }
        }
        if (numEnemies[1] > 0) {
            enemyList = enemyList + numEnemies[1] + " Vampire";
            if (numEnemies[1] > 1) {
                enemyList = enemyList + "s\n";
            } else {
                enemyList = enemyList + "\n";
            }
        }
        if (numEnemies[2] > 0) {
            enemyList = enemyList + numEnemies[2] + " Zombie";
            if (numEnemies[2] > 1) {
                enemyList = enemyList + "s\n";
            } else {
                enemyList = enemyList + "\n";
            }
        }
        return enemyList;
    }
}
