package sporting.business;

import java.util.List;

import sporting.domain.Lezione;
import sporting.domain.PersonalTrainer;
import sporting.domain.Specializzazione;

public interface LezioniService {
	PersonalTrainer findPersonalTrainerById(Integer id)throws BusinessException;
	Specializzazione findSpecializzazioneById (Integer id)throws BusinessException;
	List<Lezione> findAllLezioni() throws BusinessException;
}
