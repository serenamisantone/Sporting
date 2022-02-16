package sporting.business;
import sporting.domain.Abbonamento;
import sporting.domain.Cliente;

import java.util.List;

public interface AbbonamentiService {
	List <Abbonamento> findAllAbbonamenti() throws BusinessException;

	 void assegnaAbbonamento(Abbonamento abbonamento, Cliente cliente) throws BusinessException;

	Abbonamento findAbbonamentoById(Integer id) throws BusinessException;
}
