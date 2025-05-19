package hospital.management.hospital_management.helper;


import hospital.management.hospital_management.domain.RoleEntity;
import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.request.CredentialLoginRequest;
import hospital.management.hospital_management.dto.response.ResponseLoginDTO;
import hospital.management.hospital_management.service.AuthService;
import hospital.management.hospital_management.service.RoleService;
import hospital.management.hospital_management.service.UserService;
import hospital.management.hospital_management.util.error.CustomException;
import hospital.management.hospital_management.util.secutiry.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthServiceHelper {
    private final UserService userService;
    private final SecurityUtil securityUtil;
    private final RoleService roleService;
    public ResponseLoginDTO convertToReponseLogin(CredentialLoginRequest credentialLoginRequest, Authentication authentication) throws CustomException {
        ResponseLoginDTO responseLoginDTO = new ResponseLoginDTO();
        UserEntity currentUser = this.userService.findByUsername(credentialLoginRequest.getUsername());
        if (currentUser == null) {
            throw new CustomException("Tài khoản không tồn tại");
        }
        RoleEntity role=this.roleService.findByRoleName(currentUser.getRole().getRoleName());
        ResponseLoginDTO.UserLogin userLogin = new ResponseLoginDTO.UserLogin(
                currentUser.getId(),
                currentUser.getUsername(),
                role.getRoleName().name()
        );
        responseLoginDTO.setUserLogin(userLogin);
        String accessToken = this.securityUtil.createAccessToken(authentication.getName(), responseLoginDTO);
        responseLoginDTO.setAccessToken(accessToken);
        return responseLoginDTO;
    }

}
