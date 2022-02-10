package sporting.business;

import sporting.business.impl.file.FileSportingBusinessFactoryImpl;

public abstract class SportingBusinessFactory {
	

	private static SportingBusinessFactory factory = new FileSportingBusinessFactoryImpl();
	
	public static SportingBusinessFactory getInstance() {
		return factory;
	}
	
	public abstract UtenteService getUtenteService();

	
}