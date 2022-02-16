package sporting.business.impl.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sporting.business.BusinessException;
import sporting.business.LezioneService;
import sporting.business.SalaService;
import sporting.domain.PersonalTrainer;
import sporting.domain.Sala;

public class FileSaleServiceImpl implements SalaService {
	private String saleFileName;
	private LezioneService lezioniService;

	public FileSaleServiceImpl(String filename, LezioneService lezioniService) {
		this.saleFileName = filename;
		this.lezioniService = lezioniService;
	}

	@Override
	public List<Sala> findAllSale() throws BusinessException {
		List<Sala> result = new ArrayList<>();
		try {
			// 1,fr56,18,3
			FileData fileData = Utility.readAllRows(saleFileName);
			for (String[] colonne : fileData.getRighe()) {
				Sala sala = new Sala();
				sala.setId(Integer.parseInt(colonne[0]));
				sala.setCodice(colonne[1]);
				sala.setCapienza(Integer.parseInt(colonne[2]));
				sala.setTrainer(lezioniService.findPersonalTrainerById(Integer.parseInt(colonne[3])));
				sala.setSpecializzazione(sala.getTrainer().getSpecializzazione());
				result.add(sala);
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}@Override
	public Sala findSalaById(Integer id) throws BusinessException {
		Sala sala = new Sala();
		try {
			FileData fileData = Utility.readAllRows(saleFileName);
			//1,fr56,18,3
			for (String[] colonne : fileData.getRighe()) {
				if (Integer.parseInt(colonne[0]) == id) {
					sala.setId(id);
					sala.setCodice(colonne[1]);
					sala.setCapienza(Integer.parseInt(colonne[2]));
					sala.setSpecializzazione(lezioniService.findSpecializzazioneById(Integer.parseInt(colonne[3])));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return sala;
	}


}
