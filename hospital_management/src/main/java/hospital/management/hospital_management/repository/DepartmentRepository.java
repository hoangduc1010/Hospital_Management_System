package hospital.management.hospital_management.repository;


import hospital.management.hospital_management.domain.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Long>, JpaSpecificationExecutor<DepartmentEntity> {
    DepartmentEntity findByDepartmentNameIgnoreCase(String departmentName);
    List<DepartmentEntity> findByDepartmentNameContaining(String departmentName);
}
