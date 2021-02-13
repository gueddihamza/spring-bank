package exam.bdcc.accountservice.service;

import exam.bdcc.accountservice.entities.Compte;
import exam.bdcc.accountservice.entities.Operation;
import exam.bdcc.accountservice.model.Client;
import exam.bdcc.accountservice.model.CompteOperation;
import exam.bdcc.accountservice.model.CompteVirement;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OperationsService {
    Compte addCompte(Long clientId, String typeCompte);
    Operation versement(CompteOperation compteOperation);
    Operation retrait(CompteOperation compteOperation);
    Page<Operation> getOperationsPaginated(Long compteId, int page, int size);
    Client getClientComptes(Long clientId);
    List<Compte> getComptesByClient(Long clientId);
    void activerCompte(Long compteId);
    void suspendreCompte(Long compteId);
    List<Operation> virement(CompteVirement compteVirement);
}

