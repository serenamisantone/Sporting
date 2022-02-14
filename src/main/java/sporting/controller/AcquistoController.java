package sporting.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import sporting.business.AbbonamentiService;
import sporting.business.BusinessException;
import sporting.business.SportingBusinessFactory;
import sporting.domain.Abbonamento;
import sporting.domain.Cliente;
import sporting.domain.Persona;
import sporting.view.ViewDispatcher;

public class AcquistoController implements Initializable, DataInitializable<Persona>{ 
	@FXML
	private ComboBox<Abbonamento> acquistaComboBox;
	private ViewDispatcher dispatcher;
	private AbbonamentiService abbonamentiService;
	private Cliente cliente;
	private Abbonamento abbonamento;
	public AcquistoController() {
		SportingBusinessFactory factory = SportingBusinessFactory.getInstance();
		abbonamentiService = factory.getAbbonamentiService();

		dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	public void InizializeData(Cliente cliente) {
	}
	@FXML
	public void acquistaAction(ActionEvent event) {
		try {
			abbonamentiService.assegnaAbbonamento(abbonamento, cliente);
		} catch (BusinessException e) {
			dispatcher.renderError(e);
		}
		
	}



}
