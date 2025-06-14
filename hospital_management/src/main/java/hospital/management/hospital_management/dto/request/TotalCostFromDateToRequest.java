package hospital.management.hospital_management.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TotalCostFromDateToRequest {
    String startDate;

    String endDate;
}
