package application;

import java.awt.Shape;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import com.sun.prism.paint.Color;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Controller implements Initializable{
	
	@FXML
	private Rectangle blueSpaceShuttle;
	
	@FXML
	private Rectangle redSpaceShuttle;
	
	@FXML
	private Rectangle greenSpaceShuttle;
	
	ArrayList<Rectangle> arri = new ArrayList<>();
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		arri.add(blueSpaceShuttle);
		arri.add(greenSpaceShuttle);
		arri.add(redSpaceShuttle);
		
		blueSpaceShuttle.setOnMouseEntered(e -> move (blueSpaceShuttle));
		blueSpaceShuttle.setFill(Paint.valueOf("#008888"));
	}
	

	public void move(Rectangle blueSpaceShuttle) {
	        blueSpaceShuttle.setTranslateX(blueSpaceShuttle.getTranslateX() + 4);
	    }

}
