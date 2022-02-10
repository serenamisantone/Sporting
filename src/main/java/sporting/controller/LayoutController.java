package sporting.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import sporting.domain.Utente;
import sporting.view.MenuElement;
import sporting.view.ViewDispatcher;
import sporting.domain.Cliente;

public class LayoutController implements Initializable, DataInitializable<Utente> {

	private static final MenuElement MENU_HOME = new MenuElement("Home", "home");
	private static final MenuElement[] MENU_CLIENTI = { new MenuElement("Lezioni", "lezioni"),
			new MenuElement("Sale", "sale"), new MenuElement("Personal Trainers", "personalTrainers"),
			new MenuElement("Abbonamenti", "abbonamenti") };

	@FXML
	private VBox menuBar;
	
	private Utente utente;
	private ViewDispatcher dispatcher;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initializeData(Utente utente) {
		this.utente = utente;
		menuBar.getChildren().addAll(createButton(MENU_HOME));
		menuBar.getChildren().add(new Separator());

		if (utente instanceof Cliente) {
			for (MenuElement menu : MENU_CLIENTI) {
				menuBar.getChildren().add(createButton(menu));
			}
		}
	}

	@FXML
	public void esciAction(MouseEvent event) {
		dispatcher.logout();
	}

	private Button createButton(MenuElement viewItem) {
		Button button = new Button(viewItem.getNome());
		button.setStyle("-fx-background-color: transparent; -fx-font-size: 14;");
		button.setTextFill(Paint.valueOf("white"));
		button.setPrefHeight(10);
		button.setPrefWidth(180);
		button.setOnAction((ActionEvent event) -> {
			dispatcher.renderView(viewItem.getVista(), utente);
		});
		return button;
	}

}
