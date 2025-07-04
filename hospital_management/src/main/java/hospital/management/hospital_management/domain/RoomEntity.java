package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.util.constant.RoomTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Entity
@Table(name="rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String roomNumber;

    @Enumerated(EnumType.STRING)
    RoomTypeEnum roomType;

    @ManyToOne
    @JoinColumn(name="department_id")
    DepartmentEntity departmentRoom;

    @OneToMany(mappedBy = "room")
    Set<PatientEntity> patients;

    Integer numberOfBeds;

    Boolean isActive;
}
