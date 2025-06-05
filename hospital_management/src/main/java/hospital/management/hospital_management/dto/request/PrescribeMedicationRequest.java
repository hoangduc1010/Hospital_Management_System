package hospital.management.hospital_management.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrescribeMedicationRequest {
    Long patientId;

    Set<MedicineRequest> medicines;
}
