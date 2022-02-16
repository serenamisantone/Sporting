package sporting.controller;

import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import sporting.business.BusinessException;
import sporting.business.LezioneService;
import sporting.business.PrenotazioneService;
import sporting.business.SportingBusinessFactory;
import sporting.domain.Cliente;
import sporting.domain.Lezione;
import sporting.domain.Prenotazione;
import sporting.view.ViewDispatcher;

public class PrenotazioniController implements Initializable, DataInitializable<Cliente> {
	@FXML
	private TableView<Prenotazione> prenotazioniTable;
	@FXML
	private TableColumn<Prenotazione, String> tipo;

	@FXML
	private TableColumn<Prenotazione, String> specializzazione;

	@FXML
	private TableColumn<Prenotazione, String> data;

	@FXML
	private TableColumn<Prenotazione, String> ora;
	
	@FXML
	private TableColumn<Prenotazione, Button> cancella;
	private ViewDispatcher dispatcher;
	private PrenotazioneService prenotazioneService;
	private Prenotazione prenotazione;

	public PrenotazioniController() {
		dispatcher = ViewDispatcher.getInstance();
		SportingBusinessFactory factory = SportingBusinessFactory.getInstance();
		prenotazioneService = factory.getPrenotazioneService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@Override
	public void initializeData(Cliente cliente) {
		try {
			List<Prenotazione> prenotazione = prenotazioneService.findAllPrenotazioni(cliente);
			ObservableList<Prenotazione> prenotazioneData = FXCollections.observableArrayList(prenotazione);
			prenotazioniTable.setItems(prenotazioneData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}

			cancella.setCellValueFactory((CellDataFeatures<Prenotazione, Button> param)->{
				Button button = new Button("Cancella");
				button.setStyle("-fx-background-color: #C8A2C8");
				button.setOnAction((ActionEvent event) -> {

					try {
						prenotazioneService.CancellaPrenotazione(param.getValue(), cliente);
					} catch (BusinessException e) {
						dispatcher.renderError(e);
					}
				});
				return new SimpleObjectProperty<Button>(button);
			});
				tipo.setCellValueFactory((CellDataFeatures<Prenotazione, String> param) -> {
					if(param.getValue().getLezione()!= null) {
					return new SimpleStringProperty(( param.getValue()).getLezione().toString());
				}else {
					return new SimpleStringProperty(( param.getValue()).getSala().toString());}});
		specializzazione.setCellValueFactory((CellDataFeatures<Prenotazione, String> param) -> {
			if(param.getValue().getLezione()!= null) {
			return new SimpleStringProperty(( param.getValue()).getLezione().getSpecializzazione().getNome());}
			else {
			return new SimpleStringProperty(( param.getValue()).getSala().getSpecializzazione().getNome());}});
		data.setCellValueFactory((CellDataFeatures<Prenotazione, String> param) -> {
			return new SimpleObjectProperty<String>(
					param.getValue().getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		});
		ora.setCellValueFactory((CellDataFeatures<Prenotazione, String> param) -> {
			return new SimpleObjectProperty<String>(
					param.getValue().getOrarioInizio().format(DateTimeFormatter.ISO_LOCAL_TIME));
		});

}
}
	
	


