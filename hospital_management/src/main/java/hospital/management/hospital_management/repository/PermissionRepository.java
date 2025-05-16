package hospital.management.hospital_management.repository;

import hospital.management.hospital_management.domain.PermissionEntity;
import hospital.management.hospital_management.util.constant.HttpMethodEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity,Long>, JpaSpecificationExecutor<PermissionEntity> {
    PermissionEntity findByApiPathAndMethod(String path, HttpMethodEnum httpMethodEnum);
}
