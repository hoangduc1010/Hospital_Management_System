package hospital.management.hospital_management.service;


import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.request.UserRequest;
import hospital.management.hospital_management.dto.response.UserResponse;
import hospital.management.hospital_management.repository.UserRepository;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
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
        this.userRepository.save(userEntity);
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
