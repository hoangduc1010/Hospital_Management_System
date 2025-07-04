package hospital.management.hospital_management.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TotalCostFromDateToResponse {
    String fromDate;

    String toDate;

    Double totalCostFromPatients;

    Double totalCostFromMedicines;
}
