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
	public FileAbbonamentiServiceImpl(String filename1,String filename2) {
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
			cliente.setAbbonamento(abbonamento);
			FileData fileData = Utility.readAllRows(personeFileName);
			try (PrintWriter writer = new PrintWriter(new File(personeFileName))) {
				writer.println(fileData.getContatore());
				for (String[] colonne : fileData.getRighe()) {

					if (Long.parseLong(colonne[0]) == cliente.getId()) {
						//1,cliente,cliente,cliente,Camilla,Manari,sciusci2@gmail.com
						StringBuilder row = new StringBuilder();
						row.append(cliente.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append("cliente");
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getUsername());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getPassword());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getNome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getCognome());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getEmail());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(cliente.getAbbonamento().getCodice());
						writer.println(row.toString());
					} else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, colonne));
					}
				}
			}
			}catch (IOException e) {
		e.printStackTrace();
		throw new BusinessException(e);
		}
		
	}
@Override
public Abbonamento findAbbonamentoById(Integer id) throws BusinessException {
	Abbonamento abbonamento = new Abbonamento();
	try {
		FileData fileData = Utility.readAllRows(abbonamentiFileName);
		//1,01,Settimanale,7,30.00
		for (String[] colonne : fileData.getRighe()) {
			if (Integer.parseInt(colonne[0]) == id) {
				abbonamento.setId(id);
				abbonamento.setCodice(colonne[1]);
				abbonamento.setNome(colonne[2]);
				abbonamento.setDurata(Integer.parseInt(colonne[3]));
				abbonamento.setPrezzo(Double.parseDouble(colonne[4]));
			}
		}
	} catch (IOException e) {
		e.printStackTrace();
		throw new BusinessException(e);
	}
	return abbonamento;
}


}



		
	
