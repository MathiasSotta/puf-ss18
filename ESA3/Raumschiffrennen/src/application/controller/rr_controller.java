package application.controller;

import java.io.File;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;


import javax.swing.text.html.ImageView;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class rr_controller implements Initializable {

    Image Raumschiff = new Image(getClass().getResource("/images/raumschiff.gif").toString());

    @FXML
    private Rectangle raumschiffBlau;

    @FXML
    private Rectangle raumschiffGelb;

    @FXML
    private Rectangle raumschiffRot;

    @FXML
    private Text statusText;

    ArrayList<Rectangle> arri = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        arri.add(raumschiffBlau);
        arri.add(raumschiffGelb);
        arri.add(raumschiffRot);

        raumschiffBlau.setOnMouseEntered(e -> move(raumschiffBlau) );
        raumschiffBlau.setFill(new ImagePattern(Raumschiff, 0, 0, 150, 60, false));
        raumschiffGelb.setFill(new ImagePattern(Raumschiff, 0, 0, 150, 60, false));
        raumschiffRot.setFill(new ImagePattern(Raumschiff, 0, 0, 150, 60, false));
        statusText.setText("Und los gehts");
    }



    public void move(Shape shape) {
        shape.setTranslateX(shape.getTranslateX() + 4);
    }

    public void setText(String s) {
        statusText.setText(s);
    }
}
