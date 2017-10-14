package xyz.platform56.loans.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.platform56.loans.entity.IdentificationEntity;

@Repository
public interface IdentificationRepository extends CrudRepository<IdentificationEntity, Long> {


}
