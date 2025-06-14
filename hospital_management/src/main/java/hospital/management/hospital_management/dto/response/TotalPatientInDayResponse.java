package hospital.management.hospital_management.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TotalPatientInDayResponse {
    TotalPatientInDate totalPatientInDate;
    List<PatientDepartmentInDayResponse> departmentOfPatient;

    @Getter
    @Setter
    public static class TotalPatientInDate{
        String date;
        Integer totalPatients;
    }
}
