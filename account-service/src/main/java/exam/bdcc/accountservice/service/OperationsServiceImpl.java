package exam.bdcc.accountservice.service;


import exam.bdcc.accountservice.entities.Compte;
import exam.bdcc.accountservice.entities.Operation;
import exam.bdcc.accountservice.feign.ClientRest;
import exam.bdcc.accountservice.model.*;
import exam.bdcc.accountservice.repositories.CompteRepository;
import exam.bdcc.accountservice.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OperationsServiceImpl implements OperationsService {
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    ClientRest clientRestClient;

    @Override
    public Compte addCompte(Long clientId, String typeCompte) {
        Compte compte = new Compte();
        compte.setEtat(CompteEtat.ACTIVE);
        compte.setSolde(0d);
        compte.setType(typeCompte);
        compte.setDateCreation(LocalDateTime.now());
        compte.setIdClient(clientId);
        compteRepository.save(compte);
        return compte;
    }

    @Override
    public Operation versement(CompteOperation versementOperation) {
        Operation operation = new Operation();
        operation.setMontant(versementOperation.getMontant());
        operation.setType(OperationType.DEBIT);
        operationRepository.save(operation);
        Compte compte = compteRepository.getOne(versementOperation.getCompteId());
        operation.setCompte(compte);
        System.out.println(compte.getSolde());
        System.out.println("operation Montation-->" + operation.getMontant());
        compte.setSolde(versementOperation.getMontant() + compte.getSolde());
        if (versementOperation.getDate() != null)
            operation.setDate(versementOperation.getDate());
        else operation.setDate(LocalDateTime.now());
        compteRepository.save(compte);
        return operation;
    }

    @Override
    public Operation retrait(CompteOperation retraitOperation) {
        Operation operation = new Operation();
        operation.setMontant(retraitOperation.getMontant());
        operation.setType(OperationType.CREDIT);
        operationRepository.save(operation);
        Compte compte = compteRepository.getOne(retraitOperation.getCompteId());
        operation.setCompte(compte);
        compte.setSolde(compte.getSolde() - retraitOperation.getMontant());
        if (retraitOperation.getDate() != null)
            operation.setDate(retraitOperation.getDate());
        else operation.setDate(LocalDateTime.now());
        compteRepository.save(compte);
        return operation;
    }



    @Override
    public List<Operation> virement(CompteVirement virement) {
        Compte compteSource = compteRepository.getOne(virement.getCompteIdSource());
        Compte compteDest = compteRepository.getOne(virement.getCompteIdDest());
        if (virement.getDate() == null) {
            virement.setDate(LocalDateTime.now());
        }
        Operation sourceOperation = new Operation(null, virement.getDate(), virement.getMontant(), OperationType.VIREMENT_CREDIT, compteSource);
        Operation destOperation = new Operation(null, virement.getDate(), virement.getMontant(), OperationType.VIREMENT_DEBIT, compteDest);
        compteSource.setSolde(compteSource.getSolde() - virement.getMontant());
        compteDest.setSolde(compteDest.getSolde() + virement.getMontant());
        List<Operation> operationList = new ArrayList<>();
        operationList.add(sourceOperation);
        operationList.add(destOperation);
        List<Compte> compteList = new ArrayList<>();
        compteList.add(compteDest);
        compteList.add(compteSource);
        compteRepository.saveAll(compteList);
        operationRepository.saveAll(operationList);
        return operationList;
    }


    @Override
    public void activerCompte(Long compteId) {
        Compte compte = compteRepository.getOne(compteId);
        compte.setEtat(CompteEtat.ACTIVE);
        compteRepository.save(compte);
    }

    @Override
    public void suspendreCompte(Long compteId) {
        Compte compte = compteRepository.getOne(compteId);
        compte.setEtat(CompteEtat.SUSPENDED);
        compteRepository.save(compte);
    }
    @Override
    public Page<Operation> getOperationsPaginated(Long compteId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return operationRepository.getOperationsByCompteId(compteId, pageable);
    }

    @Override
    public Client getClientComptes(Long clientId) {
        Client client = clientRestClient.getClientById(clientId);
        client.setComptes(compteRepository.getCompteByIdClient(clientId));

        return client;
    }

    @Override
    public List<Compte> getComptesByClient(Long clientId) {
        return compteRepository.getCompteByIdClient(clientId);
    }

}