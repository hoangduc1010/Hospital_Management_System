package hospital.management.hospital_management.dto.response;


import hospital.management.hospital_management.util.constant.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    Long id;

    String fullname;

    String username;

    String dob;

    GenderEnum gender;

    RoleEnum roleName;

    String phoneNumber;

    Set<String> departmentNames;

    DoctorDiplomaEnum doctorDiploma;
    NurseDiplomaEnum nurseDiploma;

}
