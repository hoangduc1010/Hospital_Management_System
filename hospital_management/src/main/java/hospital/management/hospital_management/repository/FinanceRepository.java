package hospital.management.hospital_management.repository;


import hospital.management.hospital_management.domain.FinanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface FinanceRepository extends JpaRepository<FinanceEntity,Long> , JpaSpecificationExecutor<FinanceEntity> {
    List<FinanceEntity> findByBillingDateBetween(Instant startTime,Instant endTime);
}
