package sporting.business.impl.file;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import sporting.business.BusinessException;
import sporting.business.LezioniService;
import sporting.domain.Lezione;
import sporting.domain.PersonalTrainer;
import sporting.domain.Specializzazione;

public class FileLezioniServiceImpl implements LezioniService {

	private String lezioniFileName;
	private String personeFileName;
	private String specializzazioniFileName;
	public FileLezioniServiceImpl(String filename1, String filename2, String filenaem3) {
		lezioniFileName=filename1;
		personeFileName=filename2;
		specializzazioniFileName=filenaem3;
	}
	@Override
	public PersonalTrainer findPersonalTrainerById(Integer id) throws BusinessException {
		PersonalTrainer pt = new PersonalTrainer();
		try {
			FileData fileData = Utility.readAllRows(personeFileName);
			//3,trainer,Mario,Rossi,1
			for (String[] colonne : fileData.getRighe()) {
				if (Integer.parseInt(colonne[0]) == id && colonne[1].equals("trainer")) {
					pt.setId(id);
					pt.setNome(colonne[2]);
					pt.setCognome(colonne[3]);
					pt.setSpecializzazione(findSpecializzazioneById(Integer.parseInt(colonne[4])));
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return pt;
	}

	@Override
	public Specializzazione findSpecializzazioneById(Integer id) throws BusinessException {
		Specializzazione sp = new Specializzazione();
		try {FileData fileData = Utility.readAllRows(specializzazioniFileName);
		for (String[] colonne : fileData.getRighe()) {
			if (Integer.parseInt(colonne[0]) == id) {
				sp.setId(id);
				sp.setNome(colonne[1]);
			}
			}
		}catch(IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return sp;
	}
	@Override
	public List<Lezione> findAllLezioni() throws BusinessException {
		List<Lezione> result = new ArrayList<>();
		try {
			//2,cs73,11/03/2022,9:00:00,2,4
			FileData fileData = Utility.readAllRows(lezioniFileName);
			for (String[] colonne : fileData.getRighe()) {
				Lezione lezione = new Lezione();
				lezione.setId(Integer.parseInt(colonne[0]));
				lezione.setCodice(colonne[1]);
				lezione.setData(LocalDate.parse(colonne[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				lezione.setOrarioInizio(LocalTime.parse(colonne[3]));
				lezione.setSpecializzazione(findSpecializzazioneById(Integer.parseInt(colonne[4])));
				lezione.setPersonalTrainer(findPersonalTrainerById(Integer.parseInt(colonne[5])));
				result.add(lezione);
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	

}