package pl.polsl.lab.justyna.ksiazek.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Class that controls the alert window.
 *
 * @author Justyna Ksiazek
 * @version 1.0
 * @since 3.0
 */
public class AlertController {
    /** FXML button for approving message. */
    @FXML
    private Button okBtn;
    /** FXML label with message to show */
    @FXML
    private Label message;
 
    /**
     * FXML method handling pressing okBtn button.
     * Closes the stage.
     */
    @FXML
    private void close() {
        Stage stage = (Stage)okBtn.getScene().getWindow();
        stage.close();
    }
    
    /** 
     * Shows the message.
     * @param string message to show.
     */
    void showMessage(String string) {
        message.setText(string);
    }
    
    
    
}
