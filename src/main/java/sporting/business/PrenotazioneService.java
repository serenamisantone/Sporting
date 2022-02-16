package sporting.business;

import java.io.IOException;
import java.util.List;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import sporting.domain.Cliente;
import sporting.domain.Prenotazione;

public interface PrenotazioneService {
 void addPrenotazione(Prenotazione prenotazione) throws BusinessException, CapienzaEsauritaException;
	boolean checkCapienzaSala(Prenotazione prenotazione) throws BusinessException;
	void generateQrcode(Prenotazione prenotazione) throws WriterException, IOException,NotFoundException;
	List<Prenotazione> findAllPrenotazioni(Cliente cliente) throws BusinessException;
	void CancellaPrenotazione(Prenotazione prenotazione, Cliente cliente) throws BusinessException;
}
