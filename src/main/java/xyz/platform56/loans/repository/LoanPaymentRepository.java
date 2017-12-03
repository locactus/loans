package xyz.platform56.loans.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.platform56.loans.entity.LoanEntity;
import xyz.platform56.loans.entity.LoanPaymentEntity;

@Repository
public interface LoanPaymentRepository extends CrudRepository<LoanPaymentEntity, Long> {


}
