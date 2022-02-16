package sporting.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sporting.business.BusinessException;
import sporting.business.CapienzaEsauritaException;
import sporting.business.PrenotazioneService;
import sporting.business.SportingBusinessFactory;
import sporting.domain.Prenotazione;
import sporting.view.ViewDispatcher;

public class PrenotazioneSalaController implements Initializable, DataInitializable<Prenotazione> {
	@FXML
	private Label capienzaException;
	@FXML
	private Label errorLabel;
	@FXML
	private DatePicker data;
	@FXML
	private TextField ora;

	private ViewDispatcher dispatcher;
	private PrenotazioneService prenotazioneService;
	private Prenotazione prenotazione;
	public PrenotazioneSalaController() {
		

		dispatcher = ViewDispatcher.getInstance();
		SportingBusinessFactory factory = SportingBusinessFactory.getInstance();
		prenotazioneService = factory.getPrenotazioneService();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void initializeData(Prenotazione prenotazione) {
		this.prenotazione=prenotazione;
		
	}

	@FXML
	void confermaAction(ActionEvent event) {
		try {
			prenotazione.setData(data.getValue());
			prenotazione.setOrarioInizio(LocalTime.parse(ora.getText(), DateTimeFormatter.ISO_LOCAL_TIME));
			prenotazioneService.addPrenotazione(prenotazione);
			
			dispatcher.renderView("sale", prenotazione.getCliente());
			}catch(DateTimeParseException e1) {
				errorLabel.setText("inserisci un formato valido");
				e1.printStackTrace();
			} catch (CapienzaEsauritaException e2) {
				capienzaException.setText("sala piena");
				
				e2.printStackTrace();
			} catch (BusinessException e3) {
				e3.printStackTrace();
			}
			
	}
}
