package sporting.business.impl.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import sporting.business.BusinessException;
import sporting.business.LezioneService;
import sporting.domain.Lezione;
import sporting.domain.PersonalTrainer;
import sporting.domain.Specializzazione;

public class FileLezioniServiceImpl implements LezioneService {

	private String lezioniFileName;
	private String personeFileName;
	private String specializzazioniFileName;

	public FileLezioniServiceImpl(String filename1, String filename2, String filenaem3) {
		lezioniFileName = filename1;
		personeFileName = filename2;
		specializzazioniFileName = filenaem3;
	}

	@Override
	public PersonalTrainer findPersonalTrainerById(Integer id) throws BusinessException {
		PersonalTrainer pt = new PersonalTrainer();
		try {
			FileData fileData = Utility.readAllRows(personeFileName);
			// 3,trainer,Mario,Rossi,1
			for (String[] colonne : fileData.getRighe()) {
				if (Integer.parseInt(colonne[0]) == id && colonne[1].equals("trainer")) {
					pt.setId(id);
					pt.setNome(colonne[2]);
					pt.setCognome(colonne[3]);
					pt.setSpecializzazione(findSpecializzazioneById(Integer.parseInt(colonne[4])));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return pt;
	}

	@Override
	public Specializzazione findSpecializzazioneById(Integer id) throws BusinessException {
		Specializzazione sp = new Specializzazione();
		try {
			FileData fileData = Utility.readAllRows(specializzazioniFileName);
			for (String[] colonne : fileData.getRighe()) {
				if (Integer.parseInt(colonne[0]) == id) {
					sp.setId(id);
					sp.setNome(colonne[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return sp;
	}

	@Override
	public List<Lezione> findAllLezioni() throws BusinessException {
		List<Lezione> result = new ArrayList<>();
		try {
			// 2,cs73,11/03/2022,9:00:00,54,2,4
			FileData fileData = Utility.readAllRows(lezioniFileName);
			for (String[] colonne : fileData.getRighe()) {
				Lezione lezione = new Lezione();
				lezione.setId(Integer.parseInt(colonne[0]));
				lezione.setCodice(colonne[1]);
				lezione.setData(LocalDate.parse(colonne[2]));
				lezione.setOrarioInizio(LocalTime.parse(colonne[3]));
				lezione.setCapienza(Integer.parseInt(colonne[4]));
				lezione.setSpecializzazione(findSpecializzazioneById(Integer.parseInt(colonne[5])));
				lezione.setPersonalTrainer(findPersonalTrainerById(Integer.parseInt(colonne[6])));
				if (lezione.getCapienza() > 0)
					result.add(lezione);
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public boolean decreaseCapienzaLezione(Lezione lezione) throws BusinessException {

		try {
			FileData fileData = Utility.readAllRows(lezioniFileName);
			try (PrintWriter writer = new PrintWriter(new File(lezioniFileName))) {
				writer.println(fileData.getContatore());
				for (String[] colonne : fileData.getRighe()) {

					// 2,cs73,11/03/2022,9:00:00,54,2,4
					if (Integer.parseInt(colonne[0]) == lezione.getId()) {
						StringBuilder row = new StringBuilder();
						row.append(lezione.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(lezione.getCodice());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(lezione.getData());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(lezione.getOrarioInizio());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(Integer.parseInt(colonne[4]) - 1);
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(lezione.getSpecializzazione().getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(lezione.getPersonalTrainer().getId());
						writer.println(row.toString());

					} else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, colonne));
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return false;

	}@Override
	public Lezione findLezioneById(Integer id) throws BusinessException {
		Lezione lezione = new Lezione();
		try {
			FileData fileData = Utility.readAllRows(lezioniFileName);
			//1,af56,2022-03-10,10:00,0,1,3
			for (String[] colonne : fileData.getRighe()) {
				if (Integer.parseInt(colonne[0]) == id) {
					lezione.setId(id);
					lezione.setCodice(colonne[1]);
					lezione.setData(LocalDate.parse(colonne[2]));
					lezione.setOrarioInizio(LocalTime.parse(colonne[3]));
					lezione.setCapienza(Integer.parseInt(colonne[4]));
					lezione.setSpecializzazione(findSpecializzazioneById(Integer.parseInt(colonne[5])));
					lezione.setPersonalTrainer(findPersonalTrainerById(Integer.parseInt(colonne[6])));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return lezione;
	}

	

}
