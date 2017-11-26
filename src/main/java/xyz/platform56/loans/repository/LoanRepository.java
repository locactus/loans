package xyz.platform56.loans.repository;

import xyz.platform56.loans.entity.LoanEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends CrudRepository<LoanEntity, Long>, CustomerRepositoryCustom {


}
