package hospital.management.hospital_management.repository;

import hospital.management.hospital_management.domain.FinanceDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceDetailRepository extends JpaRepository<FinanceDetailEntity,Long>, JpaSpecificationExecutor<FinanceDetailEntity> {
}
