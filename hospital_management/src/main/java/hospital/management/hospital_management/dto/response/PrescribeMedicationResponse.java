package hospital.management.hospital_management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrescribeMedicationResponse {
    Long patientId;
    String patientName;
    private List<Map<String, Object>> medicines;

}
