package sporting.controller;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sporting.business.BusinessException;
import sporting.business.SportingBusinessFactory;
import sporting.business.UtenteNotFoundException;
import sporting.business.UtenteService;
import sporting.domain.Persona;
import sporting.view.ViewDispatcher;

public class LoginController implements Initializable, DataInitializable<Object> {

	@FXML
	private Label loginErrorLabel;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private Button loginButton;
	
	

	private UtenteService utenteService;
	private ViewDispatcher dispatcher;
 

	public LoginController() {
		SportingBusinessFactory factory = SportingBusinessFactory.getInstance();
		utenteService = factory.getUtenteService();
		
		dispatcher = ViewDispatcher.getInstance();
		
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginButton.disableProperty().bind(username.textProperty().isEmpty().or(password.textProperty().isEmpty()));
	}
		
	

	@FXML
	public void loginAction(ActionEvent event) {
		try {
			
			Persona utente = utenteService.authenticate(username.getText(), password.getText());
			dispatcher.loggedIn(utente);
		} catch (UtenteNotFoundException e) {
			loginErrorLabel.setText("Username e/o password errati!");
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}

	}
	
}
