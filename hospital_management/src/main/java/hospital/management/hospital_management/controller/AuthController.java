package hospital.management.hospital_management.controller;


import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.dto.request.CredentialLoginRequest;
import hospital.management.hospital_management.dto.request.UserRequest;
import hospital.management.hospital_management.dto.response.ResponseLoginDTO;
import hospital.management.hospital_management.dto.response.UserResponse;
import hospital.management.hospital_management.service.AuthService;
import hospital.management.hospital_management.service.UserService;
import hospital.management.hospital_management.util.annotation.ApiMessage;
import hospital.management.hospital_management.util.error.CustomException;
import hospital.management.hospital_management.util.secutiry.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    @Value("${ducnh.jwt.refresh-token-validity-in-seconds}")
    private Long refreshTokenExpiration;
    private final AuthService authService;
    private final UserService userService;
    private final SecurityUtil securityUtil;
    @PostMapping("/register")
    @ApiMessage("Đăng kí người dùng")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userInfo) throws CustomException {
        return ResponseEntity.ok().body(this.authService.registerUser(userInfo));
    }

    @PostMapping("/credential-login")
    @ApiMessage("Người dùng đăng nhập")
    public ResponseEntity<ResponseLoginDTO> credentialLogin(@Valid @RequestBody CredentialLoginRequest credentialLoginRequest) throws CustomException {
        ResponseLoginDTO responseLoginDTO=this.authService.credentialLogin(credentialLoginRequest);
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", this.securityUtil.createRefreshToken(credentialLoginRequest.getUsername(),responseLoginDTO)).
                httpOnly(true).
                secure(true).
                path("/").
                maxAge(refreshTokenExpiration).
                build();
        return ResponseEntity.ok().
                header(HttpHeaders.SET_COOKIE, responseCookie.toString()).
                body(this.authService.credentialLogin(credentialLoginRequest));
    }


}
