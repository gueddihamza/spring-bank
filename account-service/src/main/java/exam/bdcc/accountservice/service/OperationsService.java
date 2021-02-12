package exam.bdcc.accountservice.service;

import exam.bdcc.accountservice.entities.Compte;
import exam.bdcc.accountservice.entities.Operation;
import exam.bdcc.accountservice.model.CompteOperation;
import org.springframework.data.domain.Page;

public interface OperationsService {
    public Compte addCompte(Long clientId, String typeCompte);
    public Operation versement(CompteOperation compteOperation);
    public Operation retrait(CompteOperation compteOperation);
    public Compte getCompteByClient(Long clientId);
    public void activerCompte(Long compteId);
    public void suspendreCompte(Long compteId);
}
