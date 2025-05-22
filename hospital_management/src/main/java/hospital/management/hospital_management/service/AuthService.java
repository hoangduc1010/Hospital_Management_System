package hospital.management.hospital_management.service;


import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.request.CredentialLoginRequest;
import hospital.management.hospital_management.dto.request.UserRequest;
import hospital.management.hospital_management.dto.response.ResponseLoginDTO;
import hospital.management.hospital_management.dto.response.UserResponse;
import hospital.management.hospital_management.helper.AuthServiceHelper;
import hospital.management.hospital_management.repository.UserRepository;
import hospital.management.hospital_management.util.constant.RoleEnum;
import hospital.management.hospital_management.util.error.CustomException;
import hospital.management.hospital_management.util.secutiry.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthServiceHelper authServiceHelper;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserService userService;
    private final SecurityUtil securityUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    public UserResponse registerUser(UserRequest userInfo) throws CustomException {
        UserEntity userEntity=this.userRepository.findByUsername(userInfo.getUsername());
        if(userEntity!=null){
            throw new CustomException("Tài khoản đã tồn tại!");
        }
        userEntity=new UserEntity();
        UserResponse userResponse=new UserResponse();
        modelMapper.map(userInfo,userEntity);
        userEntity.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        RoleEnum roleEnum = RoleEnum.valueOf(RoleEnum.PATIENT.name());
        userEntity.setRole(this.roleService.findByRoleName(roleEnum));
        this.userRepository.save(userEntity);
        modelMapper.map(userEntity,userResponse);
        return userResponse;
    }

    public void updateUserToken(String refreshToken,String username) throws CustomException{
        UserEntity currentUser = this.userService.findByUsername(username);
        if(currentUser!=null){
            currentUser.setRefreshToken(refreshToken);
            this.userRepository.save(currentUser);
        }
    }

    @Transactional
    public Authentication checkAuthentication(CredentialLoginRequest credentialLoginRequest){
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credentialLoginRequest.getUsername(), credentialLoginRequest.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        }catch(Exception e){
            return null;
        }
    }
    public ResponseLoginDTO credentialLogin(CredentialLoginRequest credentialLoginRequest)throws CustomException {
        UserEntity userEntity=this.userRepository.findByUsername(credentialLoginRequest.getUsername());
        if(userEntity.getIsActive()==false || userEntity==null){
            throw new CustomException("Tài khoản không tồn tại");
        }
       Authentication authentication=this.checkAuthentication(credentialLoginRequest);
       if(authentication!=null){
           ResponseLoginDTO responseLoginDTO=this.authServiceHelper.convertToReponseLogin(credentialLoginRequest,authentication);
           String refreshToken=this.securityUtil.createRefreshToken(credentialLoginRequest.getUsername(),responseLoginDTO);
           this.updateUserToken(refreshToken,credentialLoginRequest.getUsername());
           return responseLoginDTO;
       }else{
           throw new CustomException("Tài khoản không tồn tại");
       }
    }
    public void logout() throws CustomException{
        String email=SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        if(email.equals("")){
            throw new CustomException("User not found");
        }
        this.updateUserToken(null,email);
    }
}
