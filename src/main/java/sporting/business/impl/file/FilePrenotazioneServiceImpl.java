package sporting.business.impl.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;

import sporting.business.BusinessException;
import sporting.business.CapienzaEsauritaException;
import sporting.business.LezioneService;
import sporting.business.PrenotazioneService;
import sporting.domain.Prenotazione;

public class FilePrenotazioneServiceImpl implements PrenotazioneService {
	private String prenotazioniFileName;
	private LezioneService lezioneService;


	public FilePrenotazioneServiceImpl(String filename, LezioneService lezioneService) {
		prenotazioniFileName = filename;
		this.lezioneService = lezioneService;
		
	}

	@Override
	public void addPrenotazione(Prenotazione prenotazione) throws BusinessException, CapienzaEsauritaException {
		try {
			if (prenotazione.getLezione() != null) {
				lezioneService.decreaseCapienzaLezione(prenotazione.getLezione());

			} else {
				if (checkCapienzaSala(prenotazione))
					throw new CapienzaEsauritaException();
			}

			FileData fileData = Utility.readAllRows(prenotazioniFileName);
			try (PrintWriter writer = new PrintWriter(new File(prenotazioniFileName))) {
				long contatore = fileData.getContatore();
				writer.println((contatore + 1));
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				StringBuilder row = new StringBuilder();
				row.append(contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(prenotazione.getCliente().getId());
				row.append(Utility.SEPARATORE_COLONNA);
				if (prenotazione.getLezione() != null) {
					row.append("lezione");
					row.append(Utility.SEPARATORE_COLONNA);
					row.append(prenotazione.getLezione().getId());

				} else {
					row.append("sala");
					row.append(Utility.SEPARATORE_COLONNA);
					row.append(prenotazione.getSala().getId());
					row.append(Utility.SEPARATORE_COLONNA);
					row.append(prenotazione.getData());
					row.append(Utility.SEPARATORE_COLONNA);
					row.append(prenotazione.getOrarioInizio());

				}
				writer.println(row.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}

	@Override
	public boolean checkCapienzaSala(Prenotazione prenotazione) throws BusinessException {
		int cont = 50;
		try {
			FileData fileData = Utility.readAllRows(prenotazioniFileName);
			// 1,1,sala,1,data,orario
			for (String[] colonne : fileData.getRighe()) {
				if (colonne[2].equals("sala") && Integer.parseInt(colonne[3]) == prenotazione.getSala().getId()
						&& LocalDate.parse(colonne[4]).equals(prenotazione.getData())
						&& LocalTime.parse(colonne[5]).equals(prenotazione.getOrarioInizio())) {
					cont--;

				}
				if (cont <= 0)
					return true;
			}

		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		return false;

	}

}
