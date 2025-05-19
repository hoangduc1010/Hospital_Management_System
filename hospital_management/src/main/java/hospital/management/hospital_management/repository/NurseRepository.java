package hospital.management.hospital_management.repository;

import hospital.management.hospital_management.domain.NurseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NurseRepository extends JpaRepository<NurseEntity,Long> , JpaSpecificationExecutor<NurseEntity> {
}
