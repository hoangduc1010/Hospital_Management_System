package hospital.management.hospital_management.dto.request;


import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.domain.PatientEntity;
import hospital.management.hospital_management.util.constant.RoomTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomRequest {
    Long id;

    String roomNumber;

    RoomTypeEnum roomType;

    Long departmentId;

    Integer numberOfBeds;
}
