package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{
	 private Stage primaryStage;
	 private Pane rootLayout;

	@Override
	public void start(Stage primaryStage) throws Exception {
		 this.primaryStage = primaryStage;
	        this.primaryStage.setTitle("StarshipRace");

	        initRootLayout();
	}
	
	 public void initRootLayout() {
	        try {
	            // Load root layout from fxml file.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(Main.class.getResource("RootLayout.fxml"));
	            rootLayout = (Pane) loader.load();

	            // Show the scene containing the root layout.
	            Scene scene = new Scene(rootLayout);
	            primaryStage.setScene(scene);
	            primaryStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public Stage getPrimaryStage() {
	        return primaryStage;
	    }
	 
	 public static void main(String[] args) {
	        launch(args);
	    }
}
