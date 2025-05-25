package hospital.management.hospital_management.helper;
import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.response.UserResponse;
import hospital.management.hospital_management.repository.DepartmentRepository;
import hospital.management.hospital_management.util.constant.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserServiceHelper {
    private final DepartmentRepository departmentRepository;
    public UserResponse convertToUserResponse(UserEntity userEntity){
        UserResponse userResponse=new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setRoleName(userEntity.getRole().getRoleName());
        userResponse.setDob(userEntity.getDob());
        userResponse.setFullname(userEntity.getFullname());
        userResponse.setPhoneNumber(userEntity.getPhoneNumber());
        userResponse.setGender(userEntity.getGender());
        if(userEntity.getRole().getRoleName().equals(RoleEnum.DOCTOR)){
            userResponse.setDoctorDiploma(userEntity.getDoctor().getDoctorDiploma());
            Set<String> departments=new HashSet<>();
            for(DepartmentEntity departmentEntity:userEntity.getDoctor().getDepartments()){
                DepartmentEntity currentDepartment=this.departmentRepository.findById(departmentEntity.getId()).get();
                departments.add(currentDepartment.getDepartmentName());
            }
            userResponse.setDepartmentNames(departments);
        }
        if(userEntity.getRole().getRoleName().equals(RoleEnum.NURSE)){
            userResponse.setNurseDiploma(userEntity.getNurse().getNurseDiploma());
            Set<String> departments=new HashSet<>();
            for(DepartmentEntity departmentEntity:userEntity.getNurse().getDepartments()){
                DepartmentEntity currentDepartment=this.departmentRepository.findById(departmentEntity.getId()).get();
                departments.add(currentDepartment.getDepartmentName());
            }
            userResponse.setDepartmentNames(departments);
        }

        return userResponse;
    }
}
