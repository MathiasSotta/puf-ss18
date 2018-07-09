package application.manager;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class ViewManager extends StackPane {

    private static ViewManager Instance;

    private Node view;
    private Initializable controller;

    public static ViewManager getInstance() {
        if (Instance == null) {
            Instance = new ViewManager();
        }
        return Instance;
    }

    public ViewManager() {
        super();
    }

    public boolean loadView(String resource) {
        try {
            System.out.println(getClass().getResource(resource));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
            System.out.println(getClass().getResource(resource));
            view = fxmlLoader.load();
            controller = fxmlLoader.getController();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean setView(final String resource) {
        if (!loadView(resource)) {
            System.out.println("ViewManager: View could not be loaded " + resource);
        }

        // check if pane already has a view
        // and remove it
        if (!getChildren().isEmpty()) {
            getChildren().remove(0);
        }
        try {
            getChildren().add(view);
        }
        catch (Exception e) {
            System.out.print(e);
        }
        return true;
    }

    public Initializable getController() {
        return controller;
    }
}
