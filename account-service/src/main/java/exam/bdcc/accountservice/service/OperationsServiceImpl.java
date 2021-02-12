package exam.bdcc.accountservice.service;

import exam.bdcc.accountservice.entities.Compte;
import exam.bdcc.accountservice.entities.Operation;
import exam.bdcc.accountservice.feign.ClientRest;
import exam.bdcc.accountservice.model.CompteEtat;
import exam.bdcc.accountservice.model.CompteOperation;
import exam.bdcc.accountservice.model.OperationType;
import exam.bdcc.accountservice.repositories.CompteRepository;
import exam.bdcc.accountservice.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

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
    public Compte getCompteByClient(Long clientId) {
        Compte compte = compteRepository.getCompteByIdClient(clientId);
        compte.setClient(clientRestClient.getClientById(clientId));
        return compte;
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
}