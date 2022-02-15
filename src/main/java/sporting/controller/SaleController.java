package sporting.controller;

import java.net.URL;
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
import sporting.business.SalaService;
import sporting.business.SportingBusinessFactory;
import sporting.domain.Cliente;
import sporting.domain.Prenotazione;
import sporting.domain.Sala;
import sporting.view.ViewDispatcher;

public class SaleController implements Initializable, DataInitializable<Cliente> {

	@FXML
	private TableView<Sala> saleTable;

	@FXML
	private TableColumn<Sala, String> codice;

	@FXML
	private TableColumn<Sala, String> specializzazione;

	@FXML
	private TableColumn<Sala, Button> prenota;

	@FXML
	private Label loginLabel;
	private ViewDispatcher dispatcher;
	private SalaService saleService;

	public SaleController() {
		dispatcher = ViewDispatcher.getInstance();
		SportingBusinessFactory factory = SportingBusinessFactory.getInstance();
		saleService = factory.getSaleService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {

			List<Sala> sale = saleService.findAllSale();
			ObservableList<Sala> saleData = FXCollections.observableArrayList(sale);
			saleTable.setItems(saleData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}

	}

	@Override
	public void initializeData(Cliente cliente) {
		if (cliente == null) {
			loginLabel.setText("Effettua il login per prenotare una sala");
		} else {
			prenota.setCellValueFactory((CellDataFeatures<Sala, Button> param) -> {
				Button button = new Button("Prenota");
				button.setStyle("-fx-background-color: #C8A2C8");
				button.setOnAction((ActionEvent event) -> {
					Prenotazione prenotazione = new Prenotazione();
					prenotazione.setSala(param.getValue());
					prenotazione.setCliente(cliente);
					dispatcher.renderView("prenotazioneSala", prenotazione);
				});
				return new SimpleObjectProperty<Button>(button);
			});
		}
		codice.setCellValueFactory(new PropertyValueFactory<>("codice"));
		specializzazione.setCellValueFactory((CellDataFeatures<Sala, String> param) -> {
			return new SimpleStringProperty(param.getValue().getSpecializzazione().getNome());
		});

	}

}