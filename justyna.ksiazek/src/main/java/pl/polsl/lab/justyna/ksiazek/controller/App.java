package pl.polsl.lab.justyna.ksiazek.controller;

import java.io.File;
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class of the JavaFX app.
 * 
 * @author Justyna Ksiazek
 * @version 1.0
 * @since 3.0
 */
public class App extends Application {
    /**
     * Starts the program by preparing and showing main menu stage.
     * @param stage stage
     * @throws IOException 
     */
    @Override
    public void start(Stage stage) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("src\\main\\java\\pl\\polsl\\lab\\justyna\\ksiazek\\view\\menu.fxml"));
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(fileInputStream);
        
        //Parameters p = this.getParameters();
        //List<String> unnamedParams = p.getUnnamed();
        //testing passed params - i dont know how to pass regurally
        List<String> unnamedParams = new ArrayList();
        unnamedParams.add("2");
        unnamedParams.add("play1");
        unnamedParams.add("play2");
        
        MainMenuController mainMenuController = loader.getController();
        mainMenuController.getParams(unnamedParams);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("AEI Monopoly - menu");
        stage.show();
    }

    /**
     * Main method of the game.
     * 
     * @param args program call parameters
     * args[0] -> number of players
     * args[1...] -> player names
     */
    public static void main(String[] args) {
        launch(args);
    }

}