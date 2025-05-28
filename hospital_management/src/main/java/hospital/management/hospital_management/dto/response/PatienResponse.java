package hospital.management.hospital_management.dto.response;


import hospital.management.hospital_management.util.constant.AppointmentsTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatienResponse {

    Long patientId;

    String fullname;

    String phoneNumber;

    String dob;

    Instant dateOfAppointment;

    AppointmentsTypeEnum appointmentsType;

    String roomNumber;

    String departmentName;

    MedicalRecordResponse medicalRecord;

}
