package sporting.business.impl.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utility {
	
	public static final String SEPARATORE_COLONNA = ",";

	public static final String[] trim(String[] s) {
		for (int i = 0; i < s.length; i++) {
			s[i] = s[i].trim();
		}
		return s;
	}

	public static FileData readAllRows(String filename) throws IOException {
		FileData result = new FileData();
		
		try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
			List<String[]> righe = new ArrayList<>();
			long contatore = Long.parseLong(in.readLine());//prima riga contatore
			result.setContatore(contatore);
			String linea = null;
			while ((linea = in.readLine()) != null) {
				righe.add(trim(linea.split(SEPARATORE_COLONNA)));//legge tutte le altre righe
			}
			result.setRighe(righe);
		} 

		return result;
	}
}
