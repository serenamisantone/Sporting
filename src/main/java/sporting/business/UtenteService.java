package sporting.business;

import java.util.List;


import sporting.domain.Persona;


public interface UtenteService {
	Persona authenticate(String username, String password) throws UtenteNotFoundException, BusinessException;

	Persona findUtenteById(Integer Id) throws BusinessException;

	
}
