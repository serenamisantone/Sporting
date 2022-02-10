package sporting;

import sporting.view.ViewDispatcher;
import javafx.application.Application;
import javafx.stage.Stage;

public class SportingApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		  ViewDispatcher dispatcher = ViewDispatcher.getInstance();
	        dispatcher.loginView(stage);
		
	}

}
