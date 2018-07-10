package application.controller;

import application.Main;
import application.manager.ViewManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.util.*;

import javax.swing.text.View;
import java.net.URL;
import java.util.ResourceBundle;

public class StartScreenController implements Initializable {
    @FXML
    private Button startPlaying;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startPlaying.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewManager.getInstance().setView("/views/GameView.fxml");
            }
        });
    }
}