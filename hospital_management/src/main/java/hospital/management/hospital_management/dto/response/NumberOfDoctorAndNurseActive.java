package hospital.management.hospital_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NumberOfDoctorAndNurseActive{
    Integer numberOfDoctors;
    Integer numberOfNurse;
}