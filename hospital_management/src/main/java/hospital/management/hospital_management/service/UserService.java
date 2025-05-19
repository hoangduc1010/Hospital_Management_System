package hospital.management.hospital_management.service;


import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.domain.DoctorEntity;
import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.request.UserRequest;
import hospital.management.hospital_management.dto.response.UserResponse;
import hospital.management.hospital_management.repository.DepartmentRepository;
import hospital.management.hospital_management.repository.DoctorRepository;
import hospital.management.hospital_management.repository.UserRepository;
import hospital.management.hospital_management.util.constant.DepartmentEnum;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
        userEntity.setUsername(userInfo.getUsername());
        userEntity.setGender(userInfo.getGender());
        userEntity.setDob(userInfo.getDob());
        userEntity.setPhoneNumber(userInfo.getPhoneNumber());
        userEntity.setFullname(userInfo.getFullname());
        userEntity.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        RoleEnum roleEnum = RoleEnum.valueOf(userInfo.getRole().name().toUpperCase());
        userEntity.setRole(this.roleService.findByRoleName(roleEnum));
        UserEntity savedUser=this.userRepository.save(userEntity);
        if(RoleEnum.DOCTOR.equals(userInfo.getRole())){
            if(userInfo.getDepartmentId()==null){
                throw new CustomException("Khoa trực không được để trống");
            }

            if(userInfo.getDoctorDiploma()==null){
                throw new CustomException("Bằng cấp không để trống");
            }
            Set<DepartmentEntity> departments=new HashSet<>();
            DoctorEntity doctor=new DoctorEntity();
            doctor.setDepartments(departments);
            doctor.setUser(savedUser);
            doctor.setDoctorDiploma(userInfo.getDoctorDiploma());
            doctor.setDepartments(departments);
            doctor.setYearOfExperience(userInfo.getYearOfExperience());
            DoctorEntity savedDoctor=this.doctorRepository.save(doctor);
            for(Long departmentId:userInfo.getDepartmentId()){
                DepartmentEntity departmentEntity=this.departmentRepository.findById(departmentId).get();
                departmentEntity.getDoctors().add(savedDoctor);
                savedDoctor.getDepartments().add(departmentEntity);
                this.doctorRepository.save(savedDoctor);
            }
            userEntity.setDoctor(doctor);
        }
        modelMapper.map(userEntity,userResponse);
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
