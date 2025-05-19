package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.util.constant.DepartmentEnum;
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

    @Enumerated(EnumType.STRING)
    DepartmentEnum departmentName;

    @ManyToMany(mappedBy = "departments")
    Set<DoctorEntity> doctors;

    @ManyToMany(mappedBy = "departments")
    Set<NurseEntity> nurses;

    String description;

}
