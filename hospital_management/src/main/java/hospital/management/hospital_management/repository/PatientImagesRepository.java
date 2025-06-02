package hospital.management.hospital_management.repository;

import hospital.management.hospital_management.domain.PatientImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientImagesRepository extends JpaRepository<PatientImagesEntity,Long> , JpaSpecificationExecutor<PatientImagesEntity> {
}
