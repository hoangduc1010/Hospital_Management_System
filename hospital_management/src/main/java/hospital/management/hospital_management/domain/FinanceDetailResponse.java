package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.dto.response.MedicineResponse;
import hospital.management.hospital_management.util.constant.AppointmentsTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinanceDetailResponse {
    Long financeDetailId;

    AppointmentsTypeEnum appointmentsType;

    MedicineResponse medicineResponse;
}
