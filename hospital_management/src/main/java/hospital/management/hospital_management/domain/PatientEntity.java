package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.util.constant.PatientStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    PatientStatusEnum patientStatus;

    String description;

    @ManyToOne
    @JoinColumn(name="department_id")
    DepartmentEntity currentDepartment;

    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @OneToOne
    @JoinColumn(name = "room_id")
    RoomEntity room;




}
