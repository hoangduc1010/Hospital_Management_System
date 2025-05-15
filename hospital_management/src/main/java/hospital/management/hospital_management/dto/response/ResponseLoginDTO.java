package hospital.management.hospital_management.dto.response;

import hospital.management.hospital_management.domain.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ResponseLoginDTO {
    private String accessToken;
    private UserLogin userLogin;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLogin{
        private Long id;
        private String name;
        private String email;
        private String role;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInsideToken{
        private Long id;
        private String name;
        private String email;
    }

}
