package sporting.business;

import java.util.List;

import sporting.domain.Sala;

public interface SalaService {
 List<Sala> findAllSale() throws BusinessException;
}
