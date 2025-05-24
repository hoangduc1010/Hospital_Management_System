package hospital.management.hospital_management.dto.request;


import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.domain.RoleEntity;
import hospital.management.hospital_management.util.constant.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {

    Long userId;

    String fullname;

    String username;

    String password;

    String phoneNumber;

    String dob;

    String address;

    GenderEnum gender;

    String avatar;

    RoleEnum role;

    DoctorDiplomaEnum doctorDiploma;

    NurseDiplomaEnum nurseDiploma;

    Integer yearOfExperience;

    List<Long> departmentId;
}
