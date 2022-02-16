package sporting.controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import sporting.business.BusinessException;
import sporting.business.CapienzaEsauritaException;
import sporting.business.PrenotazioneService;
import sporting.business.SportingBusinessFactory;
import sporting.domain.Prenotazione;
import sporting.view.ViewDispatcher;

public class PrenotazioneLezioneController implements Initializable, DataInitializable<Prenotazione> {

	private ViewDispatcher dispatcher;
	private PrenotazioneService prenotazioneService;
	private Prenotazione prenotazione;

	public PrenotazioneLezioneController() {

		dispatcher = ViewDispatcher.getInstance();
		SportingBusinessFactory factory = SportingBusinessFactory.getInstance();
		prenotazioneService = factory.getPrenotazioneService();
	}

	@FXML
	private Label ora;

	@FXML
	private Label giorno;

	@FXML
	private Label capienzaException;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void initializeData(Prenotazione prenotazione) {
		this.prenotazione = prenotazione;
		ora.setText(prenotazione.getOrarioInizio().toString());
		giorno.setText(prenotazione.getData().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
	}

	@FXML
	void confermaAction(ActionEvent event) {
		try {
			prenotazioneService.addPrenotazione(prenotazione);

		} catch (CapienzaEsauritaException e) {
			capienzaException.setText("Capienza esaurita");
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e1) {

				e1.printStackTrace();
			}
		} catch (BusinessException e) {		
			e.printStackTrace();
		}
		dispatcher.renderView("lezioni", prenotazione.getCliente());
	}
}