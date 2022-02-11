package sporting.business.impl.file;

import java.io.File;

import sporting.business.LezioniService;
import sporting.business.SportingBusinessFactory;
import sporting.business.UtenteService;

public class FileSportingBusinessFactoryImpl extends SportingBusinessFactory {
	private UtenteService utenteService;
	private LezioniService lezioniService;
	private static final String REPOSITORY_BASE = "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "dati";
	private static final String UTENTI_FILE_NAME = REPOSITORY_BASE + File.separator + "persone.txt";
	private static final String LEZIONI_FILE_NAME = REPOSITORY_BASE + File.separator + "lezioni.txt";
	private static final String SPECIALIZZAZIONI_FILE_NAME = REPOSITORY_BASE + File.separator + "specializzazioni.txt";

	public FileSportingBusinessFactoryImpl() {
		utenteService = new FileUtenteServiceImpl(UTENTI_FILE_NAME);
		lezioniService = new FileLezioniServiceImpl(LEZIONI_FILE_NAME, UTENTI_FILE_NAME, SPECIALIZZAZIONI_FILE_NAME);
	}

	@Override
	public UtenteService getUtenteService() {
		return utenteService;
	}

	@Override
	public LezioniService getLezioniService() {
		return lezioniService;
	}

}
