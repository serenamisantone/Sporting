package sporting.business;

import sporting.domain.Prenotazione;

public interface PrenotazioneService {
 void addPrenotazione(Prenotazione prenotazione) throws BusinessException, CapienzaEsauritaException;
	boolean checkCapienzaSala(Prenotazione prenotazione) throws BusinessException;
}
