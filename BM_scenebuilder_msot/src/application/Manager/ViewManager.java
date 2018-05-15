package application.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class ViewManager extends StackPane {

    private Node view;

    public ViewManager() {
        super();
    }

    public boolean loadView(String resource) {
        try {
            System.out.println(getClass().getResource(resource));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
            System.out.println(getClass().getResource(resource));
            this.view = fxmlLoader.load();
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
        getChildren().add(view);
        return true;
    }
}
