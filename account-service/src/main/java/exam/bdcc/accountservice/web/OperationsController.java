package exam.bdcc.accountservice.web;

import exam.bdcc.accountservice.entities.Compte;
import exam.bdcc.accountservice.entities.Operation;
import exam.bdcc.accountservice.model.Client;
import exam.bdcc.accountservice.model.CompteOperation;
import exam.bdcc.accountservice.model.CompteVirement;
import exam.bdcc.accountservice.service.KafkaProducerService;
import exam.bdcc.accountservice.service.OperationsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;


@RestController
@CrossOrigin("*")
public class OperationsController {
    @Autowired
    private OperationsService operationsService;
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Transactional
    @PostMapping("/addCompte")
    public Compte addCompte(@RequestBody NewAccount newAccount) {
        return operationsService.addCompte(newAccount.getClientId(), newAccount.getTypeCompte());
    }

    @Transactional
    @PostMapping("/versement")
    public Operation versment(@RequestBody CompteOperation compteOperation) {
        System.out.println(compteOperation);
        Operation operation = operationsService.versement(compteOperation);
        kafkaProducerService.send("operations", operation);
        return operation;

    }

    @Transactional
    @PostMapping("/retrait")
    public Operation retrait(@RequestBody CompteOperation compteOperation) {
        Operation operation = operationsService.retrait(compteOperation);
        kafkaProducerService.send("operations", operation);
        return operation;
    }

    @Transactional
    @PostMapping("/virement")
    public List<Operation> virement(@RequestBody CompteVirement virement) {
        List<Operation> operations = operationsService.virement(virement);
        operations.forEach(operation -> kafkaProducerService.send("operations", operation));
        return operations;
    }


    @GetMapping("/comptes/{id}/operations/{page}/{size}")
    public Page<Operation> getOperationsPaginated(@PathVariable(name = "id") Long compteId, @PathVariable int page, @PathVariable int size) {
        return operationsService.getOperationsPaginated(compteId, page, size);
    }

    @GetMapping("/clients/{id}/comptes")
    public List<Compte> getClientComptes(@PathVariable(name = "id") Long clientId) {
        return operationsService.getComptesByClient(clientId);
    }

    @GetMapping("/clients/{id}/comptes/full")
    public Client getCompteByClient(@PathVariable(name = "id") Long clientId) {
        return operationsService.getClientComptes(clientId);
    }

    @Transactional
    @PostMapping("/comptes/{id}/activate")
    public void activateCompte(@PathVariable(name = "id") Long compteId) {
        operationsService.activerCompte(compteId);
    }

    @Transactional
    @PostMapping("/compte/{id}/suspend")
    public void suspendCompte(@PathVariable(name = "id") Long compteId) {
        operationsService.activerCompte(compteId);
    }


}

@Data
@NoArgsConstructor
@AllArgsConstructor
class NewAccount {
    private Long clientId;
    private String typeCompte;
}
