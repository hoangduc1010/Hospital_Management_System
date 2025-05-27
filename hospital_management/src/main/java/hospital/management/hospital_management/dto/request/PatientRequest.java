package hospital.management.hospital_management.dto.request;


import hospital.management.hospital_management.util.constant.AppointmentsTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientRequest {
    Long patientId;

    String dateOfAppointment;

    AppointmentsTypeEnum appointmentsType;
}
