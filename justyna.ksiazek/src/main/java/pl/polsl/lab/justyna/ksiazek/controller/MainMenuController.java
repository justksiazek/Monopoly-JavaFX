package pl.polsl.lab.justyna.ksiazek.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.polsl.lab.justyna.ksiazek.model.Config;
import pl.polsl.lab.justyna.ksiazek.model.PlayerList;

/**
 * FXML Class that controls the main menu.
 *
 * @author Justyna Ksiazek
 * @version 1.0
 * @since 3.0
 */
public class MainMenuController {
    /** object holding game's configuration */
    private Config config = new Config();
    /** object holding list of players */
    private PlayerList players = new PlayerList();

    /**
     * Handles passed parameters and preps config window.
     * @param params passed parameters
     */
    void getParams(List<String> params) throws IOException {
        //here we handle the parameters but i cannot find hot to pass them INTO the app
        int numOfParams = params.size();
        //validate number of players
        if(numOfParams > 0) {
            if (params.get(0).matches("\\d")) {
                if(Integer.parseInt(params.get(0)) == numOfParams-1) {
                    //parameters are ok
                    FileInputStream fileInputStream = new FileInputStream(new File("src\\main\\java\\pl\\polsl\\lab\\justyna\\ksiazek\\view\\config.fxml"));
                    FXMLLoader loader = new FXMLLoader();
                    Parent root = loader.load(fileInputStream);

                    ConfigController configController = loader.getController();
                    configController.input.setText(params.get(0));
                    if(numOfParams == 3) {
                        configController.p1.setText(params.get(1));
                        configController.p2.setText(params.get(2));
                    }
                    if(numOfParams == 4) {
                        configController.p1.setText(params.get(1));
                        configController.p2.setText(params.get(2));
                        configController.p3.setText(params.get(3));
                    }
                    if(numOfParams == 5) {
                        configController.p1.setText(params.get(1));
                        configController.p2.setText(params.get(2));
                        configController.p3.setText(params.get(3));
                        configController.p4.setText(params.get(4));
                    }
                    configController.configuration(config, players);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("AEI Monopoly - configuration");
                    stage.show();
                }
            }
        }  
    }
    
    /** 
     * FXML method handling pressing "Start game" button.
     * Creates and opens monopoly (game) stage.
     * @throws IOException 
     */
    @FXML
    private void startGame() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("src\\main\\java\\pl\\polsl\\lab\\justyna\\ksiazek\\view\\monopoly.fxml"));
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(fileInputStream);

        MonopolyController monopolyController = loader.getController();
        monopolyController.startGame(config, players);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("AEI Monopoly - game");
        stage.show();
    }
    
    /**
     * FXML method handling pressing "Configuration" button.
     * Creates and open config stage.
     * @throws IOException
     */
    @FXML
    private void setOptions() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("src\\main\\java\\pl\\polsl\\lab\\justyna\\ksiazek\\view\\config.fxml"));
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(fileInputStream);

        ConfigController configController = loader.getController();
        configController.configuration(config, players);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("AEI Monopoly - configuration");
        stage.show();
    }
    
    /**
     * FXML method handling pressing "Exit" button.
     * Exits the program.
     */
    @FXML
    private void exitGame() throws IOException {
        Platform.exit();
    }
}
