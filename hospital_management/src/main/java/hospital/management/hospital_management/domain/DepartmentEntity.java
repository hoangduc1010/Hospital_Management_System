package hospital.management.hospital_management.domain;



import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="departments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String departmentName;

    @ManyToMany(mappedBy = "departments")
    Set<DoctorEntity> doctors;

    @ManyToMany(mappedBy = "departments")
    Set<NurseEntity> nurses;

    @Column(columnDefinition = "MEDIUMTEXT")
    String description;

    @OneToMany(mappedBy = "currentDepartment")
    Set<PatientEntity> patients;

    @OneToMany(mappedBy = "deparmentRoom")
    Set<RoomEntity> rooms;



}
