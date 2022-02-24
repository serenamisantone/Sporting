package sporting.business.impl.file;

import java.io.File;

import sporting.business.AbbonamentiService;
import sporting.business.LezioneService;
import sporting.business.PrenotazioneService;
import sporting.business.SalaService;
import sporting.business.SportingBusinessFactory;
import sporting.business.UtenteService;

public class FileSportingBusinessFactoryImpl extends SportingBusinessFactory {
	private UtenteService utenteService;
	private LezioneService lezioniService;
	private AbbonamentiService abbonamentiService;
	private SalaService saleService;
	private PrenotazioneService prenotazioneService;
	private static final String REPOSITORY_BASE = "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "dati";
	private static final String UTENTI_FILE_NAME = REPOSITORY_BASE + File.separator + "persone.txt";
	private static final String LEZIONI_FILE_NAME = REPOSITORY_BASE + File.separator + "lezioni.txt";
	private static final String SPECIALIZZAZIONI_FILE_NAME = REPOSITORY_BASE + File.separator + "specializzazioni.txt";
	private static final String ABBONAMENTI_FILE_NAME = REPOSITORY_BASE + File.separator + "abbonamenti.txt";
	private static final String SALE_FILE_NAME = REPOSITORY_BASE + File.separator + "sale.txt";
	private static final String PRENOTAZIONI_FILE_NAME = REPOSITORY_BASE + File.separator + "prenotazioni.txt";
	public FileSportingBusinessFactoryImpl() {
		abbonamentiService = new FileAbbonamentiServiceImpl(ABBONAMENTI_FILE_NAME, UTENTI_FILE_NAME);
		utenteService = new FileUtenteServiceImpl(UTENTI_FILE_NAME, abbonamentiService);
		lezioniService = new FileLezioniServiceImpl(LEZIONI_FILE_NAME, UTENTI_FILE_NAME, SPECIALIZZAZIONI_FILE_NAME);
		saleService = new FileSaleServiceImpl(SALE_FILE_NAME, lezioniService);
		prenotazioneService= new FilePrenotazioneServiceImpl(PRENOTAZIONI_FILE_NAME, lezioniService, utenteService, saleService);
	}

	@Override
	public UtenteService getUtenteService() {
		return utenteService;
	}

	@Override
	public LezioneService getLezioniService() {
		return lezioniService;
	}

	@Override
	public AbbonamentiService getAbbonamentiService() {
		return abbonamentiService;
	}


	@Override
	public SalaService getSaleService() {
		return saleService;
	}

	@Override
	public PrenotazioneService getPrenotazioneService() {

		return prenotazioneService;
	}
	
	}

