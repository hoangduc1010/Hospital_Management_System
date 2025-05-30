package hospital.management.hospital_management.dto.request;



import hospital.management.hospital_management.util.constant.RoomTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;


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

    String filterDepartmentName;

    Integer numberOfBeds;
}
