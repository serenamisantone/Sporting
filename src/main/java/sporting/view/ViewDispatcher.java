package sporting.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sporting.controller.DataInitializable;
import sporting.domain.Persona;


public class ViewDispatcher {
	private static final String FXML_SUFFIX = ".fxml";
	private static final String RESOURCE_BASE = "/viste/";
	private static ViewDispatcher instance = new ViewDispatcher();
	private Stage stage;
	private BorderPane layout;
	
	
	private ViewDispatcher() {
	}

	public static ViewDispatcher getInstance() {
		return instance;
	}
	
	public void loginView(Stage stage) throws ViewException {
        this.stage = stage;
        Parent loginView = loadView("login").getView();
        Scene scene = new Scene(loginView);
        stage.setScene(scene);
        stage.show();
    }
    public void loggedIn(Persona utente) {
        try {
        	View<Persona> layoutView = loadView("layout");
        	DataInitializable<Persona> layoutController = layoutView.getController();
        	layoutController.initializeData(utente);
             layout = (BorderPane) layoutView.getView();
             renderView("home", utente);
             Scene scene = new Scene(layout);
             stage.setScene(scene);
        } catch (ViewException e) {
             renderError(e);
        }
    }

    public void logout() {
        try {
             Parent loginView = loadView("login").getView();
             Scene scene = new Scene(loginView);
             stage.setScene(scene);
        } catch (ViewException e) {
             renderError(e);
        }
    }
 
    
    public <T> void renderView(String viewName, T data) {
        try {
        	View<T> view = loadView (viewName);
            DataInitializable<T> controller = view.getController();
            controller.initializeData(data);
            layout.setCenter(view.getView());
        } catch (ViewException e) {
            renderError(e);
        }
    }
 
    public void renderError(Exception e) {
        e.printStackTrace();
        System.exit(1);
    }

    private <T> View<T> loadView(String viewName) throws ViewException {
        try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource(RESOURCE_BASE + viewName + FXML_SUFFIX));
             Parent parent = (Parent) loader.load();
             return new View<>(parent, loader.getController());
        } catch (IOException ex) {
             throw new ViewException(ex);
        } 
    }
}