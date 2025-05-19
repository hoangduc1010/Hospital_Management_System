package hospital.management.hospital_management.service;


import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.domain.DoctorEntity;
import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.request.UserRequest;
import hospital.management.hospital_management.dto.response.UserResponse;
import hospital.management.hospital_management.repository.DepartmentRepository;
import hospital.management.hospital_management.repository.DoctorRepository;
import hospital.management.hospital_management.repository.UserRepository;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
import jakarta.transaction.Transactional;
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

    @Transactional
    public UserResponse createUser(UserRequest userInfo) throws CustomException{
        UserEntity userEntity=this.userRepository.findByUsername(userInfo.getUsername());
        if(userEntity!=null){
            throw new CustomException("Tài khoản đã tồn tại!");
        }
        userEntity=new UserEntity();
        UserResponse userResponse=new UserResponse();
        modelMapper.map(userInfo,userEntity);
        userEntity.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        RoleEnum roleEnum = RoleEnum.valueOf(userInfo.getRole().name().toUpperCase());
        userEntity.setRole(this.roleService.findByRoleName(roleEnum));
        UserEntity savedUser=this.userRepository.save(userEntity);
        if(RoleEnum.DOCTOR.equals(userInfo.getRole())){
//            DepartmentEntity currentDepartment=this.departmentRepository.findByDepartmentName(userInfo.getDepartment());
//            if(currentDepartment==null){
//                throw new CustomException("Khoa trực không tồn tại");
//            }
            if(userInfo.getDoctorDiploma()==null){
                throw new CustomException("Bằng cấp không để trống");
            }
            DoctorEntity doctor=DoctorEntity.builder().user(savedUser).doctorDiploma(userInfo.getDoctorDiploma()).
                    yearOfExperience(userInfo.getYearOfExperience()).build();
            doctor=this.doctorRepository.save(doctor);
            savedUser.setDoctor(doctor);
        }
        modelMapper.map(savedUser,userResponse);
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
