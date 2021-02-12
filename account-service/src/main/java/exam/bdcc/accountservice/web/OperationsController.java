package exam.bdcc.accountservice.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import exam.bdcc.accountservice.entities.Compte;
import exam.bdcc.accountservice.entities.Operation;
import exam.bdcc.accountservice.feign.ClientRest;
import exam.bdcc.accountservice.model.CompteEtat;
import exam.bdcc.accountservice.model.OperationType;
import exam.bdcc.accountservice.repositories.CompteRepository;
import exam.bdcc.accountservice.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.persistence.Column;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class OperationsController {
    private CompteRepository compteRepository;
    private OperationRepository operationRepository;
    private ClientRest clientRestClient;



    public OperationsController(CompteRepository compteRepository, OperationRepository operationRepository, ClientRest clientRestClient) {
        this.compteRepository = compteRepository;
        this.operationRepository = operationRepository;
        this.clientRestClient = clientRestClient;
    }




    @Transactional
    @PostMapping("/versement")
    public Operation versementRetrait(@RequestBody CompteVersement compteVersement) {
        Operation operation = new Operation();
        operation.setMontant(compteVersement.getMontant());
        operation.setType(compteVersement.getType());
        operationRepository.save(operation);
        Compte compte = compteRepository.getOne(compteVersement.getCompteId());
        operation.setCompte(compte);
        if (compteVersement.getType().equals(OperationType.CREDIT))
            compteVersement.setMontant(compteVersement.getMontant() * -1);
        compte.setSolde(compteVersement.getMontant() + compte.getSolde());
        if (compteVersement.getDate() != null)
            operation.setDate(compteVersement.getDate());
        else operation.setDate(LocalDateTime.now());
        compteRepository.save(compte);
        return operation;
    }

    @Transactional
    @PostMapping("/virement")
    public void virement(@RequestBody Virement virement) {
        Compte compteSource = compteRepository.getOne(virement.getCompteIdSource());
        Compte compteDest = compteRepository.getOne(virement.getCompteIdDest());
        Operation sourceOperation = new Operation(null, virement.getDate(), virement.getMontant(), OperationType.CREDIT, compteSource);
        Operation destOperation = new Operation(null, virement.getDate(), virement.getMontant(), OperationType.DEBIT, compteDest);
        compteSource.setSolde(compteSource.getSolde() - virement.getMontant());
        compteSource.setSolde(compteSource.getSolde() + virement.getMontant());
        List<Operation> operationList = new ArrayList<>();
        operationList.add(sourceOperation);
        operationList.add(destOperation);
        List<Compte> compteList = new ArrayList<>();
        compteList.add(compteDest);
        compteList.add(compteSource);
        compteRepository.saveAll(compteList);
        operationRepository.saveAll(operationList);
    }
    @Transactional
    @PostMapping("/compte/{id}/activate")
    public void activateCompte(@PathVariable Long id){
        Compte compte = compteRepository.getOne(id);
        compte.setEtat(CompteEtat.ACTIVE);
        compteRepository.save(compte);
    }

    @Transactional
    @PostMapping("/compte/{id}/suspend")
    public void suspendCompte(@PathVariable Long id){
        Compte compte = compteRepository.getOne(id);
        compte.setEtat(CompteEtat.SUSPENDED);
        compteRepository.save(compte);
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CompteVersement {
    private Double montant;
    private Long compteId;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime date;
    private String type;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Virement {
    private Double montant;
    private Long compteIdSource;
    private Long compteIdDest;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime date;
}
