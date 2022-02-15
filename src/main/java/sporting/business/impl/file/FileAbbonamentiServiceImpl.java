package sporting.business.impl.file;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import sporting.business.AbbonamentiService;
import sporting.business.BusinessException;
import sporting.domain.Abbonamento;
import sporting.domain.Cliente;

public class FileAbbonamentiServiceImpl implements AbbonamentiService {
	private String abbonamentiFileName;
	private String personeFileName;
	public FileAbbonamentiServiceImpl(String filename1, String filename2) {
		abbonamentiFileName=filename1;
		personeFileName=filename2;

}
	@Override
	public List<Abbonamento> findAllAbbonamenti() throws BusinessException {
		List<Abbonamento> result = new ArrayList<>();
		try {
			//1,01,Settimanale,7,30.00
			FileData fileData = Utility.readAllRows(abbonamentiFileName);
			for (String[] colonne : fileData.getRighe()) {
				Abbonamento abbonamento = new Abbonamento();
				abbonamento.setId(Integer.parseInt(colonne[0]));
				abbonamento.setCodice(colonne[1]);
				abbonamento.setNome(colonne[2]);
				abbonamento.setDurata(Integer.parseInt(colonne[3]));
				abbonamento.setPrezzo(Double.parseDouble(colonne[4]));
				result.add(abbonamento);
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
	@Override
	public void assegnaAbbonamento(Abbonamento abbonamento, Cliente cliente) throws BusinessException {
		try {
			FileData fileData = Utility.readAllRows(personeFileName);
			try (PrintWriter writer = new PrintWriter(new File(personeFileName))) {
				writer.println(fileData.getContatore());
				for (String[] righe : fileData.getRighe()) {

					if (Long.parseLong(righe[0]) == cliente.getId()) {
						//1,cliente,cliente,cliente,Camilla,Manari
						StringBuilder row = new StringBuilder();
						row.append(cliente.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente);
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getUsername());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getPassword());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getNome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getCognome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getAbbonamento().getId());
						writer.println(row.toString());
					} else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

	}

		
	}

		
	
