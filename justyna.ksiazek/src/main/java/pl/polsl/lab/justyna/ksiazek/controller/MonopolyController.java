package pl.polsl.lab.justyna.ksiazek.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import pl.polsl.lab.justyna.ksiazek.model.Board;
import pl.polsl.lab.justyna.ksiazek.model.Card;
import pl.polsl.lab.justyna.ksiazek.model.Config;
import pl.polsl.lab.justyna.ksiazek.model.Dice;
import pl.polsl.lab.justyna.ksiazek.model.ExtraCard;
import pl.polsl.lab.justyna.ksiazek.model.Field;
import pl.polsl.lab.justyna.ksiazek.model.FieldCard;
import pl.polsl.lab.justyna.ksiazek.model.Lambda;
import pl.polsl.lab.justyna.ksiazek.model.Player;
import pl.polsl.lab.justyna.ksiazek.model.PlayerList;

/**
 * FXML Class that controls the main game.
 *
 * @author Justyna Ksiazek
 * @version 1.0
 * @since 3.0
 */
public class MonopolyController {
    /** FXML main pane */
    @FXML
    private Pane pane;
    /** FXML button for ending the round */
    @FXML
    private Button endRoundBtn;
    /** FXML label with current player's name */
    @FXML
    private Label playerName;
    /** FXML label with current player's resources */
    @FXML
    private Label playerRes;
    /** FXML button for throwing the dice */
    @FXML
    private Button diceBtn;
    /** FXML label with currently rolled number */
    @FXML
    private Label rolledNum;
    /** FXML label with current field's name */
    @FXML
    private Label name;
     /** FXML label with current field's description */
    @FXML
    private Label desc;
     /** FXML label with current field's value */
    @FXML
    private Label value;
     /** FXML label with current field's penalty */
    @FXML
    private Label penalty;
     /** FXML label with current field's owner */
    @FXML
    private Label owner;
     /** FXML label with current field's cost of building */
    @FXML
    private Label buildingCost;
     /** FXML label with current field's number of built buildings */
    @FXML
    private Label numOfBuildings;
    /** FXML button for buying the current field */
    @FXML
    private Button buyBtn;
    /** FXML button for fortyfying the current field with buildings */
    @FXML
    private Button fortifyBtn;
    /** FXML text field for specyfying the number of buildings to build for fortyfying */
    @FXML
    private TextField fortifyText;
    /** FXML circle symbolising 1st players pawn */
    @FXML
    private Circle pin0;
    /** FXML circle symbolising 2nd players pawn */
    @FXML
    private Circle pin1;
    /** FXML circle symbolising 3rd players pawn */
    @FXML
    private Circle pin2;
    /** FXML circle symbolising 4th players pawn */
    @FXML
    private Circle pin3;
 
    /** object holding game's configuration */
    private Config config;
    /** object holding game board */
    private Board board = new Board();
    /** object holding game's dice */
    private Dice dice = new Dice();
    /** object holding list of players */
    private PlayerList players;
    /** object holding currently playing player */
    private Player activePlayer;
    /** object holding current field on board */
    private Field field;
    
    /** active player's number in player list */
    private int playerNum = -1;
    

    /**
     * FXML method handling pressing endRoundBtn button.
     * Ends the round by changing the player and resets information on board. Checks if game has ended.
     * @throws IOException 
     */
    @FXML
    private void endRound() throws IOException {
        //disable possibility of ending round without moving
        endRoundBtn.setDisable(true);
        buyBtn.setDisable(true);
        fortifyBtn.setDisable(true);
        fortifyText.setDisable(true);
        //clear info about field
        name.setText("");
        desc.setText("");
        value.setText("");
        penalty.setText("");
        owner.setText("");
        buildingCost.setText("");
        numOfBuildings.setText("");
        //check if player has gone bankrupt
        if(activePlayer.getResources() < 1) {
            endGame();
        }
        //next player
        playerNum++;
        //if theres no next player come back to first player
        if(playerNum >= config.getNumberOfPlayers()) {
            playerNum = 0;
        }
        //assign active player & start his turn
        activePlayer = players.getPlayers().get(playerNum);
        turn();
    }
    
    /**
     * FXML method handling pressing diceBtn button.
     * Rolls the dice and sets player and their pawn on new field.
     */
    @FXML
    private void rollDice() {
        //roll dice
        int result = dice.roll();
        //show rolled number
        rolledNum.setText("" + result);
        //calculate player position
        Lambda lambda = new Lambda();
        int position = lambda.countFields(activePlayer.getPosition(), result, board.getNumOfFields());
        //if passed START field add 250 to players resources
        if(activePlayer.getPosition() > position) {
            int newRes = activePlayer.getResources() + 250;
            activePlayer.setResources(newRes);
            String resTxt = playerRes.getText();
            playerRes.setText(resTxt + " + (250) -> " + newRes);
        }
        activePlayer.setPosition(position);
        //get field
        field = board.getField(position);
        //move players pin on board
        double x = field.getX() + 50;
        double y = field.getY() + 50;
        if(playerNum == 0) {
            pin0.setLayoutX(x - 25);
            pin0.setLayoutY(y - 25);
        }
        if(playerNum == 1) {
            pin1.setLayoutX(x + 25);
            pin1.setLayoutY(y + 20);
        }
        if(playerNum == 2) {
            pin2.setLayoutX(x - 25);
            pin2.setLayoutY(y + 25);
        }
        if(playerNum == 3) {
            pin3.setLayoutX(x + 25);
            pin3.setLayoutY(y - 25);
        }
        //deactivate dice-rolling & handle field
        diceBtn.setDisable(true);
        field();
    }
    
    /**
     * FXML method handling pressing buyBtn button.
     * Handles buying of the field.
     */
    @FXML
    private void buyField() {
        //deactivate button for buying fields
        buyBtn.setDisable(true);
        //get fields card
        FieldCard card = board.getPurchaseCard(field.getName());
        //pay for the field
        int newRes = activePlayer.getResources() - card.getValue();
        activePlayer.setResources(newRes);
        //update resources
        String resTxt = playerRes.getText();
        playerRes.setText(resTxt + " + (-" + card.getValue() + ") -> " + newRes);
        //set ownership
        field.setOwner(activePlayer.getName());
        activePlayer.setOwnedFields(card);
    }
    
    /**
     * FXML method handling pressing fortifyBtn button.
     * Validates value put into program by player via fortifyText and handles buying buildings.
     * @throws IOException
     */
    @FXML
    private void fortifyField() throws IOException {
        //take value form text field
        String text = fortifyText.getText();
        //if value in text field is wrong show alert and do nothing
        if(!text.matches("\\d")) {
            showAlert("Number of buildings must be a number!");
            return;
        }
        int number = Integer.parseInt(text);
        if(number < 1 || number > 5) {
            showAlert("Number of buildings out of bounds [1-5]");
            return;
        }
        //deactivate fortifying
        fortifyBtn.setDisable(true);
        fortifyText.setDisable(true);
        fortifyText.setText("");
        //if future number of buildings is bigger than max, add maximal possible amount of buildings
        if((number + field.getObjectCount()) > 5) {
            number = 5 - field.getObjectCount();
            showAlert("Number of buildings exceeds maximum - only " + number + " buildings will be added");
        }
        //if number of buildings is right then add them to field
        FieldCard card = board.getPurchaseCard(field.getName());
        //pay for the buildings
        int price = number * card.getObjectValue();
        int newRes = activePlayer.getResources() - price;
        activePlayer.setResources(newRes);
        //update resources
        String resTxt = playerRes.getText();
        playerRes.setText(resTxt + " + (-" + price + ") -> " + newRes);
        //update field info
        field.setObjectCount(number);
        card.setPenalty(field.getObjectCount());
    }
    
    
    /**
     * Handles showing alert window with custom message.
     * @param message message to show in alert
     * @throws IOException
     */
    private void showAlert(String message) throws IOException  {
        FileInputStream fileInputStream = new FileInputStream(new File("src\\main\\java\\pl\\polsl\\lab\\justyna\\ksiazek\\view\\alert.fxml"));
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(fileInputStream);

        AlertController alertController = loader.getController();
        alertController.showMessage(message);

        Stage alertStage = new Stage();
        alertStage.setScene(new Scene(root));
        alertStage.setTitle("AEI Monopoly - alert");
        alertStage.show();
    }
    
    /** 
     * Handles starting the game by setting up configuration and players. 
     * @param sentConfig configuration sent from menu stage
     * @param sentPlayers players sent from menu stage
     * @throws IOException 
     */
    void startGame(Config sentConfig, PlayerList sentPlayers) throws IOException {
        config = sentConfig;
        if (config.getNumberOfPlayers() == 0) {
            showAlert("Players not configured, please set it up in Configuration");
            Stage stage = (Stage)pane.getScene().getWindow();
            stage.close();
        }
        players = sentPlayers;
        setOrder();
        activePlayer = players.getPlayers().get(0);
        endRound();
    }
    
    /**
     * Draws order of players in player list.
     */
    private void setOrder() { 
        //draw random number
        players.getPlayers().forEach(player -> {
            player.setResources(dice.roll());
        });
        //sort players by drawn number
        Collections.sort(players.getPlayers(), Player.sortByResources);
        //set resources for players
        players.getPlayers().forEach(player -> {
            player.setResources(1100);
            player.setPosition(0);
        });
    }
    
    /**
     * Handles playing turn.
     */
    private void turn() {
        //show player info
        playerName.setText(activePlayer.getName());
        playerRes.setText("" + activePlayer.getResources());
        //enable dice-rolling
        diceBtn.setDisable(false);
    }
    
    /**
     * Checks the field and acts upon it - chooses course of action by chcecking field's type.
     */
    private void field() {
        //show field information
        name.setText(field.getName());
        if(field.getType().equals("Chance")) {
            //draw a chance card
            Card card = board.getChanceCards(dice);
            //show card information
            desc.setText(card.getDescription());
            value.setText("value: " + card.getValue());
            //update player resources by card value
            int newRes = activePlayer.getResources() + card.getValue();
            activePlayer.setResources(newRes);
            //update resources
            String resTxt = playerRes.getText();
            playerRes.setText(resTxt + " + (" + card.getValue() + ") -> " + newRes);
            //activate end of turn possibility
            endRoundBtn.setDisable(false);
        }
        else if(field.getType().equals("Extra")) {
            //get extra field card
            ExtraCard card = board.getExtraCard(field.getName());
            //show field information form card
            desc.setText(card.getDescription());
            value.setText("value: " + card.getValue());
            //update player resources by card 
            int newRes = activePlayer.getResources() + card.getValue();
            activePlayer.setResources(newRes);
            //update resources
            String resTxt = playerRes.getText();
            playerRes.setText(resTxt + " + (" + card.getValue() + ") -> " + newRes);
            //activate end of turn possibility
            endRoundBtn.setDisable(false);
        }
        else {
            //get field card
            FieldCard card = board.getPurchaseCard(field.getName());
            //show field information form card
            desc.setText(card.getDescription());
            value.setText("value: " + card.getValue());
            penalty.setText("penalty: " + card.getPenalty());
            owner.setText("owner: " + field.getOwner());
            buildingCost.setText("building cost: " + card.getObjectValue());
            numOfBuildings.setText("number of buildings: " + field.getObjectCount());
            //if field belongs to no one enable buy possibility
            if (field.getOwner().equals("")) {
                buyBtn.setDisable(false);
            }
            //if field belongs to someone pay penalty
            else if (!field.getOwner().equals(activePlayer.getName())) {
                payPenalty(card, players.findPlayer(field.getOwner()));
            }
            //if field belongs to player and dosent have max number of buildings enable fortification option
            else if (field.getOwner().equals(activePlayer.getName()) && field.getObjectCount() < 5) {
                fortifyBtn.setDisable(false);
                fortifyText.setDisable(false);
            }
            //activate end of turn possibility
            endRoundBtn.setDisable(false);
        }
    }

    /**
     * Handles paying penalty for stepping on someone's else field.
     * @param card card of the field player stepped on
     * @param opponent owner of the stepped-on field
     */
    void payPenalty (FieldCard card, Player opponent) {
        //subtract penalty from player's resources
        int newRes = activePlayer.getResources() - card.getPenalty();
        activePlayer.setResources(newRes);
        //update resources
        String resTxt = playerRes.getText();
        playerRes.setText(resTxt + " + (-" + card.getPenalty() + ") -> " + newRes);
        //add penalty to owner's resources
        opponent.setResources(opponent.getResources() + card.getPenalty());
    }
    
    /**
     * Handles ending of the game. Opens ranking window.
     * @throws IOException 
     */
    void endGame() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("src\\main\\java\\pl\\polsl\\lab\\justyna\\ksiazek\\view\\ranking.fxml"));
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(fileInputStream);

        RankingController rankingController = loader.getController();
        rankingController.viewRanking(players);

        Stage rankingStage = new Stage();
        rankingStage.setScene(new Scene(root));
        rankingStage.setTitle("AEI Monopoly - ranking");
        rankingStage.show();
        
        Stage stage = (Stage)pane.getScene().getWindow();
        stage.close();
    }
}
