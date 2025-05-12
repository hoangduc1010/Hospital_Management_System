package hospital.management.hospital_management.service;


import hospital.management.hospital_management.domain.RoleEntity;
import hospital.management.hospital_management.repository.RoleRepository;
import hospital.management.hospital_management.util.constant.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;
    public RoleEntity findByRoleName(RoleEnum roleName){
        return this.repository.findByRoleName(roleName);
    }
}
