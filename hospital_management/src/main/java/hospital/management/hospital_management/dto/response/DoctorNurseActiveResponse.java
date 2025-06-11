package hospital.management.hospital_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorNurseActiveResponse {
    NumberOfDoctorAndNurseActive numberOfDoctorAndNurseActive;

    String fullName;

    String roleName;

    Integer yearOfExperience;

    String diplomaName;

    List<String> departmentName;


}
