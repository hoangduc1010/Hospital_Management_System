package hospital.management.hospital_management.dto.request;


import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.domain.RoleEntity;
import hospital.management.hospital_management.util.constant.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String fullname;

    String username;

    String password;

    String phoneNumber;

    String dob;

    GenderEnum gender;

    RoleEnum role;

    DoctorDiplomaEnum doctorDiploma;

    NurseDiplomaEnum nurseDiploma;

    Integer yearOfExperience;

    Set<Long> departmentId;
}
