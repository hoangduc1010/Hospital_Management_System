package hospital.management.hospital_management.dto.request;


import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicalRecordRequest {
    Long id;

    String diagnosis;

    String symptoms;

    String medicalHistory;

    String allergies;

    String treatmentPlan;

    String doctorNote;
}
