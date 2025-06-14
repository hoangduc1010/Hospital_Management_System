package hospital.management.hospital_management.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientDepartmentInDayResponse {
    String departmentName;

    Integer numberOfPatient;
}
