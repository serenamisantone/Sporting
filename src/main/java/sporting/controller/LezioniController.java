package sporting.controller;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sporting.business.BusinessException;
import sporting.business.LezioniService;
import sporting.business.SportingBusinessFactory;
import sporting.domain.Lezione;
import sporting.domain.Persona;
import sporting.view.ViewDispatcher;

public class LezioniController implements Initializable, DataInitializable<Persona> {
	@FXML
	private TableView<Lezione> lezioniTable;
	@FXML
	private TableColumn<Lezione, String> codice;

	@FXML
	private TableColumn<Lezione, String> specializzazione;

	@FXML
	private TableColumn<Lezione, String> data;

	@FXML
	private TableColumn<Lezione, String> ora;

	@FXML
	private TableColumn<Lezione, Button> prenota;
@FXML
private Label loginLabel;
	private ViewDispatcher dispatcher;
	private LezioniService lezioniService;

	public LezioniController() {
		dispatcher = ViewDispatcher.getInstance();
		SportingBusinessFactory factory = SportingBusinessFactory.getInstance();
		lezioniService = factory.getLezioniService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {

			List<Lezione> lezione = lezioniService.findAllLezioni();
			ObservableList<Lezione> lezioneData = FXCollections.observableArrayList(lezione);
			lezioniTable.setItems(lezioneData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}

	}

	@Override
	public void initializeData(Persona persona) {
		if(persona==null) {
			loginLabel.setText("Effettua il login per prenotare una lezione");
		}else {
			prenota.setCellValueFactory((CellDataFeatures<Lezione, Button> param)->{
				Button button = new Button("Prenota");
				button.setStyle("-fx-background-color: #C8A2C8");
				button.setOnAction((ActionEvent event) -> {
					//
				});
				return new SimpleObjectProperty<Button>(button);
			});
		}
		codice.setCellValueFactory(new PropertyValueFactory<>("codice"));
		specializzazione.setCellValueFactory((CellDataFeatures<Lezione, String> param) -> {
			return new SimpleStringProperty(param.getValue().getSpecializzazione().getNome());
		});
		data.setCellValueFactory((CellDataFeatures<Lezione, String> param) -> {
			return new SimpleObjectProperty<String>(param.getValue().getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		});
		ora.setCellValueFactory((CellDataFeatures<Lezione, String> param) -> {
			return new SimpleObjectProperty<String>(param.getValue().getOrarioInizio().format(DateTimeFormatter.ISO_LOCAL_TIME));
		});
	}
}
