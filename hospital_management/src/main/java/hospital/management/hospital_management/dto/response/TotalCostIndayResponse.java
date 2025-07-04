package hospital.management.hospital_management.dto.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TotalCostIndayResponse {
    String date;
    Double totalCostPatient;
    Double totalCostBuyInMedicines;
}
