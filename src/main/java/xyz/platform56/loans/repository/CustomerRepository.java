package xyz.platform56.loans.repository;

import xyz.platform56.loans.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long>, CustomerRepositoryCustom {


}
