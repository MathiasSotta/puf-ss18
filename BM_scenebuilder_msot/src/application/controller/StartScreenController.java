package application.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.util.*;

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
                
            }
        });
    }
}
