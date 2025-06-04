package hospital.management.hospital_management.dto.response;


import hospital.management.hospital_management.util.constant.MedicineCategoryEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicineResponse {

    Long id;

    String medicineName;

    String activeIngredient;

    String dosageForm;

    String strength;

    String unit;

    Integer quantityInStock;

    Double sellPrice;

    Double purchasePrice;

    String manufacturer;

    String countryOfOrigin;

    MedicineCategoryEnum medicineCategory;

    Boolean isActive;
}
