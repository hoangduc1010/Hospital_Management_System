package hospital.management.hospital_management.repository;


import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.util.constant.DepartmentEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Long>, JpaSpecificationExecutor<DepartmentEntity> {
    DepartmentEntity findByDepartmentName(DepartmentEnum departmentName);
}
