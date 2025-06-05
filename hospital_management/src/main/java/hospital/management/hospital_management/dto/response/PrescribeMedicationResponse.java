package hospital.management.hospital_management.dto.response;

import hospital.management.hospital_management.dto.request.MedicineRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrescribeMedicationResponse {
    Long patientId;

    String patientName;

    Set<MedicineResponse> medicines;

}
