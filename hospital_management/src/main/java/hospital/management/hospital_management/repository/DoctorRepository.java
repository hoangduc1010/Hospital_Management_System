package hospital.management.hospital_management.repository;

import hospital.management.hospital_management.domain.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity,Long>, JpaSpecificationExecutor<DoctorEntity> {
    boolean existsById(Long id);
    Integer countByUser_IsActive(Boolean isActive);
    List<DoctorEntity> findAllByUser_IsActive(Boolean isActive);

}
