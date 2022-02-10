package sporting.business;

import java.util.List;


import sporting.domain.Utente;


public interface UtenteService {
	Utente authenticate(String username, String password) throws UtenteNotFoundException, BusinessException;

	Utente findUtenteById(Integer Id) throws BusinessException;

	
}
