package hospital.management.hospital_management.util.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum AppointmentsTypeEnum {
    GENERAL(100.0),
    REQUESTED(200.0),
    SPECIALIST(300.0),
    CHECKUP(450.0),
    EMERGENCY(500.0);
    private final Double price;
}
