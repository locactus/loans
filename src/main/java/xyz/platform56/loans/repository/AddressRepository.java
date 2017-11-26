package xyz.platform56.loans.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.platform56.loans.entity.PrincipalEntity;

@Repository
public interface AddressRepository extends CrudRepository<PrincipalEntity, Long> {


}
