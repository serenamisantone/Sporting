package sporting.business.impl.file;

import java.io.File;

import sporting.business.SportingBusinessFactory;
import sporting.business.UtenteService;



public class FileSportingBusinessFactoryImpl extends SportingBusinessFactory {
	private UtenteService utenteService;
	
	private static final String REPOSITORY_BASE = "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "dati";
	private static final String UTENTI_FILE_NAME = REPOSITORY_BASE + File.separator + "utenti.txt";
	
	public FileSportingBusinessFactoryImpl() {
		utenteService = new FileUtenteServiceImpl(UTENTI_FILE_NAME);		
		
	}

	@Override
	public UtenteService getUtenteService() {
		return utenteService;
	}

	

}
