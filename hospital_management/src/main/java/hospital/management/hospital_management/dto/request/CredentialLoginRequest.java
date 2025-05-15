package hospital.management.hospital_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialLoginRequest {

    @NotBlank(message = "Username cannot be empty !")
    private String username;

    @NotBlank(message = "Password cannot be empty !")
    private String password;
}
