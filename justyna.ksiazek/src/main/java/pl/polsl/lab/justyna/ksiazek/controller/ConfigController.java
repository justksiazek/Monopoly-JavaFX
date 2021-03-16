package pl.polsl.lab.justyna.ksiazek.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.polsl.lab.justyna.ksiazek.model.Config;
import pl.polsl.lab.justyna.ksiazek.model.PlayerList;
import pl.polsl.lab.justyna.ksiazek.model.QuantityException;

/**
 * FXML Class that controls the configuration.
 *
 * @author Justyna Ksiazek
 * @version 1.0
 * @since 3.0
 */
public class ConfigController {
    /** object holding game's configuration */
    Config config;
    /** object holding list of players */
    PlayerList players;
    
    /** FXML button for accepting the configuration */
    @FXML
    private Button done;
    /** FXML text field for specyfying the number of players */
    @FXML
    TextField input;
     /** FXML label for writing warnings end errors in red */
    @FXML
    private Label warning;
    /** FXML text field for specyfying the name of 1st player */
    @FXML
    TextField p1;
    /** FXML text field for specyfying the name of 2nd player */
    @FXML
    TextField p2;
    /** FXML text field for specyfying the name of 3rd player */
    @FXML
    TextField p3;
    /** FXML text field for specyfying the name of 4th player */
    @FXML
    TextField p4;
    /** FXML pane holding fields with player names */
    @FXML
    private Pane subPane;
    
    
    /**
     * Assingns objects sent from main menu stage to local objects
     * @param sentConfig game configuration
     * @param sentPlayers player list
     */
    void configuration(Config sentConfig, PlayerList sentPlayers) {
        config = sentConfig;
        players = sentPlayers;
    }
    
    /** 
     * FXML method handling pressing done button.
     * Sets up the player list by assigning names.
     */
    @FXML
    private void configure() {
        //add players to list
        if(config.getNumberOfPlayers() == 2) {
            players.setPlayer(p1.getText());
            players.setPlayer(p2.getText());
        }
        if(config.getNumberOfPlayers() == 3) {
            players.setPlayer(p1.getText());
            players.setPlayer(p2.getText());
            players.setPlayer(p3.getText());
        }
        if(config.getNumberOfPlayers() == 4) {
            players.setPlayer(p1.getText());
            players.setPlayer(p2.getText());
            players.setPlayer(p3.getText());
            players.setPlayer(p4.getText());
        }
        //close window
        Stage stage = (Stage)done.getScene().getWindow();
        stage.close();
    }
    
    /**
     * FXML method handling typing in input text field.
     * Validates the number, unlocks correct number of slots for player names.
     */
    @FXML
    private void walidateNumber() {
        //disable evetything
        subPane.setDisable(true);
        p1.setDisable(true);
        p2.setDisable(true);
        p3.setDisable(true);
        p4.setDisable(true);
        //get input
        String number = input.getText();
        //if not a number show warning
        if(!number.matches("\\d")) {
            warning.setText("Invalid value, try again");
            return;
        }
        //if a number try to set number of players
        try {
            config.setNumberOfPlayers(Integer.parseInt(number));
        } catch (QuantityException ex) {
            warning.setText(ex.getMessage());
            return;
        }
        //if value is correct delete warnings and enable correct number of fields
        warning.setText("");
        subPane.setDisable(false);
        if (Integer.parseInt(number) == 2) {
            p1.setDisable(false);
            p2.setDisable(false);
        }
        if (Integer.parseInt(number) == 3) {
            p1.setDisable(false);
            p2.setDisable(false);
            p3.setDisable(false);
        }
        if (Integer.parseInt(number) == 4) {
            p1.setDisable(false);
            p2.setDisable(false);
            p3.setDisable(false);
            p4.setDisable(false);
        }
    }
    
    /**
     * FXML method handling typing in p1-p4 text fields.
     * Validates the names, unlocks done button.
     */
    @FXML
    private void walidatePlayer() {
        done.setDisable(true);
        if (config.getNumberOfPlayers() == 2) {
            //check if fields have values
            if(p1.getText().equals("") && p2.getText().equals("")) {
                warning.setText("Set player names");
            }
            if(!p1.getText().equals("") && !p2.getText().equals("")) {
                //check if player names are different
                if(p1.getText().equals(p2.getText())) {
                    warning.setText("Player names must be different");
                }
                //enable adding players to playerlist
                else {
                    warning.setText("");
                    done.setDisable(false);
                }
            }
        }
        if (config.getNumberOfPlayers() == 3) {
            //check if fields have values
            if(p1.getText().equals("") || p2.getText().equals("") || p3.getText().equals("")) {
                warning.setText("Set player names");
            }
            if(!p1.getText().equals("") && !p2.getText().equals("") && !p3.getText().equals("")) {
                //check if player names are different
                if(p1.getText().equals(p2.getText()) || p1.getText().equals(p3.getText()) || p2.getText().equals(p3.getText())) {
                    warning.setText("Player names must be different");
                }
                //enable adding players to playerlist
                else {
                    warning.setText("");
                    done.setDisable(false);
                }
            }
        }
        if (config.getNumberOfPlayers() == 4) {
            //check if fields have values
            if(p1.getText().equals("") || p2.getText().equals("") || p3.getText().equals("") || p3.getText().equals("")) {
                warning.setText("Set player names");
            }
            if(!p1.getText().equals("") && !p2.getText().equals("") && !p3.getText().equals("") && !p4.getText().equals("")) {
                //check if player names are different
                if(p1.getText().equals(p2.getText()) || p1.getText().equals(p3.getText()) || p1.getText().equals(p4.getText()) ||
                    p2.getText().equals(p3.getText()) || p2.getText().equals(p4.getText()) || p3.getText().equals(p4.getText())) {
                    warning.setText("Player names must be different");
                }
                //enable adding players to playerlist
                else {
                    warning.setText("");
                    done.setDisable(false);
                }
            }
        }
    }
}
