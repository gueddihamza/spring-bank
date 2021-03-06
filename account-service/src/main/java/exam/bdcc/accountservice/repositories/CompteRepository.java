package exam.bdcc.accountservice.repositories;

import exam.bdcc.accountservice.entities.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CompteRepository extends JpaRepository<Compte,Long> {
    public List<Compte> getCompteByIdClient(Long idClient);

}
