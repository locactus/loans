package xyz.platform56.loans.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.platform56.loans.entity.ScheduleEntity;
import xyz.platform56.loans.entity.SingleLineScheduleEntity;

@Repository
public interface SingleLineScheduleRepository extends CrudRepository<SingleLineScheduleEntity, Long> {
}
