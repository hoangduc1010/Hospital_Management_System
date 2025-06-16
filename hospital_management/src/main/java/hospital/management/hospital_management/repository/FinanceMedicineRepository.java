package hospital.management.hospital_management.repository;

import hospital.management.hospital_management.domain.FinanceMedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface FinanceMedicineRepository  extends JpaRepository<FinanceMedicineEntity,Long>, JpaSpecificationExecutor<FinanceMedicineEntity> {
    List<FinanceMedicineEntity> findByBuyInDateBetween(Instant startTime,Instant endTime);
}
