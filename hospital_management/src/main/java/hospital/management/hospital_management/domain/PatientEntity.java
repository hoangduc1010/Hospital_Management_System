package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.util.constant.AppointmentsTypeEnum;
import hospital.management.hospital_management.util.constant.PatientStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;

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

    @Column(columnDefinition = "MEDIUMTEXT")
    String description;

    @ManyToOne
    @JoinColumn(name="department_id")
    DepartmentEntity currentDepartment;

    @OneToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    RoomEntity room;

    @OneToOne
    private MedicalRecordEntity medicalRecord;


    Instant dateOfAppointment;

    @Enumerated(EnumType.STRING)
    AppointmentsTypeEnum appointmentsType;


    @OneToMany(mappedBy = "patientImages")
    Set<PatientImagesEntity> images;




}
