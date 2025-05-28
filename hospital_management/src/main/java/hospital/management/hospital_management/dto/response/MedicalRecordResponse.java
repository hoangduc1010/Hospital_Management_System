package hospital.management.hospital_management.dto.response;


import hospital.management.hospital_management.domain.PatientEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicalRecordResponse {

    Long id;

    PatientEntity patient;

    String diagnosis;

    String symptoms;

    String medicalHistory;

    String allergies;

    String treatmentPlan;

    String doctorNote;
}
