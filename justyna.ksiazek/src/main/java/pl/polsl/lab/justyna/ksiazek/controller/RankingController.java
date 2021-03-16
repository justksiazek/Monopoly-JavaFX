package pl.polsl.lab.justyna.ksiazek.controller;

import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pl.polsl.lab.justyna.ksiazek.model.Player;
import pl.polsl.lab.justyna.ksiazek.model.PlayerList;

/**
 * FXML Class that controls the ranking stage.
 *
 * @author Justyna Ksiazek
 * @version 1.0
 * @since 3.0
 */
public class RankingController {    
     /** FXML button for exiting the game */
    @FXML
    private Button exitBtn;
     /** FXML table view for showing the ranking */
    @FXML
    private TableView table;
    
    /** list of players */
    private PlayerList players;
    
    
     /**
     * FXML method handling pressing exitBtn button.
     * Quits the game, returns to min menu.
     */
    @FXML
    private void exitGame() {
        Stage stage = (Stage)exitBtn.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Views the ranking in table view.
     * @param sentPlayers players sent from game
     */
    void viewRanking(PlayerList sentPlayers) {
        players = sentPlayers;
        Collections.sort(players.getPlayers(), Player.sortByResources);
        //setup table columns
        TableColumn<Player, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(120);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Player, Integer> resCol = new TableColumn<>("Resources");
        resCol.setMinWidth(120);
        resCol.setCellValueFactory(new PropertyValueFactory<>("resources"));
        //setup table
        table.setItems(getGamers());
        table.getColumns().addAll(nameCol, resCol);
    }
    
    /**
     * Makes an observable list to show.
     * @return finished observable list
     */
    public ObservableList<Player> getGamers() {
        ObservableList<Player> data = FXCollections.observableArrayList();
        for(Player player : players.getPlayers()) {
            data.add(player);
        }
        return data;
    }

}
