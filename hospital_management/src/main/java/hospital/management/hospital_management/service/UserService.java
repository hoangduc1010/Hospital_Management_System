package hospital.management.hospital_management.service;
import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.request.UserRequest;
import hospital.management.hospital_management.dto.response.UserResponse;
import hospital.management.hospital_management.helper.UserServiceHelper;
import hospital.management.hospital_management.repository.DepartmentRepository;
import hospital.management.hospital_management.repository.DoctorRepository;
import hospital.management.hospital_management.repository.UserRepository;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorService doctorService;
    private final UserServiceHelper userServiceHelper;
    private final NurseService nurseService;
    @Transactional
    public UserResponse createUser(UserRequest userInfo) throws CustomException, ConstraintViolationException {
        UserEntity userEntity=this.userRepository.findByUsername(userInfo.getUsername());
        if(userEntity!=null){
            throw new CustomException("Tài khoản đã tồn tại!");
        }
        userEntity=new UserEntity();
        UserResponse userResponse=new UserResponse();
        userEntity.setUsername(userInfo.getUsername());
        userEntity.setGender(userInfo.getGender());
        userEntity.setDob(userInfo.getDob());
        userEntity.setPhoneNumber(userInfo.getPhoneNumber());
        userEntity.setFullname(userInfo.getFullname());
        userEntity.setIsActive(true);
        userEntity.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        RoleEnum roleEnum = RoleEnum.valueOf(userInfo.getRole().name().toUpperCase());
        userEntity.setRole(this.roleService.findByRoleName(roleEnum));
        UserEntity savedUser=this.userRepository.save(userEntity);
        if(RoleEnum.DOCTOR.equals(userInfo.getRole())){
            this.doctorService.createDoctor(userInfo,savedUser);
        }
        if(RoleEnum.NURSE.equals(userInfo.getRole())){
            this.nurseService.createNurse(userInfo,userEntity);
        }
        userResponse=this.userServiceHelper.convertToUserResponse(savedUser);
        return userResponse;
    }

    public UserEntity findByUsername(String username) throws CustomException{
        UserEntity user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("Tài khoản không tồn tại!");
        }
        return user;

    }

}
