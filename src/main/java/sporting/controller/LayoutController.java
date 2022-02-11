package sporting.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import sporting.domain.Persona;
import sporting.view.MenuElement;
import sporting.view.ViewDispatcher;
import sporting.view.ViewException;
import sporting.domain.Cliente;
import sporting.domain.Operatore;

public class LayoutController implements Initializable, DataInitializable<Persona> {

	private static final MenuElement MENU_HOME = new MenuElement("Home", "home");
	private static final MenuElement[] MENU_CLIENTI = { new MenuElement("Lezioni", "lezioni"),
			new MenuElement("Sale", "sale"), new MenuElement("Personal Trainers", "personalTrainers"),
			new MenuElement("Abbonamenti", "abbonamenti") };
	private static final MenuElement[] MENU_OPERATORI = { new MenuElement("Gestione lezioni", "getsioneLezioni"),
			new MenuElement("Gestione sale", "gestioneSale"),
			new MenuElement("Gestione Personal Trainers", "gestionePersonalTrainers"),
			new MenuElement("Gestione Abbonamenti", "gestioneAbbonamenti"),
			new MenuElement("Clienti", "gestioneClienti") };
	private static final MenuElement[] MENU_ANTEPRIMA = { new MenuElement("Lezioni", "lezioni"),
			new MenuElement("Sale", "sale"), new MenuElement("Abbonamenti", "abbonamenti"), };

	@FXML
	private VBox menuBar;
	@FXML
	private ImageView layoutButton;

	private Persona utente;
	private ViewDispatcher dispatcher;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initializeData(Persona utente) {
		this.utente = utente;
		menuBar.getChildren().addAll(createButton(MENU_HOME));
		//menuBar.getChildren().add(new Separator());
		if (utente == null) {
			for (MenuElement menu : MENU_ANTEPRIMA) {
				menuBar.getChildren().add(createButton(menu));
			}
			Image img = new Image("viste/login-icon.png");
			layoutButton.setImage(img);
		}
		if (utente instanceof Cliente) {
			for (MenuElement menu : MENU_CLIENTI) {
				menuBar.getChildren().add(createButton(menu));
			}
		}
		if (utente instanceof Operatore) {
			for (MenuElement menu : MENU_OPERATORI) {
				menuBar.getChildren().add(createButton(menu));
			}
		}
		if(utente!=null) {
			Image img = new Image("viste/logout.png");
			layoutButton.setImage(img);
		}
	}

	@FXML
	public void layoutAction(MouseEvent event) {
		
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
