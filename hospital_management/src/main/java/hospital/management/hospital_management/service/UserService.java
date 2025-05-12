package hospital.management.hospital_management.service;


import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.request.UserRequest;
import hospital.management.hospital_management.dto.response.UserResponse;
import hospital.management.hospital_management.repository.UserRepository;
import hospital.management.hospital_management.util.constant.RoleEnum;
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
    public UserResponse createUser(UserRequest userInfo){
        UserEntity userEntity=new UserEntity();
        UserResponse userResponse=new UserResponse();
        userEntity.setFullname(userInfo.getFullname());
        userEntity.setDob(userEntity.getDob());
        userEntity.setGender(userInfo.getGender());
        userEntity.setUsername(userInfo.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userEntity.setPhoneNumber(userInfo.getPhoneNumber());
        userEntity.setDob(userInfo.getDob());
        RoleEnum roleEnum = RoleEnum.valueOf(userInfo.getRole().name().toUpperCase());
        userEntity.setRole(this.roleService.findByRoleName(roleEnum));
        this.userRepository.save(userEntity);
        modelMapper.map(userEntity,userResponse);
        return userResponse;
    }
}
