package sporting.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import sporting.domain.Persona;

public class HomeController implements Initializable, DataInitializable<Persona> {

	@FXML
	private Label benvenutoLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void initializeData(Persona utente) {
		StringBuilder testo = new StringBuilder();
		testo.append("Benvenut* ");
		if (utente != null) {
			testo.append(utente.getNome());
			testo.append(" ");
			testo.append(utente.getCognome());
			benvenutoLabel.setText(testo.toString());
		}

	}
}
