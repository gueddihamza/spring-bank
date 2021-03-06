package exam.bdcc.accountservice.repositories;

import exam.bdcc.accountservice.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface OperationRepository extends JpaRepository<Operation, Long> {
    public Page<Operation> getOperationsByCompteId(Long id, Pageable pageable);
}
