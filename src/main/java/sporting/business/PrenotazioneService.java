package sporting.business;

import java.io.IOException;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import sporting.domain.Prenotazione;

public interface PrenotazioneService {
 void addPrenotazione(Prenotazione prenotazione) throws BusinessException, CapienzaEsauritaException;
	boolean checkCapienzaSala(Prenotazione prenotazione) throws BusinessException;
	void generateQrcode(Prenotazione prenotazione) throws WriterException, IOException,NotFoundException;
}
