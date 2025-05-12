package hospital.management.hospital_management.dto.response;


import hospital.management.hospital_management.util.constant.GenderEnum;
import hospital.management.hospital_management.util.constant.RoleEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String fullname;

    String username;

    String dob;

    GenderEnum gender;

    RoleEnum roleName;

}
