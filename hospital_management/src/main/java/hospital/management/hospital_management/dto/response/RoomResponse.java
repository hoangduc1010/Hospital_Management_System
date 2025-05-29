package hospital.management.hospital_management.dto.response;


import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.domain.PatientEntity;
import hospital.management.hospital_management.util.constant.RoomTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomResponse {

    Long id;

    String roomNumber;

    RoomTypeEnum roomType;

    String departmentName;

    Integer numberOfBeds;
}
