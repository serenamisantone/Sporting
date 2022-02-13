package sporting.business.impl.file;

import java.io.IOException;
import sporting.business.BusinessException;
import sporting.business.UtenteNotFoundException;
import sporting.business.UtenteService;
import sporting.domain.Cliente;
import sporting.domain.Operatore;
import sporting.domain.Persona;


public class FileUtenteServiceImpl implements UtenteService {

	private String utentiFilename;

	public FileUtenteServiceImpl(String utentiFileName) {
		this.utentiFilename = utentiFileName;

	}

	@Override
	public Persona authenticate(String username, String password) throws UtenteNotFoundException, BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				//1, cliente, cliente,cliente,Amleto,Di Salle
				if (colonne[2].equals(username) && colonne[3].equals(password)) {
					Persona utente = null;
					// colonna[1] identifica il ruolo
					switch (colonne[1]) {
					case "operatore":
						utente = new Operatore();
						break;
					
					case "cliente":
						utente = new Cliente();
						((Cliente) utente).setEmail(colonne[6]);
						
						break;
					default:
						break;
					}
					if (utente != null) {
						utente.setId(Integer.parseInt(colonne[0]));
						utente.setUsername(username);
						utente.setPassword(password);
						utente.setNome(colonne[2]);
						utente.setCognome(colonne[3]);
						
					} else {
						throw new BusinessException("errore nella lettura del file");
					}

					return utente;
				}
			}
			throw new UtenteNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	


	@Override
	public Persona findUtenteById(Integer id) throws BusinessException {

		Persona utente = null;
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				// 1, cliente, cliente,cliente,Amleto,Di Salle
				if (Integer.parseInt(colonne[0]) == id) {

					// colonna[1] identifica il ruolo
					switch (colonne[1]) {
					case "operatore":
						utente = new Operatore();
						break;
					
					case "cliente":
						utente = new Cliente();
						break;
					default:
						break;
					}
					if (utente != null) {
						utente.setId(Integer.parseInt(colonne[0]));
						utente.setUsername(colonne[2]);
						utente.setPassword(colonne[3]);
						utente.setNome(colonne[4]);
						utente.setCognome(colonne[5]);
						
					} else {
						throw new BusinessException("errore nella lettura del file");
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return utente;
	}

}
