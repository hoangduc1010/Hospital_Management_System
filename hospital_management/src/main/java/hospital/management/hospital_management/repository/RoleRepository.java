package hospital.management.hospital_management.repository;

import hospital.management.hospital_management.domain.RoleEntity;
import hospital.management.hospital_management.util.constant.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long>, JpaSpecificationExecutor<RoleEntity> {
    RoleEntity findByRoleName(RoleEnum roleName);
}
