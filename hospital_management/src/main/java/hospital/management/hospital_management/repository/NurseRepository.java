package hospital.management.hospital_management.repository;


import hospital.management.hospital_management.domain.NurseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NurseRepository extends JpaRepository<NurseEntity,Long> , JpaSpecificationExecutor<NurseEntity> {
    Integer countByUser_IsActive(Boolean isActive);
    List<NurseEntity> findAllByUser_IsActive(Boolean isActive);
}
