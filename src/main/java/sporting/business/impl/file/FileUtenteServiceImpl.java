package sporting.business.impl.file;

import java.io.IOException;
import sporting.business.BusinessException;
import sporting.business.UtenteNotFoundException;
import sporting.business.UtenteService;
import sporting.domain.Cliente;
import sporting.domain.Operatore;
import sporting.domain.Utente;


public class FileUtenteServiceImpl implements UtenteService {

	private String utentiFilename;

	public FileUtenteServiceImpl(String utentiFileName) {
		this.utentiFilename = utentiFileName;

	}

	@Override
	public Utente authenticate(String username, String password) throws UtenteNotFoundException, BusinessException {
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				if (colonne[4].equals(username) && colonne[5].equals(password)) {
					Utente utente = null;
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
	public Utente findUtenteById(Integer id) throws BusinessException {

		Utente utente = null;
		try {
			FileData fileData = Utility.readAllRows(utentiFilename);
			for (String[] colonne : fileData.getRighe()) {
				// 2,preparatore,serena,misantone,serenamisantone,passwords,teramo,DISPONIBILE
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
						utente.setUsername(colonne[4]);
						utente.setPassword(colonne[5]);
						utente.setNome(colonne[2]);
						utente.setCognome(colonne[3]);
						
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
