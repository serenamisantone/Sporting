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
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import sporting.business.AbbonamentiService;
import sporting.business.BusinessException;
import sporting.business.SportingBusinessFactory;
import sporting.domain.Abbonamento;
import sporting.domain.Cliente;
import sporting.domain.Persona;
import sporting.view.ViewDispatcher;

public class AbbonamentiController implements Initializable, DataInitializable<Cliente> {
	@FXML
	private TableView<Abbonamento> abbonamentiTable;
	@FXML
	private TableColumn<Abbonamento, String> codiceTableColumn;

	@FXML
	private TableColumn<Abbonamento, String> tipoTableColumn;

	@FXML
	private TableColumn<Abbonamento, Double> prezzoTableColumn;

	@FXML
	private TableColumn<Abbonamento, Button> acquistaTableColumn;
@FXML
private Label loginLabel;
@FXML
private Label label;
	private ViewDispatcher dispatcher;
	private AbbonamentiService abbonamentiService;
	public AbbonamentiController() {
		dispatcher = ViewDispatcher.getInstance();
		SportingBusinessFactory factory = SportingBusinessFactory.getInstance();
		abbonamentiService = factory.getAbbonamentiService();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {

			List<Abbonamento> abbonamento = abbonamentiService.findAllAbbonamenti();
			ObservableList<Abbonamento> abbonamentoData = FXCollections.observableArrayList(abbonamento);
			abbonamentiTable.setItems(abbonamentoData);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}

	}

	@Override
	public void initializeData(Cliente cliente) {
		if(cliente==null) {
			loginLabel.setText("Effettua il login per acquistare \n un abbonamento");
		}else {
			if(cliente.getAbbonamento()== null) {
			acquistaTableColumn.setCellValueFactory((CellDataFeatures<Abbonamento, Button> param)->{
				Button button = new Button("Acquista");
				button.setStyle("-fx-background-color: #C8A2C8");
				button.setOnAction((ActionEvent event) -> {

					try {
						abbonamentiService.assegnaAbbonamento(param.getValue(), cliente);
						label.setText("Pagamento andato a buon fine");
					} catch (BusinessException e) {
						dispatcher.renderError(e);
					}
				});
				return new SimpleObjectProperty<Button>(button);
			});
		}else {
			label.setText("Abbonamento già in possesso");
		}
		}
		codiceTableColumn.setCellValueFactory(new PropertyValueFactory<>("codice"));
		tipoTableColumn.setCellValueFactory((CellDataFeatures<Abbonamento, String> param) -> {
			return new SimpleStringProperty(param.getValue().getNome());
		});
		prezzoTableColumn.setCellValueFactory((CellDataFeatures<Abbonamento, Double> param) -> {
			return new SimpleObjectProperty<Double>(param.getValue().getPrezzo());
		});
		
	}

}



